package de.exxcellent.challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.exxcellent.challenge.input.csv.CsvReader;
import de.exxcellent.challenge.util.Analysis;
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
        String teamWithSmallestGoalSpread = "A good team"; // Your goal analysis function call …
        System.out.printf("Team with smallest goal spread       : %s%n",
                          teamWithSmallestGoalSpread);
    }

    private static void analyzeWeather(String path) {
        try (var csv = new CsvReader<>(',', new FileReader(path), WeatherData.class)) {
            List<WeatherData> data = csv.readData();
            WeatherData minTempSpread = Analysis.argmin(data,
                                                        d -> d.maxTemperature - d.minTemperature);
            int dayWithSmallestTempSpread = minTempSpread.day;
            System.out.printf("Day with smallest temperature spread : %s%n",
                              dayWithSmallestTempSpread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: App <--football|--weather| > <path to file>");
    }

}
