import io.jenetics.BitGene;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public final class Knapsack implements Problem<ISeq<Item>, BitGene, Double> {

    private final Codec<ISeq<Item>, BitGene> codec;
    private final double knapsackSize;

    public Knapsack(ISeq<Item> items, double knapsackSize) {
        this.codec = Codecs.ofSubSet(items);
        this.knapsackSize = knapsackSize;
    }


    @Override
    public Function<ISeq<Item>, Double> fitness() {
        return items -> {
            var sum = items.stream().reduce(
                    new Item(0, 0),
                    (x, acc) -> new Item(acc.size() + x.size(), acc.value() + x.value())
            );
            return sum.size() <= knapsackSize ? sum.value() : 0;
        };
    }

    @Override
    public Codec<ISeq<Item>, BitGene> codec() {
        return codec;
    }

    public static Knapsack randomOf(final int itemCount, final RandomGenerator random) {
        return new Knapsack(
                Stream.generate(() -> Item.random(random))
                        .limit(itemCount)
                        .collect(ISeq.toISeq()),
                itemCount * 100.0 / 3.0
        );
    }
}