package ui;

import java.util.HashMap;
import java.util.Map;

/**
 * Парсер команд пользователя.
 * Поддерживает флаги: -c, -s, -n, -f, -e, -a, -p
 */
public final class CommandParser {

    private static final Map<String, String> FLAG_ALIASES = new HashMap<>();

    static {
        FLAG_ALIASES.put("-c", "--class");
        FLAG_ALIASES.put("-s", "--source");
        FLAG_ALIASES.put("-n", "--size");
        FLAG_ALIASES.put("-f", "--field");
        FLAG_ALIASES.put("-p", "--path");
        FLAG_ALIASES.put("-e", "--even");
        FLAG_ALIASES.put("-a", "--algo");
        FLAG_ALIASES.put("-t", "--threads");
        FLAG_ALIASES.put("-v", "--value");
        FLAG_ALIASES.put("-o", "--output");
    }

    private CommandParser() {}

    public static Command parse(String input) throws CommandParseException {
        if (input == null || input.isBlank()) {
            throw new CommandParseException("Команда не может быть пустой");
        }

        String[] tokens = input.trim().split("\\s+");
        CommandAction action = CommandAction.fromString(tokens[0]);

        var builder = new Command.Builder().setAction(action);

        if (action == CommandAction.HELP
                || action == CommandAction.EXIT
                || action == CommandAction.CLEAR
                || action == CommandAction.UNKNOWN) {
            return builder.build();
        }

        int i = 1;
        while (i < tokens.length) {
            String token = tokens[i];

            if (!token.startsWith("-")) {
                throw new CommandParseException(
                        "Неожиданный аргумент: '" + token + "'. Используйте флаги (-c, -s, -n, -f, -e, -a, -p)");
            }

            String flag = normalizeFlag(token);

            if (flag.equals("--even")) {
                builder.setSortType(SortType.EVEN);
                i++;
                continue;
            }

            if (i + 1 >= tokens.length) {
                throw new CommandParseException("Флаг '" + token + "' требует значение");
            }

            String value = tokens[i + 1];
            applyFlag(builder, flag, value);

            i += 2;
        }

        return builder.build();
    }

    private static String normalizeFlag(String flag) {
        return FLAG_ALIASES.getOrDefault(flag.toLowerCase(), flag.toLowerCase());
    }

    private static void applyFlag(Command.Builder builder, String flag, String value)
            throws CommandParseException {

        switch (flag) {
            case "--class" -> builder.setClassType(parseIntFlag(flag, value, 1, 5));
            case "--source" -> builder.setDataSourceType(parseIntFlag(flag, value, 1, 3));
            case "--size" -> builder.setCollectionSize(parseIntFlag(flag, value, 1, 10000));
            case "--field" -> builder.setFieldIndex(parseIntFlag(flag, value, 1, 3));
            case "--algo" -> builder.setAlgorithmCode(parseIntFlag(flag, value, 1, 3));
            case "--threads" -> builder.setThreadCount(parseIntFlag(flag, value, 1, 16));
            case "--path" -> builder.setFilePath(value);
            case "--value" -> builder.setSearchValue(value);
            case "--output" -> builder.setOutputPath(value);
            default -> throw new CommandParseException("Неизвестный флаг: '" + flag + "'");
        }
    }

    private static int parseIntFlag(String flag, String value, int min, int max)
            throws CommandParseException {

        try {
            int parsed = Integer.parseInt(value);
            if (parsed < min || parsed > max) {
                throw new CommandParseException(
                        "Значение флага '" + flag + "' должно быть от " + min + " до " + max);
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new CommandParseException(
                    "Значение флага '" + flag + "' должно быть числом: '" + value + "'");
        }
    }

    public static class CommandParseException extends Exception {
        public CommandParseException(String message) {
            super(message);
        }
    }
}