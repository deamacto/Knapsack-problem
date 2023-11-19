import io.jenetics.util.ISeq;

import java.io.*;

public class DataReader {
    public static Knapsack readItems(String file) {
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line = br.readLine();
            int numberOfItems = Integer.parseInt(line.split(" ")[0]);
            int knapsackCapacity = Integer.parseInt(line.split(" ")[1]);

            ISeq<Item> items = ISeq.empty();

            line = br.readLine();
            while(line != null) {
                int size = Integer.parseInt(line.split(" ")[1]);
                int value = Integer.parseInt(line.split(" ")[0]);
                Item item = new Item(size, value);
                items.append(item);
                line = br.readLine();
            }

            Knapsack knapsack = new Knapsack(items, knapsackCapacity);
            return knapsack;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
