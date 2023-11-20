package utils;

import domain.Item;
import domain.Knapsack;
import io.jenetics.util.ISeq;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProblemReader {
    public static Knapsack fromFile(String file) throws IOException {
        var f = Files.readAllLines(Paths.get(file));
        var knapsackCapacity = Integer.parseInt(f.get(0).split(" ")[1]);

        var items = f.stream().skip(1).map(x -> {
            var value = Integer.parseInt(x.split(" ")[0]);
            var size = Integer.parseInt(x.split(" ")[1]);
            return new Item(size, value);
        }).collect(ISeq.toISeq());

        return new Knapsack(items, knapsackCapacity);

    }
}
