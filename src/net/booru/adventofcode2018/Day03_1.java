package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day03_1 {
    public static void run() throws IOException {
        final List<Box> boxes = Files.readAllLines(Path.of("input03"))
                                     .stream()
                                     .map(line -> Box.parse(line))
                                     .collect(Collectors.toList());

        int rightMost = boxes.stream().max(Comparator.comparing(Box::getRight)).get().getRight() + 1;
        int bottomMost = boxes.stream().max(Comparator.comparing(Box::getBottom)).get().getBottom() + 1;
        int[][] mutableCloth = new int[rightMost][bottomMost];

        for (Box box : boxes) {
            fillCloth(mutableCloth, box);
        }

        int filled = 0;
        for (int y = 0; y < bottomMost; y++) {
            for (int x = 0; x < rightMost; x++) {
                filled += mutableCloth[x][y] >= 2 ? 1 : 0;
            }
        }

        System.out.println(filled);
    }

    private static void fillCloth(final int[][] mutableCloth, final Box box) {
        for (int y = box.iY; y < box.iY + box.iHeight; y++) {
            for (int x = box.iX; x < box.iX + box.iWidth; x++) {
                mutableCloth[x][y] += 1;
            }
        }
    }

    private static class Box {
        final int iId, iX, iY, iWidth, iHeight;

        public static Box parse(final String line) {
            // #1261 @ 292,515: 18x18
            final int index1 = line.indexOf('@');
            final int index2 = line.indexOf(',');
            final int index3 = line.indexOf(':');
            final int index4 = line.indexOf('x');
            final int[] s = Stream.of(line.substring(1, index1).trim(),
                                      line.substring(index1 + 1, index2).trim(),
                                      line.substring(index2 + 1, index3).trim(),
                                      line.substring(index3 + 1, index4).trim(),
                                      line.substring(index4 + 1).trim())
                                  .mapToInt(Integer::parseInt).toArray();

            return new Box(s[0], s[1], s[2], s[3], s[4]);
        }

        public Box(final int id, final int x, final int y, final int width, final int height) {
            iId = id;
            iX = x;
            iY = y;
            iWidth = width;
            iHeight = height;
        }

        public int getRight() {
            return iX + iWidth;
        }

        public int getBottom() {
            return iY + iHeight;
        }
    }
}

