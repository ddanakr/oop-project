package universitysystem.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class ConsoleUtils {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleUtils() {
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    public static int readInt(String prompt) {
        return getIntInput(prompt);
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
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
            Integer parsed = tryParseInt(getInput(prompt));
            if (parsed != null) {
                return parsed;
            }
            System.out.println("Please enter a valid number.");
        }
    }

    public static void printHeader(String title) {
        String safe = title == null ? "" : title.trim();
        int width = Math.max(36, safe.length());
        System.out.println();
        System.out.println("=".repeat(width));
        System.out.println(centerPad(safe, width));
        System.out.println("=".repeat(width));
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
            System.out.printf("%d. %s%n", i + 1, String.valueOf(options.get(i)));
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
            if (row == null) {
                continue;
            }
            for (int i = 0; i < columns; i++) {
                String cell = i < row.size() && row.get(i) != null ? row.get(i) : "";
                widths[i] = Math.max(widths[i], cell.length());
            }
        }

        String separator = buildSeparator(widths);
        System.out.println(separator);
        if (headers != null && !headers.isEmpty()) {
            printRow(headers, widths);
            System.out.println(separator);
        }
        if (safeRows.isEmpty()) {
            System.out.println("|" + centerPad(" (no data) ", totalWidth(widths)) + "|");
        } else {
            for (List<String> row : safeRows) {
                printRow(row, widths);
            }
        }
        System.out.println(separator);
    }

    private static void printRow(List<String> cells, int[] widths) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < widths.length; i++) {
            String cell = i < cells.size() && cells.get(i) != null ? cells.get(i) : "";
            sb.append(" ").append(padRight(cell, widths[i])).append(" |");
        }
        System.out.println(sb);
    }

    private static String buildSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int width : widths) {
            sb.append("-".repeat(width + 2)).append("+");
        }
        return sb.toString();
    }

    private static int totalWidth(int[] widths) {
        int total = 0;
        for (int width : widths) {
            total += width + 3;
        }
        return Math.max(0, total - 1);
    }

    public static String padRight(String value, int width) {
        String safe = value == null ? "" : value;
        if (safe.length() >= width) {
            return safe;
        }
        return safe + " ".repeat(width - safe.length());
    }

    public static String centerPad(String value, int width) {
        String safe = value == null ? "" : value;
        int pad = Math.max(0, width - safe.length());
        int left = pad / 2;
        int right = pad - left;
        return " ".repeat(left) + safe + " ".repeat(right);
    }

    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        SCANNER.nextLine();
    }

    private static Integer tryParseInt(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
