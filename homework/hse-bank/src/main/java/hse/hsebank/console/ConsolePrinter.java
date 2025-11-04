package hse.hsebank.console;

/**
 * Utility class for console output formatting
 */
public class ConsolePrinter {

    public static void printWelcome() {
        printSeparator();
        System.out.println("=== HSE Bank: Financial Accounting System ===");
        System.out.println("Welcome to the financial management system!");
        printSeparator();
    }

    public static void printGoodbye() {
        printSeparator();
        System.out.println("Thank you for using HSE Bank System!");
        System.out.println("Goodbye!");
        printSeparator();
    }

    public static void printMenu(String title, String[] options) {
        printSeparator();
        System.out.println("=== " + title + " ===");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }
        System.out.println("0. Exit");
        printSeparator();
    }

    public static void printSubMenu(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void printSuccess(String message) {
        System.out.println(" " + message);
    }

    public static void printError(String message) {
        System.out.println(" Error: " + message);
    }

    public static void printInfo(String message) {
        System.out.println(" " + message);
    }

    public static void printWarning(String message) {
        System.out.println(" " + message);
    }

    public static void printSeparator() {
        System.out.println("----------------------------------------");
    }

    public static void printDoubleSeparator() {
        System.out.println("========================================");
    }

    public static void printTableHeader(String[] headers) {
        printSeparator();
        System.out.print("|");
        for (String header : headers) {
            System.out.printf(" %-15s |", header);
        }
        System.out.println();
        printSeparator();
    }

    public static void printTableRow(String[] cells) {
        System.out.print("|");
        for (String cell : cells) {
            System.out.printf(" %-15s |", cell);
        }
        System.out.println();
    }

    public static void printBalance(double balance) {
        String color = balance >= 0 ? "\u001B[32m" : "\u001B[31m"; // Green for positive, red for negative
        String reset = "\u001B[0m";
        System.out.printf("Current balance: %s$%.2f%s%n", color, balance, reset);
    }

    public static void printSectionTitle(String title) {
        System.out.println();
        printDoubleSeparator();
        System.out.println("  " + title);
        printDoubleSeparator();
    }
}