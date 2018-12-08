package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class Day05_1 {
    public static void run() throws IOException {
        final char[] polymerData = Files.readString(Path.of("input05")).toCharArray();

        final LinkedList<Character> polymer = new LinkedList<>();
        for (int i = 0; i < polymerData.length-1; i++) {
            polymer.add(polymerData[i]);
        }

        reduce(polymer);

        System.out.println(polymer.size());
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
