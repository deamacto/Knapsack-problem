package zadanie2;

import domain.Knapsack;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.ISeq;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;

public class Main {

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

    static Engine<BitGene, Double> rouletteSelectorEngine(Knapsack problem) {
        return Engine.builder(problem)
                .populationSize(500)
                .survivorsSelector(new RouletteWheelSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(0.2),
                        new SinglePointCrossover<>(0.9))
                .build();
    }

    static Engine<BitGene, Double> eliteSelectorEngine(Knapsack problem) {
        return Engine.builder(problem)
                .populationSize(500)
                .survivorsSelector(new EliteSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(0.2),
                        new SinglePointCrossover<>(0.9))
                .build();
    }

    static Engine<BitGene, Double> swapMutator(Knapsack problem) {
        return Engine.builder(problem)
                .populationSize(500)
                .survivorsSelector(new TournamentSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new SwapMutator<>(0.2),
                        new SinglePointCrossover<>(0.9))
                .build();
    }

    static Engine<BitGene, Double> uniformCrossover(Knapsack problem) {
        return Engine.builder(problem)
                .populationSize(500)
                .survivorsSelector(new TournamentSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(0.2),
                        new UniformCrossover<>(0.9))
                .build();
    }

    static Engine<BitGene, Double> inverseOperator(Knapsack problem) {
        return Engine.builder(problem)
                .populationSize(500)
                .survivorsSelector(new TournamentSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new InverseOperator<>(0.2),
                        new UniformCrossover<>(0.9))
                .build();
    }

    static RunResult runOnce(Engine<BitGene, Double> engine, int limit) {
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        var best = engine.stream().limit(limit)
                .peek(statistics)
                .collect(toBestPhenotype());

        return new RunResult(best, statistics);
    }

    static ISeq<RunResult> runMany(Engine<BitGene, Double> engine, int n, int limit) {
        return IntStream.range(0, n)
                .mapToObj(x -> runOnce(engine, limit))
                .collect(ISeq.toISeq());
    }

    static ISeq<EngineRunResult> runBatch(ISeq<NamedEngine> engines, int iterations, int limit) {
        return engines.stream()
                .map(x -> new EngineRunResult(x.name(), runMany(x.engine(), iterations, limit)))
                .collect(ISeq.toISeq());
    }

    public static void main(String[] args) throws IOException {
        final Knapsack knapsack = utils.ProblemReader.fromFile("src/main/f8_l-d_kp_23_10000");

        var engines = ISeq.of(
                new NamedEngine("basic", basicEngine(knapsack)),
                new NamedEngine("roulette", rouletteSelectorEngine(knapsack)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack)),
                new NamedEngine("swap", swapMutator(knapsack)),
                new NamedEngine("uniform", uniformCrossover(knapsack)),
                new NamedEngine("inverse", inverseOperator(knapsack))
        );

        var results = runBatch(engines, 5, 13);

        results.stream().forEach(x -> {
                    var avg = x.avgFitness();
                    var all = x.results().stream().map(y -> y.best().fitness().toString()).collect(Collectors.joining(" "));
                    System.out.println(x.engine() + " | " + all + " | " + avg);
                }
        );
    }


}

