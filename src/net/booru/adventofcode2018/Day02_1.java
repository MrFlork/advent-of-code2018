package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02_1 {
    public static void run() throws IOException {
        final List<String> lines = Files.readAllLines(Path.of("input02"));

        int hasTwo = 0;
        int hasThree = 0;

        for (String line : lines) {
            hasTwo += hasN(line, 2);
            hasThree += hasN(line, 3);
        }

        final int checksum = hasTwo * hasThree;
        System.out.println(checksum);
    }

    private static int hasN(final String line, final int count) {
        final char[] chars = line.toCharArray();
        final HashMap<Integer, Integer> counts = new HashMap<>(chars.length * 2);

        for (int c : chars) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == count) {
                return 1;
            }
        }

        return 0;
    }
}
