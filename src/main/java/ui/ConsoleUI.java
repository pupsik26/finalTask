package ui;

import com.sorting.data.DataSource;
import com.sorting.data.DataSourceFactory;
import com.sorting.factory.SortableFactory;
import com.sorting.model.Sortable;
import com.sorting.sorting.BubbleSortStrategy;
import com.sorting.sorting.MergeSortStrategy;
import com.sorting.sorting.QuickSortStrategy;
import com.sorting.sorting.SortContext;
import com.sorting.sorting.SortStrategy;
import ui.CommandParser.CommandParseException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Консольный интерфейс пользователя.
 * Поддерживает два режима работы:
 * <ul>
 *   <li>Интерактивный — пошаговый выбор через меню</li>
 *   <li>Командный — ввод команды целиком с флагами</li>
 * </ul>
 */
public class ConsoleUI {

    private final Scanner scanner;
    private final InputValidator inputValidator;

    // Состояние приложения
    private int selectedClassType;
    private int selectedDataSourceType;
    private int collectionSize;
    private int selectedFieldIndex;
    private int selectedAlgorithmType;
    private String filePath;
    private List<Sortable> currentData;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
        this.inputValidator = new InputValidator(scanner);
        this.currentData = new ArrayList<>();
    }

    /**
     * Запускает главный цикл программы.
     */
    public void run() {
        printWelcomeMessage();

        boolean isRunning = true;

        while (isRunning) {
            MenuPrinter.printMainMenu(
                    selectedClassType,
                    selectedDataSourceType,
                    collectionSize,
                    selectedFieldIndex,
                    selectedAlgorithmType
            );
            MenuPrinter.printCommandHint();

            System.out.print("\n> ");
            String rawInput = scanner.nextLine().trim();

            if (rawInput.isEmpty()) {
                continue;
            }

            if (isCommandMode(rawInput)) {
                isRunning = handleCommand(rawInput);
            } else {
                isRunning = handleMenuChoice(rawInput);
            }
        }
    }

    /**
     * Определяет, является ли ввод командой (содержит буквы) или цифрой меню.
     */
    private boolean isCommandMode(String input) {
        return !input.matches("^\\d+$");
    }

    /**
     * Обработка команды.
     *
     * @return false если нужно выйти из программы
     */
    private boolean handleCommand(String rawInput) {
        try {
            Command command = CommandParser.parse(rawInput);

            if (command.isHelp()) {
                MenuPrinter.printHelp();
                return true;
            }

            if (command.isExit()) {
                printFarewellMessage();
                return false;
            }

            if ("clear".equalsIgnoreCase(command.getAction())) {
                resetState();
                MenuPrinter.printSuccess("Все настройки сброшены");
                return true;
            }

            if (command.isStart()) {
                return handleStartCommand(command);
            }

            MenuPrinter.printError("Неизвестная команда: '" + command.getAction() + "'");
            MenuPrinter.printInfo("Введите 'help' для справки");
            return true;

        } catch (CommandParseException e) {
            MenuPrinter.printError(e.getMessage());
            MenuPrinter.printInfo("Введите 'help' для справки по командам");
            return true;
        }
    }

    /**
     * Обработка команды start.
     * Применяет флаги к текущему состоянию и запускает сортировку.
     */
    private boolean handleStartCommand(Command command) {
        command.getClassType().ifPresent(v -> selectedClassType = v);
        command.getDataSourceType().ifPresent(v -> selectedDataSourceType = v);
        command.getCollectionSize().ifPresent(v -> collectionSize = v);
        command.getFieldIndex().ifPresent(v -> selectedFieldIndex = v);
        command.getAlgorithmType().ifPresent(v -> selectedAlgorithmType = v);
        command.getFilePath().ifPresent(v -> filePath = v);

        if (!validateAllParametersInteractive()) {
            return true;
        }

        executeSort();
        return true;
    }

    /**
     * Интерактивно запрашивает недостающие параметры.
     *
     * @return true если все параметры заполнены
     */
    private boolean validateAllParametersInteractive() {
        if (selectedClassType == 0) {
            MenuPrinter.printInfo("Не выбран класс. Выберите:");
            MenuPrinter.printClassSelectionMenu();
            selectedClassType = inputValidator.getIntInRange("Класс (1-5): ", 1, 5);
        }

        if (selectedDataSourceType == 0) {
            MenuPrinter.printInfo("Не выбран источник данных. Выберите:");
            MenuPrinter.printDataSourceMenu();
            selectedDataSourceType = inputValidator.getIntInRange("Источник (1-3): ", 1, 3);
        }

        if (collectionSize == 0) {
            collectionSize = inputValidator.getPositiveInt("Размер коллекции (1-10000): ", 10000);
        }

        if (selectedFieldIndex == 0) {
            MenuPrinter.printInfo("Не выбрано поле. Выберите:");
            MenuPrinter.printFieldSelectionMenu(selectedClassType);
            selectedFieldIndex = inputValidator.getIntInRange("Поле (1-3): ", 1, 3);
        }

        if (selectedAlgorithmType == 0) {
            MenuPrinter.printInfo("Не выбран алгоритм. Выберите:");
            MenuPrinter.printAlgorithmMenu();
            selectedAlgorithmType = inputValidator.getIntInRange("Алгоритм (1-3): ", 1, 3);
        }

        return true;
    }

    /**
     * Обработка выбора пункта меню (цифра).
     *
     * @return false если нужно выйти
     */
    private boolean handleMenuChoice(String input) {
        try {
            int choice = Integer.parseInt(input);

            return switch (choice) {
                case 1 -> { handleClassSelection(); yield true; }
                case 2 -> { handleDataSourceSelection(); yield true; }
                case 3 -> { handleCollectionSizeSelection(); yield true; }
                case 4 -> { handleFieldSelection(); yield true; }
                case 5 -> { handleAlgorithmSelection(); yield true; }
                case 6 -> { handleSortExecution(); yield true; }
                case 7 -> { printFarewellMessage(); yield false; }
                default -> {
                    MenuPrinter.printError("Выберите пункт от 1 до 7");
                    yield true;
                }
            };
        } catch (NumberFormatException e) {
            MenuPrinter.printError("Неверный ввод. Введите цифру (1-7) или команду (help, start, exit)");
            return true;
        }
    }

    private void handleClassSelection() {
        MenuPrinter.printClassSelectionMenu();
        selectedClassType = inputValidator.getIntInRange("Выберите класс (1-5): ", 1, 5);
        selectedFieldIndex = 0;
        currentData.clear();
        MenuPrinter.printSuccess("Выбран класс: " + SortableFactory.getClassName(selectedClassType));
    }

    private void handleDataSourceSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        MenuPrinter.printDataSourceMenu();
        selectedDataSourceType = inputValidator.getIntInRange("Выберите источник (1-3): ", 1, 3);

        if (selectedDataSourceType == 3) {
            filePath = inputValidator.getFilePath("Введите путь к файлу: ");
        }

        currentData.clear();
        MenuPrinter.printSuccess("Выбран источник: " + getDataSourceName(selectedDataSourceType));
    }

    private void handleCollectionSizeSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        collectionSize = inputValidator.getPositiveInt("Введите размер коллекции (1-10000): ", 10000);
        currentData.clear();
        MenuPrinter.printSuccess("Размер коллекции: " + collectionSize);
    }

    private void handleFieldSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        MenuPrinter.printFieldSelectionMenu(selectedClassType);
        selectedFieldIndex = inputValidator.getIntInRange("Выберите поле (1-3): ", 1, 3);

        String fieldName = SortableFactory.getFieldNames(selectedClassType)[selectedFieldIndex - 1];
        MenuPrinter.printSuccess("Выбрано поле: " + fieldName);
    }

    private void handleAlgorithmSelection() {
        MenuPrinter.printAlgorithmMenu();
        selectedAlgorithmType = inputValidator.getIntInRange("Выберите алгоритм (1-3): ", 1, 3);
        MenuPrinter.printSuccess("Выбран алгоритм: " + getAlgorithmName(selectedAlgorithmType));
    }

    private void handleSortExecution() {
        if (!validateAllParametersInteractive()) {
            return;
        }
        executeSort();
    }

    /**
     * Выполнение сортировки (используется и в командном, и в интерактивном режиме).
     */
    private void executeSort() {
        try {
            if (currentData.isEmpty()) {
                loadData();
            }

            if (currentData.isEmpty()) {
                MenuPrinter.printError("Нет данных для сортировки");
                return;
            }

            SortStrategy<Sortable> strategy = createSortStrategy();
            SortContext<Sortable> sortContext = new SortContext<>(strategy);

            Comparator<Sortable> comparator = createComparator();

            long startTime = System.nanoTime();
            sortContext.sort(currentData, comparator);
            long endTime = System.nanoTime();
            double elapsedMs = (endTime - startTime) / 1_000_000.0;

            String fieldName = SortableFactory.getFieldNames(selectedClassType)[selectedFieldIndex - 1];
            String algorithmName = getAlgorithmName(selectedAlgorithmType);

            MenuPrinter.printSortResults(currentData, fieldName, algorithmName);
            System.out.printf("⏱ Время сортировки: %.3f мс%n%n", elapsedMs);

        } catch (Exception e) {
            MenuPrinter.printError("Ошибка при сортировке: " + e.getMessage());
        }
    }

    /**
     * Загрузка данных из источника.
     */
    private void loadData() {
        MenuPrinter.printInfo("Загрузка данных...");

        try {
            DataSource<Sortable> dataSource = DataSourceFactory.createDataSource(
                    selectedDataSourceType, selectedClassType);

            currentData = dataSource.getData(collectionSize);

            MenuPrinter.printSuccess("Загружено " + currentData.size() + " объектов");

        } catch (Exception e) {
            MenuPrinter.printError("Ошибка при загрузке данных: " + e.getMessage());
            currentData.clear();
        }
    }

    /**
     * Создание стратегии сортировки.
     */
    private SortStrategy<Sortable> createSortStrategy() {
        return switch (selectedAlgorithmType) {
            case 1 -> new BubbleSortStrategy<>();
            case 2 -> new QuickSortStrategy<>();
            case 3 -> new MergeSortStrategy<>();
            default -> throw new IllegalStateException("Неизвестный алгоритм: " + selectedAlgorithmType);
        };
    }

    /**
     * Создание компаратора для выбранного поля.
     */
    @SuppressWarnings("unchecked")
    private Comparator<Sortable> createComparator() {
        int fieldIndex = selectedFieldIndex - 1;

        return (obj1, obj2) -> {
            Comparable<Object> value1 = (Comparable<Object>) obj1.getFieldByIndex(fieldIndex);
            Comparable<Object> value2 = (Comparable<Object>) obj2.getFieldByIndex(fieldIndex);
            return value1.compareTo(value2);
        };
    }

    /**
     * Сброс всего состояния.
     */
    private void resetState() {
        selectedClassType = 0;
        selectedDataSourceType = 0;
        collectionSize = 0;
        selectedFieldIndex = 0;
        selectedAlgorithmType = 0;
        filePath = null;
        currentData.clear();
    }

    private void printWelcomeMessage() {
        System.out.println("""
                
                ╔════════════════════════════════════════╗
                ║                                        ║
                ║   ПРОГРАММА СОРТИРОВКИ ДАННЫХ          ║
                ║                                        ║
                ║   Версия 2.0 (с командным режимом)     ║
                ║   Java 17                              ║
                ║                                        ║
                ╚════════════════════════════════════════╝
                
                Введите 'help' для справки по командам.
                Или выберите пункт меню цифрой (1-7).
                """);
    }

    private void printFarewellMessage() {
        System.out.println("""
                
                ╔════════════════════════════════════════╗
                ║   До свидания!                         ║
                ║   Спасибо за использование программы!  ║
                ╚════════════════════════════════════════╝
                """);
    }

    // ==================== ВСПОМОГАТЕЛЬНЫЕ ====================

    private String getDataSourceName(int type) {
        return switch (type) {
            case 1 -> "Ручной ввод";
            case 2 -> "Случайные данные";
            case 3 -> "Из файла";
            default -> "Неизвестно";
        };
    }

    private String getAlgorithmName(int type) {
        return switch (type) {
            case 1 -> "Bubble Sort";
            case 2 -> "Quick Sort";
            case 3 -> "Merge Sort";
            default -> "Неизвестно";
        };
    }
}
