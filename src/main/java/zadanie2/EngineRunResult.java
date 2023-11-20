package zadanie2;

import io.jenetics.util.ISeq;

public record EngineRunResult(String engine, ISeq<RunResult> results) {

    double avgFitness() {
        return results.stream()
                .map(x -> x.best().fitness())
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

}
