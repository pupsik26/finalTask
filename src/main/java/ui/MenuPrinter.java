package ui;

/**
 * Утилитарный класс для вывода меню и сообщений.
 */
public final class MenuPrinter {

    private MenuPrinter() {}

    public static void printMainMenu(int selectedClass, int selectedDataSource,
                                     int collectionSize, int selectedField) {

        System.out.println("\n════════════════════════════════════════╗");
        System.out.println("║         ГЛАВНОЕ МЕНЮ                   ");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("1. Выбрать класс для сортировки" +
                getStatusText(selectedClass, () -> getClassName(selectedClass)));
        System.out.println("2. Выбрать источник данных" +
                getStatusText(selectedDataSource, () -> getDataSourceName(selectedDataSource)));
        System.out.println("3. Указать размер коллекции" +
                (collectionSize > 0 ? " [" + collectionSize + "]" : ""));
        System.out.println("4. Выбрать поле для сортировки" +
                getStatusText(selectedField, () -> getFieldName(selectedClass, selectedField)));
        System.out.println("5. Выполнить сортировку");
        System.out.println("6. Выход");
        System.out.println();
    }

    public static void printClassSelectionMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ВЫБОР КЛАССА ДЛЯ СОРТИРОВКИ       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. User (Имя, Пароль, Email)");
        System.out.println("2. Student (Группа, Средний балл, Зачетка)");
        System.out.println("3. Car (Мощность, Модель, Год)");
        System.out.println("4. Bus (Номер, Модель, Пробег)");
        System.out.println("5. Barrel (Объем, Материал хранения, Материал)");
        System.out.println();
    }

    public static void printDataSourceMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       ВЫБОР ИСТОЧНИКА ДАННЫХ           ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Ручной ввод");
        System.out.println("2. Случайные данные");
        System.out.println("3. Из файла");
        System.out.println();
    }

    public static void printFieldSelectionMenu(int classType) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     ВЫБОР ПОЛЯ ДЛЯ СОРТИРОВКИ          ║");
        System.out.println("╚════════════════════════════════════════╝");

        String[] fields = getFieldNames(classType);
        for (int i = 0; i < fields.length; i++) {
            System.out.println((i + 1) + ". " + fields[i]);
        }
        System.out.println();
    }

    public static void printSortResults(java.util.List<?> data, String fieldName, long elapsedMs) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       РЕЗУЛЬТАТЫ СОРТИРОВКИ            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Поле сортировки: " + fieldName);
        System.out.println("Количество элементов: " + data.size());
        System.out.println("────────────────────────────────────────");

        for (int i = 0; i < Math.min(data.size(), 20); i++) {
            System.out.println((i + 1) + ". " + data.get(i));
        }

        if (data.size() > 20) {
            System.out.println("... и еще " + (data.size() - 20) + " объектов");
        }
        System.out.println();
        System.out.printf("⏱ Время сортировки: %.3f мс%n%n", (double)elapsedMs / 1_000_000.0);
    }

    public static void printError(String message) {
        System.out.println("\n❌ ОШИБКА: " + message + "\n");
    }

    public static void printInfo(String message) {
        System.out.println("\nℹ " + message + "\n");
    }

    public static void printSuccess(String message) {
        System.out.println("\n✅ " + message + "\n");
    }

    public static void printHelp() {
        System.out.println("""
            
            ╔══════════════════════════════════════════════════════╗
            ║                    СПРАВКА                           ║
            ╚══════════════════════════════════════════════════════╝

            📌 РЕЖИМЫ РАБОТЫ:
              1. Интерактивный — выбирайте пункты меню цифрами
              2. Командный — вводите команду целиком с флагами

            📌 ДОСТУПНЫЕ КОМАНДЫ:
              help              Показать эту справку
              exit              Выйти из программы
              clear             Сбросить настройки
              start [флаги]     Запустить сортировку

            📌 ФЛАГИ:
              -c, --class    <1-5>   Класс (1-User, 2-Student, 3-Car, 4-Bus, 5-Barrel)
              -s, --source   <1-3>   Источник (1-ручной, 2-рандом, 3-файл)
              -n, --size     <1-10000> Размер коллекции
              -f, --field    <1-3>   Поле для сортировки
              -p, --path     <путь>  Путь к файлу (для source=3)

            📌 ПРИМЕРЫ:
              start -c 3 -s 2 -n 10 -f 1
              ➜ Сортировка Car по мощности (10 шт, рандом)

              start -c 2 -s 2 -n 50 -f 2
              ➜ Сортировка Student по среднему баллу (50 шт)

            """);
    }

    public static void printCommandHint() {
        System.out.println("💡 Подсказка: введите 'help' для справки или команду вида: start -c 1 -s 2 -n 10 -f 1");
    }

    private static String getStatusText(int value, java.util.function.Supplier<String> textProvider) {
        if (value > 0) {
            return " [" + textProvider.get() + "]";
        }
        return "";
    }

    private static String getClassName(int type) {
        return switch (type) {
            case 1 -> "User";
            case 2 -> "Student";
            case 3 -> "Car";
            case 4 -> "Bus";
            case 5 -> "Barrel";
            default -> "Неизвестно";
        };
    }

    private static String getDataSourceName(int type) {
        return switch (type) {
            case 1 -> "Ручной ввод";
            case 2 -> "Случайные данные";
            case 3 -> "Из файла";
            default -> "Неизвестно";
        };
    }

    private static String[] getFieldNames(int classType) {
        return switch (classType) {
            case 1 -> new String[]{"name", "password", "email"};
            case 2 -> new String[]{"groupNumber", "gpa", "recordBookNumber"};
            case 3 -> new String[]{"power", "model", "year"};
            case 4 -> new String[]{"number", "model", "mileage"};
            case 5 -> new String[]{"volume", "storedMaterial", "material"};
            default -> new String[]{"field1", "field2", "field3"};
        };
    }

    private static String getFieldName(int classType, int fieldIndex) {
        if (classType == 0 || fieldIndex == 0) return "Не выбрано";
        String[] fields = getFieldNames(classType);
        return fieldIndex >= 1 && fieldIndex <= 3 ? fields[fieldIndex - 1] : "Неизвестно";
    }
}