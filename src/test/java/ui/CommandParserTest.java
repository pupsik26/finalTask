package ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CommandParser — парсер команд")
class CommandParserTest {

    @Nested
    @DisplayName("Простые команды (без флагов)")
    class SimpleCommandTests {

        @Test
        void shouldParseHelp() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("help");
            assertEquals(CommandAction.HELP, cmd.getAction());
        }

        @Test
        void shouldParseExit() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("exit");
            assertEquals(CommandAction.EXIT, cmd.getAction());
        }

        @Test
        void shouldParseClear() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("clear");
            assertEquals(CommandAction.CLEAR, cmd.getAction());
        }

        @Test
        void shouldParseCount() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("count");
            assertEquals(CommandAction.COUNT, cmd.getAction());
        }

        @Test
        @DisplayName("Регистронезависимость: 'HELP' == 'help'")
        void shouldBeCaseInsensitive() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("HELP");
            assertEquals(CommandAction.HELP, cmd.getAction());
        }
    }

    @Nested
    @DisplayName("Команда start с флагами")
    class StartCommandTests {

        @Test
        @DisplayName("Парсит все флаги (короткие)")
        void shouldParseAllShortFlags() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("start -c 3 -s 2 -n 10 -f 1 -a 2");

            assertEquals(CommandAction.START, cmd.getAction());
            assertEquals(3, cmd.getClassType().orElse(0));
            assertEquals(2, cmd.getDataSourceType().orElse(0));
            assertEquals(10, cmd.getCollectionSize().orElse(0));
            assertEquals(1, cmd.getFieldIndex().orElse(0));
            assertEquals(2, cmd.getAlgorithmCode().orElse(0));
        }

        @Test
        @DisplayName("Парсит длинные флаги --class --source")
        void shouldParseLongFlags() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("start --class 1 --source 3 --size 50 --field 2 --algo 3");

            assertEquals(1, cmd.getClassType().orElse(0));
            assertEquals(3, cmd.getDataSourceType().orElse(0));
            assertEquals(50, cmd.getCollectionSize().orElse(0));
            assertEquals(2, cmd.getFieldIndex().orElse(0));
            assertEquals(3, cmd.getAlgorithmCode().orElse(0));
        }

        @Test
        @DisplayName("Парсит флаг -e (even)")
        void shouldParseEvenFlag() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("start -c 1 -s 2 -n 10 -f 1 -e");
            assertEquals(SortType.EVEN, cmd.getSortType().orElse(null));
        }

        @Test
        @DisplayName("Парсит флаг -p (path)")
        void shouldParsePathFlag() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("start -c 3 -s 3 -p data.csv -n 5 -f 1 -a 1");
            assertEquals("data.csv", cmd.getFilePath().orElse(""));
        }

        @Test
        @DisplayName("Парсит флаги -t (threads) и -v (value) для команды count")
        void shouldParseThreadAndValueFlags() throws CommandParser.CommandParseException {
            // ЗАМЕНЕНО: вместо outputPath тестируем реальные поля threadCount и searchValue
            Command cmd = CommandParser.parse("count -t 8 -v \"Car{power=150}\"");
            assertEquals(8, cmd.getThreadCount().orElse(0));
            assertEquals("Car{power=150}", cmd.getSearchValue().orElse("").replace("\"", ""));
        }

        @Test
        @DisplayName("Частичные флаги — остальные параметры пусты")
        void shouldHandlePartialFlags() throws CommandParser.CommandParseException {
            Command cmd = CommandParser.parse("start -c 1 -s 2");
            assertTrue(cmd.getClassType().isPresent());
            assertTrue(cmd.getDataSourceType().isPresent());
            assertFalse(cmd.getCollectionSize().isPresent());
            assertFalse(cmd.getFieldIndex().isPresent());
        }
    }

    @Nested
    @DisplayName("Ошибки парсинга")
    class ErrorTests {

        @Test
        @DisplayName("Бросает исключение для пустого ввода")
        void shouldThrowOnEmptyInput() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse(""));
        }

        @Test
        @DisplayName("Бросает исключение для null")
        void shouldThrowOnNullInput() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse(null));
        }

        @Test
        @DisplayName("Бросает исключение для неизвестного флага")
        void shouldThrowOnUnknownFlag() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse("start -x 5"));
        }

        @Test
        @DisplayName("Бросает исключение для флага без значения")
        void shouldThrowOnFlagWithoutValue() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse("start -c"));
        }

        @Test
        @DisplayName("Бросает исключение для значения вне диапазона (class = 99)")
        void shouldThrowOnOutOfRange() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse("start -c 99"));
        }

        @Test
        @DisplayName("Бросает исключение для нечислового значения")
        void shouldThrowOnNonNumeric() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse("start -c abc"));
        }

        @Test
        @DisplayName("Бросает исключение для лишнего аргумента")
        void shouldThrowOnExtraArgument() {
            assertThrows(CommandParser.CommandParseException.class,
                    () -> CommandParser.parse("start -c 1 extra"));
        }
    }
}