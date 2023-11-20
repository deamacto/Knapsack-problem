package domain;

import io.jenetics.internal.util.Requires;

import java.util.random.RandomGenerator;

public record Item(double size, double value) {
    public Item {
        Requires.nonNegative(size);
        Requires.nonNegative(value);
    }

    public static Item random(final RandomGenerator random) {
        return new Item(
                random.nextDouble() * 100,
                random.nextDouble() * 100
        );
    }
}
