package ui;

/**
 * Перечисление доступных команд пользователя.
 */
public enum CommandAction {

    HELP("help", "Показать справку"),
    START("start", "Запустить сортировку"),
    COUNT("count", "Подсчитать вхождения элемента"),
    SAVE("save", "Сохранить результат в файл"),
    EXIT("exit", "Выйти из программы"),
    CLEAR("clear", "Сбросить настройки"),
    UNKNOWN("", "Неизвестная команда");

    private final String command;
    private final String description;

    CommandAction(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static CommandAction fromString(String input) {
        if (input == null || input.isBlank()) {
            return UNKNOWN;
        }

        String normalized = input.trim().toLowerCase();

        for (CommandAction action : values()) {
            if (action.command.equals(normalized)) {
                return action;
            }
        }

        return UNKNOWN;
    }

    public boolean isUnknown() {
        return this == UNKNOWN;
    }
}