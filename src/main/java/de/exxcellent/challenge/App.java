package de.exxcellent.challenge;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.exxcellent.challenge.football.FootballData;
import de.exxcellent.challenge.input.csv.CsvReader;
import de.exxcellent.challenge.util.ListUtil;
import de.exxcellent.challenge.weather.WeatherData;


/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as
 * baseline for your software design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     *
     * @param args
     *         The CLI arguments passed
     */
    public static void main(String... args) {
        if (args.length == 0 || args.length > 2) {
            printUsage();
            return;
        }
        List<String> switches =
                Arrays.stream(args).takeWhile(arg -> arg.startsWith("--")).collect(
                        Collectors.toList());
        if (switches.size() > 1) {
            printUsage();
            return;
        }

        String path = args[switches.size()];
        if (switches.size() == 1 && switches.get(0).equals("--football")) {
            analyzeFootball(path);
        } else {
            analyzeWeather(path);
        }
    }

    private static void analyzeFootball(String path) {
        String teamWithSmallestGoalSpread =
                runAnalysis(path, FootballData.class, d -> Math.abs(d.goals - d.goalsAllowed)).map(
                        fb -> fb.team).orElse("No teams.");
        System.out.printf("Team with smallest goal spread       : %s%n",
                          teamWithSmallestGoalSpread);
    }

    private static void analyzeWeather(String path) {
        int dayWithSmallestTempSpread = runAnalysis(path, WeatherData.class,
                                                    d -> d.maxTemperature - d.minTemperature).map(
                wd -> wd.day).orElse(-1);
        System.out.printf("Day with smallest temperature spread : %d%n",
                          dayWithSmallestTempSpread);
    }

    private static <T, N extends Comparable<? super N>> Optional<T> runAnalysis(String path,
                                                                                Class<T> objectType,
                                                                                Function<? super T, N> keyComp) {
        try (var csv = new CsvReader<>(',', new FileReader(path), objectType)) {
            List<? extends T> data = csv.readData();
            return ListUtil.argmin(data, keyComp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static void printUsage() {
        System.out.println("Usage: App <--football|--weather| > <path to file>");
    }

}
