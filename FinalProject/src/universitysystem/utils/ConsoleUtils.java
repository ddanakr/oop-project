package universitysystem.utils;

import java.util.List;
import java.util.Scanner;


public class ConsoleUtils {

    private static final Scanner scanner = new Scanner(System.in);

    // ── Input 

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid number.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid number.");
            }
        }
    }

    // ── Tables

    /**
     * Print Table
     * headers = ["Name", "GPA", "Year"]
     * rows    = [["Alice", "3.8", "2"], ["Bob", "3.5", "3"]]
     */

    public static void printTable(List<String> headers, List<List<String>> rows) {

        int[] widths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        for (List<String> row : rows) {
            for (int i = 0; i < row.size() && i < widths.length; i++) {
                widths[i] = Math.max(widths[i], row.get(i) == null ? 4 : row.get(i).length());
            }
        }

        String separator = buildSeparator(widths);

        // Top border
        System.out.println(separator.replace('┼', '┬').replace('├', '┌').replace('┤', '┐'));
        // Header
        printRow(headers, widths);
        System.out.println(separator);
        // Rows
        if (rows.isEmpty()) {
            System.out.println("│" + centerPad(" (no data) ", totalWidth(widths)) + "│");
        } else {
            for (List<String> row : rows) {
                printRow(row, widths);
            }
        }
        // Bottom border
        System.out.println(separator.replace('┼', '┴').replace('├', '└').replace('┤', '┘'));
    }

    private static void printRow(List<String> cells, int[] widths) {
        StringBuilder sb = new StringBuilder("│");
        for (int i = 0; i < widths.length; i++) {
            String cell = (i < cells.size() && cells.get(i) != null) ? cells.get(i) : "";
            sb.append(" ").append(padRight(cell, widths[i])).append(" │");
        }
        System.out.println(sb);
    }

    private static String buildSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("├");
        for (int i = 0; i < widths.length; i++) {
            sb.append("─".repeat(widths[i] + 2));
            sb.append(i < widths.length - 1 ? "┼" : "┤");
        }
        return sb.toString();
    }

    private static int totalWidth(int[] widths) {
        int total = 0;
        for (int w : widths) total += w + 3;
        return total - 1;
    }

    // ── Formatting

    public static String padRight(String s, int width) {
        return String.format("%-" + width + "s", s);
    }

    public static String centerPad(String s, int width) {
        int pad = Math.max(0, width - s.length());
        int left = pad / 2;
        int right = pad - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }

    // ── Console Header

    public static void printHeader(String title) {
        int width = 36;
        System.out.println("═".repeat(width));
        System.out.println(centerPad(title, width));
        System.out.println("═".repeat(width));
    }

    // ── For pauses between actions

    public static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}