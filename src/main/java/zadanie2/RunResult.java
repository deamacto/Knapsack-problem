package zadanie2;

import io.jenetics.BitGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.ISeq;

public record RunResult(Phenotype<BitGene, Double> best, EvolutionStatistics<Double, ?> stats) {

}

