package itu.matelas.demo.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;

public class Generateur {
    private static final Random random = new Random();

    public static int generateInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static double generateDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static double generateDecimal(double min, double max, int decimalPlaces) {
        double value = generateDouble(min, max);
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }

    public static LocalDate genererDateOuvrable(LocalDate debut, LocalDate fin) {
        LocalDate randomDate;
        do {
            long start = debut.toEpochDay();
            long end = fin.toEpochDay();
            long randomEpochDay = start + random.nextLong(end - start + 1);
            randomDate = LocalDate.ofEpochDay(randomEpochDay);
        } while (randomDate.getDayOfWeek() == DayOfWeek.SATURDAY || randomDate.getDayOfWeek() == DayOfWeek.SUNDAY);
        return randomDate;
    }


    public static String formatNumber(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');

        DecimalFormat formatter = new DecimalFormat("#,###.#######",symbols);
        return formatter.format(number);
    }
}
