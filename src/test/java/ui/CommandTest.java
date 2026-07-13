package ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Command — модель команды")
class CommandTest {

    @Nested
    @DisplayName("Builder — создание команды")
    class BuilderTests {

        @Test
        @DisplayName("Создаёт команду со всеми параметрами")
        void shouldBuildWithAllParams() {
            Command cmd = new Command.Builder()
                    .setAction(CommandAction.START)
                    .setClassType(3)
                    .setDataSourceType(2)
                    .setCollectionSize(10)
                    .setFieldIndex(1)
                    .setSortType(SortType.EVEN)
                    .setAlgorithmCode(2)
                    .setFilePath("data.csv")
                    .build();

            assertEquals(CommandAction.START, cmd.getAction());
            assertEquals(3, cmd.getClassType().orElse(0));
            assertEquals(2, cmd.getDataSourceType().orElse(0));
            assertEquals(10, cmd.getCollectionSize().orElse(0));
            assertEquals(1, cmd.getFieldIndex().orElse(0));
            assertEquals(SortType.EVEN, cmd.getSortType().orElse(null));
            assertEquals(2, cmd.getAlgorithmCode().orElse(0));
            assertEquals("data.csv", cmd.getFilePath().orElse(""));
        }

        @Test
        @DisplayName("Создаёт команду с частичными параметрами")
        void shouldBuildWithPartialParams() {
            Command cmd = new Command.Builder()
                    .setAction(CommandAction.START)
                    .setClassType(1)
                    .build();

            assertTrue(cmd.getClassType().isPresent());
            assertFalse(cmd.getDataSourceType().isPresent());
            assertFalse(cmd.getCollectionSize().isPresent());
        }

        @Test
        @DisplayName("Создаёт команду без параметров")
        void shouldBuildWithoutParams() {
            Command cmd = new Command.Builder()
                    .setAction(CommandAction.HELP)
                    .build();

            assertTrue(cmd.isHelp());
            assertFalse(cmd.getClassType().isPresent());
        }
    }

    @Nested
    @DisplayName("Проверки типа команды")
    class IsMethodsTests {

        @Test
        @DisplayName("isHelp/isStart/isExit/isClear работают корректно")
        void shouldDetectCommandType() {
            assertTrue(new Command.Builder().setAction(CommandAction.HELP).build().isHelp());
            assertTrue(new Command.Builder().setAction(CommandAction.START).build().isStart());
            assertTrue(new Command.Builder().setAction(CommandAction.EXIT).build().isExit());
            assertTrue(new Command.Builder().setAction(CommandAction.CLEAR).build().isClear());
            assertTrue(new Command.Builder().setAction(CommandAction.UNKNOWN).build().isUnknown());
        }

        @Test
        @DisplayName("isHelp() возвращает false для START")
        void isHelpShouldBeFalseForStart() {
            Command cmd = new Command.Builder().setAction(CommandAction.START).build();
            assertFalse(cmd.isHelp());
            assertFalse(cmd.isExit());
        }
    }

    @Nested
    @DisplayName("hasAllRequiredParams — проверка обязательных параметров")
    class HasAllRequiredParamsTests {

        @Test
        @DisplayName("Возвращает true когда все обязательные параметры заданы")
        void shouldReturnTrueWhenAllPresent() {
            Command cmd = new Command.Builder()
                    .setAction(CommandAction.START)
                    .setClassType(1)
                    .setDataSourceType(2)
                    .setCollectionSize(10)
                    .setFieldIndex(1)
                    .build();

            assertTrue(cmd.hasAllRequiredParams());
        }

        @Test
        @DisplayName("Возвращает false когда нет classType")
        void shouldReturnFalseWhenClassTypeMissing() {
            Command cmd = new Command.Builder()
                    .setAction(CommandAction.START)
                    .setDataSourceType(2)
                    .setCollectionSize(10)
                    .setFieldIndex(1)
                    .build();

            assertFalse(cmd.hasAllRequiredParams());
        }

        @Test
        @DisplayName("Возвращает false когда нет fieldIndex")
        void shouldReturnFalseWhenFieldIndexMissing() {
            Command cmd = new Command.Builder()
                    .setAction(CommandAction.START)
                    .setClassType(1)
                    .setDataSourceType(2)
                    .setCollectionSize(10)
                    .build();

            assertFalse(cmd.hasAllRequiredParams());
        }
    }

    @Test
    @DisplayName("toString() содержит ключевую информацию")
    void toStringShouldContainKeyInfo() {
        Command cmd = new Command.Builder()
                .setAction(CommandAction.START)
                .setClassType(3)
                .setDataSourceType(2)
                .build();

        String str = cmd.toString();
        assertTrue(str.contains("start"));
        assertTrue(str.contains("class=3"));
        assertTrue(str.contains("source=2"));
    }

    @Test
    @DisplayName("Команда иммутабельна — повторные вызовы возвращают одинаковые значения")
    void shouldBeImmutable() {
        Command cmd = new Command.Builder()
                .setAction(CommandAction.START)
                .setClassType(1)
                .build();

        assertEquals(cmd.getClassType(), cmd.getClassType());
        assertEquals(cmd.getAction(), cmd.getAction());
    }
}