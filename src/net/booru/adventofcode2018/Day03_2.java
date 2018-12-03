package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day03_2 {
    public static void run() throws IOException {
        final List<Box> boxes = Files.readAllLines(Path.of("input03"))
                                     .stream()
                                     .map(line -> Box.parse(line))
                                     .collect(Collectors.toList());

        final HashSet<Box> candidates = new HashSet<>(boxes);

        for (int i = 0; i < boxes.size() - 1; i++) {
            final Box boxI = boxes.get(i);
            boolean isOverlapping = false;

            for (int j = i + 1; j < boxes.size(); j++) {
                final Box boxJ = boxes.get(j);

                if (boxI.isOverlapping(boxJ)) {
                    candidates.remove(boxJ);
                    isOverlapping = true;
                }
            }

            if (isOverlapping) {
                candidates.remove(boxI);
            }
        }

        System.out.println(candidates.iterator().next().iId);
    }

    private static class Box {
        final int iId, iBottomLeftX, iBottomLeftY, iTopRightX, iTopRightY;

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
            iBottomLeftX = x;
            iBottomLeftY = y;
            iTopRightX = x + width;
            iTopRightY = y + height;
        }

        public boolean isOverlapping(Box other) {
            return other.iBottomLeftX <= iTopRightX &&
                   other.iBottomLeftY <= iTopRightY &&
                   iBottomLeftX <= other.iTopRightX &&
                   iBottomLeftY <= other.iTopRightY;
        }

    }
}


