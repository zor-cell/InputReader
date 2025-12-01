package io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class IOUtils {
    /**
     * Reads the next line in the given {@code reader} split by {@code delimiter} and parses it into a list by applying {@code parser}.
     */
    public static <T> List<T> readList(Scanner reader, String delimiter, Function<String, T> parser) {
        String line = reader.nextLine();
        String[] parts = line.split(delimiter);

        List<T> result = new ArrayList<>(parts.length);
        for(String part : parts) {
            result.add(parser.apply(part));
        }

        return result;
    }

    /**
     * Reads a 2d array of the given dimensions and returns it as a 2d list.
     */
    public static <T> List<List<T>> readGrid(Scanner reader, String delimiter, int width, int height, Function<String, T> parser) {
        List<List<T>> grid = new ArrayList<>();

        for(int r = 0;r < height;r++) {
            String line = reader.nextLine();
            String[] parts = line.split(delimiter);

            List<T> row = new ArrayList<>();
            for (int c = 0; c < width; c++) {
                row.add(parser.apply(parts[c]));
            }

            grid.add(row);
        }

        return grid;
    }
}
