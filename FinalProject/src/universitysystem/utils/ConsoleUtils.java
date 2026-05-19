package universitysystem.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ConsoleUtils {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleUtils() {}

    public static void printHeader(String title) {
        String safe = title == null ? "" : title.trim();
        System.out.println();
        if (!safe.isEmpty()) {
            System.out.println(repeat('=', safe.length()));
            System.out.println(safe);
            System.out.println(repeat('=', safe.length()));
        }
    }

    public static void printMenu(String title, String... options) {
        List<String> list = new ArrayList<>();
        if (options != null) {
            for (String option : options) {
                list.add(option);
            }
        }
        printMenu(title, list);
    }

    public static void printMenu(String title, List<String> options) {
        printHeader(title);
        if (options == null || options.isEmpty()) {
            System.out.println("(no options)");
            return;
        }
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, String.valueOf(options.get(i)));
        }
    }

    public static String getInput(String prompt) {
        if (prompt != null && !prompt.trim().isEmpty()) {
            System.out.print(prompt);
        }
        return SCANNER.nextLine();
    }

    public static int getIntInput(String prompt) {
        while (true) {
            String input = getInput(prompt);
            Integer parsed = tryParseInt(input);
            if (parsed != null) return parsed;
            System.out.println("Please enter a valid number.");
        }
    }

    public static void printTable(List<String> headers, List<List<String>> rows) {
        if ((headers == null || headers.isEmpty()) && (rows == null || rows.isEmpty())) {
            System.out.println("(empty)");
            return;
        }

        List<List<String>> safeRows = rows == null ? new ArrayList<>() : rows;
        int columns = headers != null ? headers.size() : (safeRows.isEmpty() ? 0 : safeRows.get(0).size());
        int[] widths = new int[columns];

        if (headers != null) {
            for (int i = 0; i < columns; i++) {
                String header = i < headers.size() && headers.get(i) != null ? headers.get(i) : "";
                widths[i] = Math.max(widths[i], header.length());
            }
        }

        for (List<String> row : safeRows) {
            if (row == null) continue;
            for (int i = 0; i < columns; i++) {
                String cell = i < row.size() && row.get(i) != null ? row.get(i) : "";
                widths[i] = Math.max(widths[i], cell.length());
            }
        }

        if (headers != null && !headers.isEmpty()) {
            printTableRow(headers, widths);
            printTableSeparator(widths);
        }

        for (List<String> row : safeRows) {
            if (row == null) continue;
            printTableRow(row, widths);
        }
    }

    private static void printTableSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            if (i > 0) sb.append("-+-");
            sb.append(repeat('-', Math.max(1, widths[i])));
        }
        System.out.println(sb.toString());
    }

    private static void printTableRow(List<String> row, int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            if (i > 0) sb.append(" | ");
            String cell = row != null && i < row.size() && row.get(i) != null ? row.get(i) : "";
            sb.append(padRight(cell, widths[i]));
        }
        System.out.println(sb.toString());
    }

    private static String padRight(String value, int width) {
        String safe = value == null ? "" : value;
        if (safe.length() >= width) return safe;
        return safe + repeat(' ', width - safe.length());
    }

    private static Integer tryParseInt(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        if (trimmed.isEmpty()) return null;
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static String repeat(char ch, int count) {
        if (count <= 0) return "";
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
}
