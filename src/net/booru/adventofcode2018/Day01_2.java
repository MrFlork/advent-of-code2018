package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

public class Day01_2 {
    public static void run() throws IOException {
        final List<String> changes = Files.readAllLines(Path.of("input01"));

        final HashSet<Integer> seenFrequencies = new HashSet<>(changes.size() * 2);
        int frequency = 0;
        seenFrequencies.add(0);

        boolean done = false;
        while (!done) {
            for (String change : changes) {
                frequency += Integer.parseInt(change);

                if (seenFrequencies.contains(frequency)) {
                    System.out.println(frequency);
                    done = true;
                    break;
                }
                seenFrequencies.add(frequency);
            }
        }
    }
}
