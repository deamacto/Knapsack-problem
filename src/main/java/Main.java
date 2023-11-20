import domain.Knapsack;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.ISeq;
import utils.ProblemReader;

import java.io.IOException;
import java.util.stream.IntStream;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;

public class Main {

    record RunResult(Phenotype<BitGene, Double> best, EvolutionStatistics<Double, ?> stats) {

    }

    static RunResult runOnce(Engine<BitGene, Double> engine) {
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        var best = engine.stream().limit(1000)
                .peek(statistics)
                .collect(toBestPhenotype());

        return new RunResult(best, statistics);
    }

    static ISeq<RunResult> runMany(Engine<BitGene, Double> engine, int n) {
        return IntStream.range(0, n)
                .mapToObj(x -> runOnce(engine))
                .collect(ISeq.toISeq());
    }

    static Engine<BitGene, Double> basicEngine(Knapsack problem) {
        return Engine.builder(problem)
                .populationSize(500)
                .survivorsSelector(new TournamentSelector<>(5))
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(0.2),
                        new SinglePointCrossover<>(0.9))
                .build();
    }


    public static void main(final String[] args) throws IOException {
        final Knapsack knapsack = ProblemReader.fromFile("src/main/f8_l-d_kp_23_10000");

        var results = runMany(basicEngine(knapsack), 5);
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