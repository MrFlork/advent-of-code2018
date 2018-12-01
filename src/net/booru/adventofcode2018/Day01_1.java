package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day01_1 {
    public static void run() throws IOException {
        final List<String> changes = Files.readAllLines(Path.of("input01"));

        int frequency = 0;
        for (String change : changes) {
            frequency += Integer.parseInt(change);
        }

        System.out.println(frequency);
    }
}
