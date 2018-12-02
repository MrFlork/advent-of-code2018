package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02_2 {
    public static void run() throws IOException {
        final List<String> lines = Files.readAllLines(Path.of("input02"));

        for (int j = 0; j < lines.size() - 1; j++) {
            final String firstLine = lines.get(j);

            for (int i = j + 1; i < lines.size(); i++) {
                final String diff = diffLines(lines.get(j), lines.get(i));
                if (diff != null) {
                    System.out.println(diff);
                    break;
                }
            }
        }
    }

    private static String diffLines(final String a, final String b) {
        // Strings have the same length
        int diffs = 0;
        int position = 0;
        for (int i = 0; i < a.length() && diffs <= 1; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                ++diffs;
                position = i;
            }
        }

        if (diffs != 1) {
            return null;
        }

        // one diff, return the same chars
        return a.substring(0, position) + a.substring(position + 1);
    }
}
