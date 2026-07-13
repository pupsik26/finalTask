package ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommandAction — парсинг строк команд")
class CommandActionTest {

    @Nested
    @DisplayName("fromString — распознавание команд")
    class FromStringTests {

        @Test
        @DisplayName("Распознаёт 'help' как HELP")
        void shouldParseHelp() {
            assertEquals(CommandAction.HELP, CommandAction.fromString("help"));
        }

        @Test
        @DisplayName("Распознаёт 'start' как START")
        void shouldParseStart() {
            assertEquals(CommandAction.START, CommandAction.fromString("start"));
        }

        @Test
        @DisplayName("Распознаёт 'exit' как EXIT")
        void shouldParseExit() {
            assertEquals(CommandAction.EXIT, CommandAction.fromString("exit"));
        }

        @Test
        @DisplayName("Распознаёт 'clear' как CLEAR")
        void shouldParseClear() {
            assertEquals(CommandAction.CLEAR, CommandAction.fromString("clear"));
        }

        @Test
        @DisplayName("Распознаёт 'count' как COUNT")
        void shouldParseCount() {
            assertEquals(CommandAction.COUNT, CommandAction.fromString("count"));
        }

        @Test
        @DisplayName("Распознаёт 'save' как SAVE")
        void shouldParseSave() {
            assertEquals(CommandAction.SAVE, CommandAction.fromString("save"));
        }
    }

    @Nested
    @DisplayName("Регистронезависимость и пробелы")
    class CaseAndTrimTests {

        @ParameterizedTest
        @ValueSource(strings = {"HELP", "Help", "hElP", "  help  ", "\tstart\t"})
        @DisplayName("Регистр и пробелы не должны влиять на результат")
        void shouldBeCaseInsensitiveAndTrimSpaces(String input) {
            String normalized = input.toLowerCase().trim();
            CommandAction expected = CommandAction.fromString(normalized);
            assertEquals(expected, CommandAction.fromString(input));
        }
    }

    @Nested
    @DisplayName("Неизвестные и некорректные значения")
    class UnknownAndInvalidTests {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   ", "unknown", "foobar", "startt"})
        @DisplayName("Возвращает UNKNOWN для null, пустых и неизвестных команд")
        void shouldReturnUnknown(String input) {
            assertEquals(CommandAction.UNKNOWN, CommandAction.fromString(input));
        }
    }

    @Nested
    @DisplayName("Геттеры")
    class GetterTests {

        @Test
        @DisplayName("getCommand() возвращает строковое имя команды")
        void shouldReturnCommandName() {
            assertEquals("help", CommandAction.HELP.getCommand());
            assertEquals("start", CommandAction.START.getCommand());
        }

        @Test
        @DisplayName("getDescription() возвращает непустое описание")
        void shouldReturnDescription() {
            assertNotNull(CommandAction.HELP.getDescription());
            assertFalse(CommandAction.HELP.getDescription().isEmpty());
        }

        @Test
        @DisplayName("isUnknown() работает корректно")
        void shouldDetectUnknown() {
            assertTrue(CommandAction.UNKNOWN.isUnknown());
            assertFalse(CommandAction.HELP.isUnknown());
        }
    }
}