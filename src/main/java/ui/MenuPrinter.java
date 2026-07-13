package ui;

/**
 * Утилитарный класс для вывода меню и сообщений.
 */
public final class MenuPrinter {

    private MenuPrinter() {}

    public static void printMainMenu(int selectedClass, int selectedDataSource,
                                     int collectionSize, int selectedField,
                                     SortType sortType, SortAlgorithm algorithm) {

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
        System.out.println("5. Выбрать тип сортировки" +
                (sortType != null ? " [" + sortType.getDescription() + "]" : ""));
        System.out.println("6. Выбрать алгоритм сортировки" +
                (algorithm != null ? " [" + algorithm.getDescription() + "]" : ""));
        System.out.println("7. Выполнить сортировку");
        System.out.println("8. Подсчитать вхождения элемента (многопоточно)"); // <-- НОВОЕ
        System.out.println("9. Выход");
        System.out.println();
    }

    public static void printSortTypeMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ВЫБОР ТИПА СОРТИРОВКИ             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Обычная сортировка");
        System.out.println("2. Сортировка только чётных значений");
        System.out.println("   (только для числовых полей, доп. задание 1)");
        System.out.println();
    }

    public static void printAlgorithmMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    ВЫБОР АЛГОРИТМА СОРТИРОВКИ          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("1. Merge Sort (сортировка слиянием)");
        System.out.println("2. Quick Sort (быстрая сортировка)");
        System.out.println("3. Smart Sorter (автоматический выбор)");
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

    public static void printSortResults(java.util.List<?> data, String fieldName,
                                        SortType sortType, SortAlgorithm algorithm, long elapsedMs) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       РЕЗУЛЬТАТЫ СОРТИРОВКИ            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Тип сортировки: " + sortType.getDescription());
        System.out.println("Алгоритм: " + algorithm.getDescription());
        System.out.println("Поле сортировки: " + fieldName);
        System.out.println("Количество элементов: " + data.size());
        System.out.println("────────────────────────────────────────");

        int limit = Math.min(data.size(), 20);
        for (int i = 0; i < limit; i++) {
            System.out.println((i + 1) + ". " + data.get(i));
        }

        if (data.size() > 20) {
            System.out.println("... и еще " + (data.size() - 20) + " объектов");
        }
        System.out.println();
        System.out.printf(" Время сортировки: %.3f мс%n%n", (double) elapsedMs / 1_000_000.0);
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
            
             РЕЖИМЫ РАБОТЫ:
              1. Интерактивный — выбирайте пункты меню цифрами
              2. Командный — вводите команду целиком с флагами
            
            📌 ДОСТУПНЫЕ КОМАНДЫ:
              help              Показать эту справку
              exit              Выйти из программы
              clear             Сбросить настройки
              start [флаги]     Запустить сортировку
              count [флаги]     Подсчитать вхождения элемента (многопоточно)
            
            📌 ФЛАГИ:
              -c, --class    <1-5>   Класс (1-User, 2-Student, 3-Car, 4-Bus, 5-Barrel)
              -s, --source   <1-3>   Источник (1-ручной, 2-рандом, 3-файл)
              -n, --size     <1-10000> Размер коллекции
              -f, --field    <1-3>   Поле для сортировки
              -e, --even             Сортировка только чётных значений (доп. задание 1)
              -a, --algo     <1-3>   Алгоритм (1-Merge, 2-Quick, 3-Smart)
              -p, --path     <путь>  Путь к файлу (для source=3)
              -t, --threads  <1-16>  Количество потоков для подсчета (для count)
              -v, --value    <строка> Искомое значение (для count)
            
            📌 ТИПЫ СОРТИРОВКИ:
              Обычная — сортирует все объекты по выбранному полю
              Чётные  — сортирует только объекты с чётными значениями
                        числового поля, остальные остаются на местах
            
            📌 АЛГОРИТМЫ СОРТИРОВКИ:
              1. Merge Sort — сортировка слиянием
              2. Quick Sort — быстрая сортировка
              3. Smart Sorter — автоматически выбирает:
                 - Quick Sort для списков < 100 элементов
                 - Merge Sort для списков >= 100 элементов
            
            📌 ПРИМЕРЫ:
              start -c 3 -s 2 -n 10 -f 1 -a 1
              ➜ Сортировка Car по мощности (Merge Sort, 10 шт, рандом)
              
              start -c 3 -s 2 -n 10 -f 1 -e -a 2
              ➜ Сортировка только чётных значений мощности Car (Quick Sort)
              
              start -c 2 -s 3 -p students.csv -f 2 -a 3
              ➜ Сортировка Student по gpa из файла (Smart Sorter)
            
            """);
    }

    public static void printCommandHint() {
        System.out.println("💡 Подсказка: введите 'help' для справки или команду вида: start -c 1 -s 2 -n 10 -f 1 -a 1 [-e]");
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
        if (classType == 0 || fieldIndex == 0) {
            return "Не выбрано";
        }
        String[] fields = getFieldNames(classType);
        return fieldIndex >= 1 && fieldIndex <= 3 ? fields[fieldIndex - 1] : "Неизвестно";
    }
}