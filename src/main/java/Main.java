import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.ISeq;

import java.util.Random;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

public class Main {
    public static void main(final String[] args) {
        final Knapsack knapsack = Knapsack.randomOf(200, new Random(123));

        Knapsack testKnapsack = new Knapsack(ISeq.of(
                new Item(1, 10),
                new Item(1, 10),
                new Item(2, 15),
                new Item(3, 40)
        ), 2);

        for(int i = 0; i < 5; i++) {
            mainLoop(knapsack);
            System.out.println("\n");
        }
    }

    public static void mainLoop(Knapsack knapsack) {
        final Engine<BitGene, Double> engine = Engine.builder(knapsack)
                .populationSize(500)
                .survivorsSelector(new TournamentSelector<>(5))
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(0.2),
                        new SinglePointCrossover<>(0.9))
                .build();

        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        final Phenotype<BitGene, Double> best = engine.stream()
//                .limit(bySteadyFitness(50))
                .limit(1000)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        System.out.println(best);
    }
}