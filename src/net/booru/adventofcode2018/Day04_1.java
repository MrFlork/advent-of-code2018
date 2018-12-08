package net.booru.adventofcode2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Day04_1 {

    public static void run() throws IOException {
        final Deque<Record> records = Files.readAllLines(Path.of("input04"))
                                           .stream()
                                           .map(Record::parse)
                                           .sorted(Comparator.comparing(Record::getTimeStamp))
                                           .collect(Collectors.toCollection(ArrayDeque::new));

        final Collection<Guard> guards = parseGuards(records);

        // 1. find guard that sleeps the most minutes
        final Guard maxSleepGuard = guards.stream().max(Comparator.comparing(Guard::getTotalSleep)).get();

        // 2. at which minute does he most often sleep
        int current = 0;
        int max = 0;
        int maxMinute = 0;

        for (State state : maxSleepGuard.getStatesOrdered()) {
            current += state.getValue(); // increase decrease with sleep/awake
            if (current > max) {
                max = current;
                maxMinute = state.getMinute();
            }
        }

        final int answer = maxSleepGuard.iId * maxMinute;
        System.out.println(answer);
    }

    private static Collection<Guard> parseGuards(final Deque<Record> records) {
        final HashMap<Integer, Guard> guards = new HashMap<>(records.size());
        while (!records.isEmpty()) {
            final Record guardRecord = records.pop();
            final int guardId = Integer.parseInt(guardRecord.getEvent());

            final ArrayList<Sleep> sleeps = new ArrayList<>(4);
            final ArrayList<Wake> wakes = new ArrayList<>(4);
            while (!records.isEmpty() && !records.peek().isGuard()) {
                final Record sleep = records.pop();
                final Record wake = records.pop();
                sleeps.add(new Sleep(sleep.getTimeStamp().getMinute(),
                                     wake.getTimeStamp().getMinute()));
                wakes.add(new Wake(wake.getTimeStamp().getMinute()));
            }

            if (guards.containsKey(guardId)) {
                final Guard guard = guards.get(guardId);
                guard.iSleeps.addAll(sleeps);
                guard.iWakes.addAll(wakes);
            }
            else {
                final Guard guard = new Guard(guardId, sleeps, wakes);
                guards.put(guardId, guard);
            }
        }

        return guards.values();
    }

    public interface State {
        int getMinute();
        int getValue();
    }

    public static class Wake implements State {
        final int iMinute;

        public Wake(final int minute) {
            iMinute = minute;
        }

        @Override
        public int getMinute() {
            return iMinute;
        }

        @Override
        public int getValue() {
            return -1;
        }
    }

    public static class Sleep implements State {
        final int iStart;
        final int iDuration;

        public Sleep(final int start, final int end) {
            iStart = start;
            iDuration = end - start;
        }

        public int getMinute() {
            return iStart;
        }

        public int getValue() {
            return 1;
        }

        public int getDuration() {
            return iDuration;
        }
    }

    public static class Guard {
        final int iId;
        final ArrayList<Sleep> iSleeps;
        final ArrayList<Wake> iWakes;

        public Guard(final int id, final ArrayList<Sleep> sleeps, final ArrayList<Wake> wakes) {
            iId = id;
            iSleeps = sleeps;
            iWakes = wakes;
        }

        public int getTotalSleep() {
            final int sum = iSleeps.stream().mapToInt(Sleep::getDuration).sum();
            System.out.println("tot sleep = " + sum);
            return sum;
        }
        
        public ArrayList<State> getStatesOrdered() {
            final ArrayList<State> states = new ArrayList<>(iSleeps);
            states.addAll(iWakes);
            states.sort(Comparator.comparing(State::getMinute));
            return states;
        }
    }

    private static class Record {
        public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        private final String iEvent;
        private final LocalDateTime iTimeStamp;

        public Record(final LocalDateTime timeStamp, final String event) {
            iEvent = event;
            iTimeStamp = timeStamp;
        }

        public static Record parse(final String line) {
            final String dateInString = line.substring(1, 17);
            final String stateString = line.substring(19);
            final LocalDateTime dateTime = LocalDateTime.parse(dateInString, FORMATTER);
            final String event = parseEvent(stateString);

            return new Record(dateTime, event);
        }

        public static String parseEvent(final String state) {
            if (state.contains("wakes")) {
                return "wake";
            }
            if (state.contains("asleep")) {
                return "sleep";
            }
            if (state.contains("begins")) {
                final int beginIndex = state.indexOf('#') + 1;
                final int endIndex = state.indexOf(' ', beginIndex);
                return state.substring(beginIndex, endIndex); // the guard ID
            }
            throw new IllegalStateException("Unknown state");
        }


        public LocalDateTime getTimeStamp() {
            return iTimeStamp;
        }

        public String getEvent() {
            return iEvent;
        }

        public boolean isGuard() {
            return Character.isDigit(iEvent.charAt(0));
        }
    }
}

