package zadanie2;

import domain.Knapsack;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.ISeq;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;

public class Main {

    static Engine<BitGene, Double> basicEngine(Knapsack problem, double mutation, double crossover) {
        return Engine.builder(problem)
                .populationSize(40)
                .survivorsSelector(new TournamentSelector<>(5))
//                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(mutation),
                        new SinglePointCrossover<>(crossover))
                .build();
    }

    static Engine<BitGene, Double> rouletteSelectorEngine(Knapsack problem, double mutation, double crossover) {
        return Engine.builder(problem)
                .populationSize(40)
                .survivorsSelector(new RouletteWheelSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(mutation),
                        new SinglePointCrossover<>(crossover))
                .build();
    }

    static Engine<BitGene, Double> eliteSelectorEngine(Knapsack problem, double mutation, double crossover, int n) {
        return Engine.builder(problem)
                .populationSize(40)
                .survivorsSelector(new EliteSelector<>(n))
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(mutation),
                        new SinglePointCrossover<>(crossover))
                .build();
    }

    static Engine<BitGene, Double> swapMutator(Knapsack problem, double mutation, double crossover) {
        return Engine.builder(problem)
                .populationSize(40)
                .survivorsSelector(new TournamentSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new SwapMutator<>(mutation),
                        new SinglePointCrossover<>(crossover))
                .build();
    }

    static Engine<BitGene, Double> uniformCrossover(Knapsack problem, double mutation, double crossover) {
        return Engine.builder(problem)
                .populationSize(40)
                .survivorsSelector(new TournamentSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new Mutator<>(mutation),
                        new UniformCrossover<>(crossover))
                .build();
    }

    static Engine<BitGene, Double> inverseOperator(Knapsack problem, double mutation, double crossover) {
        return Engine.builder(problem)
                .populationSize(40)
                .survivorsSelector(new TournamentSelector<>())
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(
                        new InverseOperator<>(mutation),
                        new UniformCrossover<>(crossover))
                .build();
    }

    static RunResult runOnce(Engine<BitGene, Double> engine, int limit) {
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        var best = engine.stream().limit(limit)
                .peek(statistics)
                .collect(toBestPhenotype());

        return new RunResult(best, statistics);
    }

    static RunResult runOnceWithSteadyFitness(Engine<BitGene, Double> engine, int limit) {
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        var best = engine.stream().limit(Limits.bySteadyFitness(limit))
                .peek(statistics)
                .collect(toBestPhenotype());

        return new RunResult(best, statistics);
    }

    static ISeq<RunResult> runMany(Engine<BitGene, Double> engine, int n, int limit) {
        return IntStream.range(0, n)
                .mapToObj(x -> runOnce(engine, limit))
                .collect(ISeq.toISeq());
    }

    static ISeq<RunResult> runManyWithSteadyFitness(Engine<BitGene, Double> engine, int n, int limit) {
        return IntStream.range(0, n)
                .mapToObj(x -> runOnceWithSteadyFitness(engine, limit))
                .collect(ISeq.toISeq());
    }

    static ISeq<EngineRunResult> runBatch(ISeq<NamedEngine> engines, int iterations, int limit) {
        return engines.stream()
                .map(x -> new EngineRunResult(x.name(), runMany(x.engine(), iterations, limit)))
                .collect(ISeq.toISeq());
    }

    static ISeq<EngineRunResult> runBatchWithSteadyFitness(ISeq<NamedEngine> engines, int iterations, int limit) {
        return engines.stream()
                .map(x -> new EngineRunResult(x.name(), runManyWithSteadyFitness(x.engine(), iterations, limit)))
                .collect(ISeq.toISeq());
    }

    static void printStats(ISeq<EngineRunResult> results) {
        results.stream().forEach(x -> {
                    var avg = x.avgFitness();
                    var all = x.results().stream().map(y -> y.best().fitness().toString()).collect(Collectors.joining(" "));
                    var generations = x.results().map(y -> y.stats().altered().count()).stream().mapToLong(a -> a).average().getAsDouble();
                    System.out.println(x.engine() + " | " + avg + " | " + all + " | " + generations);
                }
        );
        System.out.println(" ");
    }

    public static void main(String[] args) throws IOException {
        final Knapsack knapsack = utils.ProblemReader.fromFile("src/main/f8_l-d_kp_23_10000");

//        var engines = ISeq.of(
//               new NamedEngine("basic", basicEngine(knapsack))
//               new NamedEngine("roulette", rouletteSelectorEngine(knapsack)),
//                new NamedEngine("elite", eliteSelectorEngine(knapsack)),
//              new NamedEngine("swap", swapMutator(knapsack)),
//               new NamedEngine("uniform", uniformCrossover(knapsack)),
//                new NamedEngine("inverse", inverseOperator(knapsack))
//        );

        var engines = ISeq.of(
                new NamedEngine("basic", basicEngine(knapsack, 0.05, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.2, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.3, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.4, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.5, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.6, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.7, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.8, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.9, 0.1))
        );

        var engines2 = ISeq.of(
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.05)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.1)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.2)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.3)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.4)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.5)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.6)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.7)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.8)),
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.9))
        );

        var enginesSelectors = ISeq.of(
                new NamedEngine("basic", basicEngine(knapsack, 0.1, 0.6)),
                new NamedEngine("roulette", rouletteSelectorEngine(knapsack, 0.1, 0.6)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 1)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 2)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 3)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 5)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 7)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 10)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 12)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 15)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 20)),
                new NamedEngine("elite", eliteSelectorEngine(knapsack, 0.1, 0.6, 25))
        );

        var engineMutator = ISeq.of(
                new NamedEngine("swap", swapMutator(knapsack, 0.05, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.1, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.2, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.3, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.4, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.5, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.6, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.7, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.8, 0.6)),
                new NamedEngine("swap", swapMutator(knapsack, 0.9, 0.6))
        );

        var engineCrossover = ISeq.of(
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.05)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.1)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.2)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.3)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.4)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.5)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.6)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.7)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.8)),
                new NamedEngine("crossover", uniformCrossover(knapsack, 0.1, 0.9))
        );

        var engineInverse = ISeq.of(
                new NamedEngine("inverse", inverseOperator(knapsack, 0.05, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.1, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.2, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.3, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.4, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.5, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.6, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.7, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.8, 0.2)),
                new NamedEngine("inverse", inverseOperator(knapsack, 0.9, 0.2))
        );


        var results = runBatchWithSteadyFitness(engines, 10, 50);
        var results2 = runBatchWithSteadyFitness(engines2, 10, 50);
        var resultsSelectors = runBatchWithSteadyFitness(enginesSelectors, 10, 50);
        var resultsMutator = runBatchWithSteadyFitness(engineMutator, 10, 50);
        var resultsCrossover = runBatchWithSteadyFitness(engineCrossover, 10,50);
        var resultsInverse = runBatchWithSteadyFitness(engineInverse, 10, 50);

        printStats(results);
        printStats(results2);
        printStats(resultsSelectors);
        printStats(resultsMutator);
        printStats(resultsCrossover);
        printStats(resultsInverse);
    }
}