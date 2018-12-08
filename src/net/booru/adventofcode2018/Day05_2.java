package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Day05_2 {
    public static void run() throws IOException {
        final char[] polymerData = Files.readString(Path.of("input05")).toCharArray();

        HashSet<Character> units = new HashSet<>(polymerData.length);
        final LinkedList<Character> polymer = new LinkedList<>();
        for (int i = 0; i < polymerData.length - 1; i++) {
            final char unit = polymerData[i];
            polymer.add(unit);
            units.add(Character.toLowerCase(unit));
        }

        int minLength = polymerData.length;
        for (Character unit : units) {
            final LinkedList<Character> unitFilteredPolymer =
                polymer.stream()
                       .filter(c -> Character.toLowerCase(c) != unit)
                       .collect(Collectors.toCollection(LinkedList::new));

            reduce(unitFilteredPolymer);
            minLength = Math.min(unitFilteredPolymer.size(), minLength);
        }

        System.out.println(minLength);
    }

    private static void reduce(final LinkedList<Character> polymer) {
        final ListIterator<Character> iterator = polymer.listIterator();
        while (iterator.hasNext()) {
            Character current = iterator.next();

            if (!iterator.hasNext()) {
                break;
            }

            final Character next = iterator.next();

            if (isReaction(current, next)) {
                iterator.remove();
                iterator.previous();
                iterator.remove();

                if (iterator.hasPrevious()) {
                    iterator.previous();
                }
            }
            else {
                iterator.previous(); // backup one
            }
        }
    }

    private static boolean isReaction(char a, char b) {
        return Character.toLowerCase(a) == Character.toLowerCase(b) && a != b;
    }
}
