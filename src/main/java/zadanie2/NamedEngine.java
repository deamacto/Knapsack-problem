package zadanie2;

import io.jenetics.BitGene;
import io.jenetics.engine.Engine;

public record NamedEngine(String name, Engine<BitGene, Double> engine) {
}
