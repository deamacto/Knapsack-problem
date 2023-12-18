import thiagodnf.jacof.aco.AntColonySystem;
import thiagodnf.jacof.problem.Problem;
import thiagodnf.jacof.util.ExecutionStats;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {

        Problem problem = new MyKnapsackProblem("myproblem.kp");

        var runCount = 3;

        var parametersNumberOfAnts = List.of(
                new Parameters(1, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(5, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(20, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(30, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(40, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(50, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(60, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(70, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(80, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(90, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(100, 50, 1.0, 2.0, 0.1, 0.1, 0.9)
        );

        System.out.println("numberOfAnts");
        prettyPrintResults(runMultipleTimes(problem, parametersNumberOfAnts, runCount));

        var parametersNumberOfIterations = List.of(
                new Parameters(10, 1, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 5, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 10, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 20, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 30, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 40, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 60, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 70, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 80, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 90, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 100, 1.0, 2.0, 0.1, 0.1, 0.9)
        );

        System.out.println("numberOfIterations");
        prettyPrintResults(runMultipleTimes(problem, parametersNumberOfIterations, runCount));

        var parametersAlpha = List.of(
                new Parameters(10, 50, 0.1, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.2, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.3, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.4, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.5, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.6, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.7, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.8, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 0.9, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.9)
        );

        System.out.println("alpha");
        prettyPrintResults(runMultipleTimes(problem, parametersAlpha, runCount));

        var parametersBeta = List.of(
                new Parameters(10, 50, 1.0, 0.1, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.2, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.3, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.4, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.5, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.6, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.7, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.8, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 0.9, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 1.0, 0.1, 0.1, 0.9)
        );

        System.out.println("beta");
        prettyPrintResults(runMultipleTimes(problem, parametersBeta, runCount));

        var parametersRho = List.of(
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.2, 0.1, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.3, 0.1, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.4, 0.1, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.5, 0.1, 0.1)
        );

        System.out.println("rho");
        prettyPrintResults(runMultipleTimes(problem, parametersRho, runCount));

        var parametersOmega = List.of(
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.2, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.3, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.4, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.5, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.6, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.7, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.8, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.9, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 1.0, 0.1)
        );

        System.out.println("omega");
        prettyPrintResults(runMultipleTimes(problem, parametersOmega, runCount));

        var parametersQ0 = List.of(
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.1),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.2),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.3),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.4),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.5),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.6),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.7),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.8),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 0.9),
                new Parameters(10, 50, 1.0, 2.0, 0.1, 0.1, 1.0)
        );

        System.out.println("q0");
        prettyPrintResults(runMultipleTimes(problem, parametersQ0, runCount));
    }

    static List<AggregatedResult> runMultipleTimes(Problem problem, List<Parameters> parameters, int times) {
        return parameters.stream()
                .map(x -> runMultipleTimes(problem, x, times))
                .toList();
    }

    static void printResults(List<AggregatedResult> results) {
        results.forEach(x -> {
            var line = String.format("%f,%f", x.meanRuntime, x.meanBestSolution);
            System.out.println(line);
        });
    }

    static void prettyPrintResults(List<AggregatedResult> results) {
        results.forEach(x -> {
            var line = String.format("meanRuntime: %f, meanBestSolution: %f", x.meanRuntime, x.meanBestSolution);
            System.out.println(line);
        });
    }

    record Parameters(int numberOfAnts, int numberOfIterations, double alpha, double beta, double rho, double omega,
                      double q0) {
    }

    record Result(ExecutionStats stats) {
        double executionTime() {
            return stats.executionTime;
        }

        int[] bestSolution() {
            return stats.bestSolution;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "executionTime=" + executionTime() +
                    ", bestSolution=" + Arrays.toString(bestSolution()) +
                    '}';
        }
    }

    record AggregatedResult(List<Result> results, double meanRuntime, double meanBestSolution) {
    }

    static Result runWithParameters(Problem problem, Parameters parameters) {
        AntColonySystem aco = new AntColonySystem(problem);
        aco.setNumberOfAnts(parameters.numberOfAnts);
        aco.setNumberOfIterations(parameters.numberOfIterations);
        aco.setAlpha(parameters.alpha);
        aco.setBeta(parameters.beta);
        aco.setRho(parameters.rho);
        aco.setOmega(parameters.omega);
        aco.setQ0(parameters.q0);
        var stats = ExecutionStats.execute(aco, problem);
        return new Result(stats);
    }

    static AggregatedResult runMultipleTimes(Problem problem, Parameters parameters, int times) {
        var results = IntStream.range(0, times)
                .mapToObj(i -> runWithParameters(problem, parameters))
                .toList();

        var meanRuntime = results.stream()
                .mapToDouble(x -> x.executionTime())
                .average()
                .getAsDouble();

        var meanBestSolution = results.stream()
                .mapToDouble(x -> problem.evaluate(x.bestSolution()))
                .average()
                .getAsDouble();

        return new AggregatedResult(results, meanRuntime, meanBestSolution);
    }
}
