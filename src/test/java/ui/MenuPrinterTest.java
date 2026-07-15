package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MenuPrinter — вывод меню в консоль")
class MenuPrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("printMainMenu выводит все 11 пунктов")
    void shouldPrintAllMenuItems() {
        MenuPrinter.printMainMenu(0, 0, 0, 0, null, null);
        String output = outContent.toString();

        assertTrue(output.contains("1."));
        assertTrue(output.contains("2."));
        assertTrue(output.contains("3."));
        assertTrue(output.contains("4."));
        assertTrue(output.contains("5."));
        assertTrue(output.contains("6."));
        assertTrue(output.contains("7."));
        assertTrue(output.contains("8."));
        assertTrue(output.contains("9."));
        assertTrue(output.contains("10."));
        assertTrue(output.contains("11."));
    }

    @Test
    @DisplayName("printMainMenu показывает выбранный класс")
    void shouldShowSelectedClass() {
        MenuPrinter.printMainMenu(3, 0, 0, 0, null, null);
        String output = outContent.toString();
        assertTrue(output.contains("Car"));
    }

    @Test
    @DisplayName("printMainMenu показывает размер коллекции")
    void shouldShowCollectionSize() {
        MenuPrinter.printMainMenu(1, 2, 100, 0, null, null);
        String output = outContent.toString();
        assertTrue(output.contains("[100]"));
    }

    @Test
    @DisplayName("printHelp выводит справку с флагами")
    void shouldPrintHelp() {
        MenuPrinter.printHelp();
        String output = outContent.toString();

        assertTrue(output.contains("СПРАВКА"));
        assertTrue(output.contains("-c"));
        assertTrue(output.contains("-s"));
        assertTrue(output.contains("-n"));
        assertTrue(output.contains("-f"));
        assertTrue(output.contains("-e"));
        assertTrue(output.contains("-a"));
        assertTrue(output.contains("-o"));
        assertTrue(output.contains("-t"));
        assertTrue(output.contains("-v"));
    }

    @Test
    @DisplayName("printError выводит сообщение с ❌")
    void shouldPrintError() {
        MenuPrinter.printError("тест ошибки");
        String output = outContent.toString();
        assertTrue(output.contains("ОШИБКА"));
        assertTrue(output.contains("тест ошибки"));
    }

    @Test
    @DisplayName("printSuccess выводит сообщение с ✅")
    void shouldPrintSuccess() {
        MenuPrinter.printSuccess("успех");
        String output = outContent.toString();
        assertTrue(output.contains("успех"));
    }

    @Test
    @DisplayName("printInfo выводит сообщение с ℹ")
    void shouldPrintInfo() {
        MenuPrinter.printInfo("информация");
        String output = outContent.toString();
        assertTrue(output.contains("информация"));
    }

    @Test
    @DisplayName("printClassSelectionMenu выводит 5 классов")
    void shouldPrintClassMenu() {
        MenuPrinter.printClassSelectionMenu();
        String output = outContent.toString();
        assertTrue(output.contains("User"));
        assertTrue(output.contains("Student"));
        assertTrue(output.contains("Car"));
        assertTrue(output.contains("Bus"));
        assertTrue(output.contains("Barrel"));
    }

    @Test
    @DisplayName("printAlgorithmMenu выводит 3 алгоритма")
    void shouldPrintAlgorithmMenu() {
        MenuPrinter.printAlgorithmMenu();
        String output = outContent.toString();
        assertTrue(output.contains("Merge"));
        assertTrue(output.contains("Quick"));
        assertTrue(output.contains("Smart"));
    }

    @Test
    @DisplayName("printSortTypeMenu выводит 2 типа")
    void shouldPrintSortTypeMenu() {
        MenuPrinter.printSortTypeMenu();
        String output = outContent.toString();
        assertTrue(output.contains("Обычная"));
        assertTrue(output.contains("чётн"));
    }
}