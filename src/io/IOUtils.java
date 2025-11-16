package io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class IOUtils {
    public static <T> List<T> readLine(Scanner reader, Function<String, T> parser, String delimiter) {
        String line = reader.nextLine();
        String[] parts = line.split(delimiter);

        List<T> result = new ArrayList<>(parts.length);
        for(String part : parts) {
            result.add(parser.apply(part));
        }

        return result;
    }
}
