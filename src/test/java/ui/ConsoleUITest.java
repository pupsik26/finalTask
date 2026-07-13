package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConsoleUI — интеграционные тесты")
class ConsoleUITest {

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

    private ConsoleUI createUI(String input) {
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        return new ConsoleUI(scanner);
    }

    @Test
    @DisplayName("Приветствие отображается при запуске")
    void shouldShowWelcomeMessage() {
        createUI("10\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("ПРОГРАММА СОРТИРОВКИ"));
    }

    @Test
    @DisplayName("Команда exit корректно завершает работу")
    void shouldExitOnExitCommand() {
        createUI("exit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("До свидания"));
    }

    @Test
    @DisplayName("Пункт 10 (выход) завершает работу")
    void shouldExitOnMenuItem10() {
        createUI("10\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("До свидания"));
    }

    @Test
    @DisplayName("Команда help показывает справку")
    void shouldShowHelpOnHelpCommand() {
        createUI("help\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("СПРАВКА"));
        assertTrue(output.contains("start"));
    }

    @Test
    @DisplayName("Команда clear сбрасывает настройки")
    void shouldClearStateOnClearCommand() {
        createUI("clear\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("сброшены"));
    }

    @Test
    @DisplayName("Выбор класса через меню (цифра 1, потом 3)")
    void shouldSelectClassThroughMenu() {
        createUI("1\n3\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("Car"));
    }

    @Test
    @DisplayName("Невалидный ввод (буквы вместо цифр) обрабатывается с ошибкой")
    void shouldHandleInvalidInput() {
        createUI("abc\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("Неизвестная команда") || output.contains("Ошибка"));
    }

    @Test
    @DisplayName("Выбор несуществующего пункта меню (>10) выдаёт ошибку")
    void shouldHandleOutOfRangeMenuChoice() {
        createUI("99\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("1 до 10"));
    }

    @Test
    @DisplayName("Неизвестная команда выдаёт сообщение об ошибке")
    void shouldHandleUnknownCommand() {
        createUI("foobar\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("Неизвестная команда") || output.contains("foobar"));
    }

    @Test
    @DisplayName("Попытка сохранения без загруженных данных выдаёт ошибку")
    void shouldFailToSaveWithoutData() {
        createUI("save\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("Нет данных"));
    }

    @Test
    @DisplayName("Попытка подсчёта без загруженных данных выдаёт ошибку")
    void shouldFailToCountWithoutData() {
        createUI("count\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("Нет данных") || output.contains("загрузите"));
    }

    @Test
    @DisplayName("Выбор источника без класса — ошибка")
    void shouldFailToSelectSourceWithoutClass() {
        createUI("2\nexit\n").run();
        String output = outContent.toString();
        assertTrue(output.contains("Сначала выберите класс"));
    }
}