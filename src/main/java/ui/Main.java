package ui;

import java.util.Scanner;

/**
 * Точка входа в приложение.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleUI ui = new ConsoleUI(scanner);

        try {
            ui.run();
        } catch (Exception e) {
            System.err.println("❌ КРИТИЧЕСКАЯ ОШИБКА: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            scanner.close();
        }
    }
}