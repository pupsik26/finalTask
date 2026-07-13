package ui;

import daryaClassStream.ModelBuilderClass.*;
import dataSource.consoleReader.ConsoleReader;
import dataSource.fileReader.FileReader;
import dataSource.randomData.Generator;
import fileWriter.FileWriter;
import makarSorting.EvenFieldSorter;
import makarSorting.SortFacade;
import makarSorting.Strategy.MergeSortStrategy;
import makarSorting.Strategy.QuickSortStrategy;
import makarSorting.Strategy.SmartSorter;
import makarSorting.Strategy.SortStrategy;
import ui.service.ElementCounter;
import daryaClassStream.daryaStream.Stream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Консольный интерфейс пользователя.
 */
public class ConsoleUI {

    private final Scanner scanner;
    private final SortFacade sortFacade;
    private final EvenFieldSorter evenFieldSorter;

    private int selectedClassType;
    private int selectedDataSourceType;
    private int collectionSize;
    private int selectedFieldIndex;
    private SortType selectedSortType;
    private SortAlgorithm selectedAlgorithm;
    private String filePath;
    private List<Object> currentData;
    private final ElementCounter elementCounter;
    private final FileWriter fileWriter;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
        this.sortFacade = new SortFacade();
        this.evenFieldSorter = new EvenFieldSorter();
        this.elementCounter = new ElementCounter();
        this.currentData = new ArrayList<>();
        this.fileWriter = new FileWriter();
        this.selectedSortType = SortType.NORMAL;
        this.selectedAlgorithm = SortAlgorithm.SMART;
    }

    public void run() {
        printWelcomeMessage();

        boolean isRunning = true;

        while (isRunning) {
            MenuPrinter.printMainMenu(
                    selectedClassType, selectedDataSourceType,
                    collectionSize, selectedFieldIndex,
                    selectedSortType, selectedAlgorithm
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

    private boolean isCommandMode(String input) {
        return !input.matches("^\\d+$");
    }

    private boolean handleCommand(String rawInput) {
        try {
            Command command = CommandParser.parse(rawInput);

            if (command.isHelp()) {
                MenuPrinter.printHelp();
                return true;
            }

            if (command.isExit()) {
                System.out.println("\nДо свидания!");
                return false;
            }

            if (command.isClear()) {
                resetState();
                MenuPrinter.printSuccess("Все настройки сброшены");
                return true;
            }

            if (command.isStart()) {
                return handleStartCommand(command);
            }

            if (command.getAction() == CommandAction.SAVE) {
                return handleSaveCommand(command);
            }

            if (command.getAction() == CommandAction.STREAM_DEMO) {
                handleStreamDemo();
                return true;
            }

            if (command.isUnknown()) {
                MenuPrinter.printError("Неизвестная команда: '" + rawInput.split("\\s+")[0] + "'");
                MenuPrinter.printInfo("Введите 'help' для справки");
                return true;
            }

            return true;

        } catch (CommandParser.CommandParseException e) {
            MenuPrinter.printError(e.getMessage());
            MenuPrinter.printInfo("Введите 'help' для справки");
            return true;
        }
    }

    private boolean handleSaveCommand(Command command) {
        if (currentData.isEmpty()) {
            MenuPrinter.printError("Нет данных для сохранения. Сначала загрузите данные.");
            return true;
        }

        String path = command.getOutputPath().orElse(null);
        if (path == null || path.isBlank()) {
            System.out.print("Введите путь для сохранения (например, result.csv): ");
            path = scanner.nextLine().trim();
        }

        executeSave(path);
        return true;
    }

    private void handleSaveExecution() {
        if (currentData.isEmpty()) {
            MenuPrinter.printError("Нет данных для сохранения. Сначала загрузите данные.");
            return;
        }

        System.out.print("Введите путь для сохранения (result.csv, result.json или result.xml): ");
        String path = scanner.nextLine().trim();

        if (path.isBlank()) {
            MenuPrinter.printError("Путь к файлу не может быть пустым.");
            return;
        }

        executeSave(path);
    }

    @SuppressWarnings("unchecked")
    private void executeSave(String filepath) {
        try {
            String lowerPath = filepath.toLowerCase();
            MenuPrinter.printInfo("Сохранение данных...");

            if (lowerPath.endsWith(".csv")) {
                fileWriter.writeCsv(filepath, currentData);
            } else if (lowerPath.endsWith(".json")) {
                fileWriter.writeJson(filepath, currentData);
            } else if (lowerPath.endsWith(".xml")) {
                fileWriter.writeXml(filepath, currentData);
            } else {
                MenuPrinter.printError("Неподдерживаемое расширение. Используйте .csv, .json или .xml");
                return;
            }

            MenuPrinter.printSuccess("Данные успешно сохранены в: " + filepath);

        } catch (Exception e) {
            MenuPrinter.printError("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    private void handleCountExecution() {
        if (currentData.isEmpty()) {
            MenuPrinter.printError("Сначала загрузите данные (выберите класс, источник и размер)!");
            return;
        }

        System.out.println("\n💡 Подсказка: скопируйте строку элемента из результата сортировки выше.");
        System.out.print("Введите строковое представление элемента для поиска: ");
        String targetValue = scanner.nextLine().trim();

        System.out.print("Введите количество потоков (по умолчанию 4, макс 16): ");
        String threadsInput = scanner.nextLine().trim();
        int threads = 4;
        if (!threadsInput.isEmpty()) {
            try {
                threads = Integer.parseInt(threadsInput);
                if (threads < 1 || threads > 16) {
                    threads = 4;
                }
            } catch (NumberFormatException e) {
                threads = 4;
            }
        }

        executeCount(targetValue, threads);
    }

    private void executeCount(String targetValue, int threads) {
        if (targetValue.isEmpty()) {
            MenuPrinter.printError("Значение для поиска не может быть пустым!");
            return;
        }

        MenuPrinter.printInfo("Запуск многопоточного подсчета (" + threads + " поток(ов))...");

        long startTime = System.nanoTime();

        long count = elementCounter.countOccurrences(currentData, targetValue, threads);

        long endTime = System.nanoTime();
        double elapsedMs = (endTime - startTime) / 1_000_000.0;

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       РЕЗУЛЬТАТ ПОДСЧЕТА               ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Искомый элемент: " + targetValue);
        System.out.println("Количество вхождений: " + count);
        System.out.println("Размер коллекции: " + currentData.size());
        System.out.printf("⏱ Время выполнения: %.3f мс%n%n", elapsedMs);
    }

    private boolean handleStartCommand(Command command) {
        boolean classChanged = command.getClassType().isPresent()
                && command.getClassType().getAsInt() != selectedClassType;

        command.getClassType().ifPresent(v -> selectedClassType = v);
        command.getDataSourceType().ifPresent(v -> selectedDataSourceType = v);
        command.getCollectionSize().ifPresent(v -> collectionSize = v);
        command.getFieldIndex().ifPresent(v -> selectedFieldIndex = v);
        command.getSortType().ifPresent(v -> selectedSortType = v);
        command.getAlgorithmCode().ifPresent(v -> selectedAlgorithm = SortAlgorithm.fromCode(v));
        command.getFilePath().ifPresent(v -> filePath = v);

        if (classChanged) {
            currentData.clear();
        }

        if (!validateParametersInteractive()) {
            return true;
        }

        executeSort();
        return true;
    }

    private boolean handleMenuChoice(String input) {
        try {
            int choice = Integer.parseInt(input);

            return switch (choice) {
                case 1 -> { handleClassSelection(); yield true; }
                case 2 -> { handleDataSourceSelection(); yield true; }
                case 3 -> { handleCollectionSizeSelection(); yield true; }
                case 4 -> { handleFieldSelection(); yield true; }
                case 5 -> { handleSortTypeSelection(); yield true; }
                case 6 -> { handleAlgorithmSelection(); yield true; }
                case 7 -> { handleSortExecution(); yield true; }
                case 8 -> { handleCountExecution(); yield true; }
                case 9 -> { handleSaveExecution(); yield true; }
                case 10 -> { handleStreamDemo(); yield true; }
                case 11 -> { System.out.println("\nДо свидания!"); yield false; }
                default -> {
                    MenuPrinter.printError("Выберите пункт от 1 до 11");
                    yield true;
                }
            };
        } catch (NumberFormatException e) {
            MenuPrinter.printError("Неверный ввод. Введите цифру или команду");
            return true;
        }
    }

    /**
     * Запускает демонстрацию возможностей Stream API (Дополнительное задание).
     */
    private void handleStreamDemo() {
        System.out.println("\n🚀 Запуск демонстрации Stream API...");
        System.out.println("⚠️ ВНИМАНИЕ: Для работы требуются файлы:");
        System.out.println("   - student.json");
        System.out.println("   - car.json");
        System.out.println("   - bus.json");
        System.out.println("   - barrel.json");
        System.out.println("   - user.json");
        System.out.println("   Расположенные в: src/main/dataSource/fileReader/examples/\n");

        try {
            // Создаем экземпляр класса из пакета Дарьи и запускаем демонстрацию
            Stream streamDemo = new Stream();
            streamDemo.demonstrateAllProcessors();

            MenuPrinter.printSuccess("Демонстрация Stream API успешно завершена!");
        } catch (Exception e) {
            MenuPrinter.printError("Критическая ошибка при выполнении демонстрации: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleClassSelection() {
        MenuPrinter.printClassSelectionMenu();
        selectedClassType = getIntInRange("Выберите класс (1-5): ", 1, 5);
        selectedFieldIndex = 0;
        currentData.clear();
        MenuPrinter.printSuccess("Выбран класс: " + getClassName(selectedClassType));
    }

    private void handleDataSourceSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        MenuPrinter.printDataSourceMenu();
        selectedDataSourceType = getIntInRange("Выберите источник (1-3): ", 1, 3);

        if (selectedDataSourceType == 3) {
            System.out.print("Введите путь к файлу (.csv, .json, .xml): ");
            filePath = scanner.nextLine().trim();
        }

        currentData.clear();
        MenuPrinter.printSuccess("Выбран источник: " + getDataSourceName(selectedDataSourceType));
    }

    private void handleCollectionSizeSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        collectionSize = getIntInRange("Введите размер коллекции (1-10000): ", 1, 10000);
        currentData.clear();
        MenuPrinter.printSuccess("Размер коллекции: " + collectionSize);
    }

    private void handleFieldSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        MenuPrinter.printFieldSelectionMenu(selectedClassType);
        selectedFieldIndex = getIntInRange("Выберите поле (1-3): ", 1, 3);

        String fieldName = getFieldName(selectedClassType, selectedFieldIndex);
        MenuPrinter.printSuccess("Выбрано поле: " + fieldName);
    }

    private void handleSortTypeSelection() {
        MenuPrinter.printSortTypeMenu();
        int choice = getIntInRange("Выберите тип сортировки (1-2): ", 1, 2);

        selectedSortType = (choice == 1) ? SortType.NORMAL : SortType.EVEN;
        MenuPrinter.printSuccess("Выбран тип сортировки: " + selectedSortType.getDescription());
    }

    private void handleAlgorithmSelection() {
        MenuPrinter.printAlgorithmMenu();
        int choice = getIntInRange("Выберите алгоритм (1-3): ", 1, 3);

        selectedAlgorithm = SortAlgorithm.fromCode(choice);
        MenuPrinter.printSuccess("Выбран алгоритм: " + selectedAlgorithm.getDescription());
    }

    private void handleSortExecution() {
        if (!validateParametersInteractive()) {
            return;
        }
        executeSort();
    }

    @SuppressWarnings("unchecked")
    private void executeSort() {
        try {
            if (currentData.isEmpty()) {
                loadData();
            }

            if (currentData.isEmpty()) {
                MenuPrinter.printError("Нет данных для сортировки");
                return;
            }

            String fieldName = getFieldName(selectedClassType, selectedFieldIndex);
            Class<?> modelClass = getModelClass(selectedClassType);

            MenuPrinter.printInfo("Начинаем сортировку...");

            long startTime = System.nanoTime();

            if (selectedSortType == SortType.EVEN) {
                sortFacade.sortEven(currentData, modelClass, fieldName);
            } else {
                if (selectedAlgorithm == SortAlgorithm.SMART) {
                    sortFacade.sort(currentData, modelClass, fieldName);
                } else {
                    SortStrategy<Object> strategy = createStrategy(selectedAlgorithm);
                    Comparator<Object> comparator = (Comparator<Object>)
                            new makarSorting.ComparatorRegister().findComparator(modelClass, fieldName);
                    strategy.sort(currentData, comparator);
                }
            }

            long endTime = System.nanoTime();

            MenuPrinter.printSortResults(currentData, fieldName, selectedSortType,
                    selectedAlgorithm, endTime - startTime);

        } catch (Exception e) {
            MenuPrinter.printError("Ошибка при сортировке: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Создаёт стратегию сортировки с ЯВНЫМ указанием типа <Object>.
     */
    private SortStrategy<Object> createStrategy(SortAlgorithm algorithm) {
        return switch (algorithm) {
            case MERGE -> new MergeSortStrategy<Object>();
            case QUICK -> new QuickSortStrategy<Object>();
            case SMART -> new SmartSorter<Object>();
        };
    }

    private void loadData() {
        MenuPrinter.printInfo("Загрузка данных...");

        try {
            currentData = switch (selectedDataSourceType) {
                case 1 -> loadManualData();
                case 2 -> loadRandomData();
                case 3 -> loadFileData();
                default -> throw new IllegalStateException("Неизвестный источник: " + selectedDataSourceType);
            };

            MenuPrinter.printSuccess("Загружено " + currentData.size() + " объектов");

        } catch (Exception e) {
            MenuPrinter.printError("Ошибка при загрузке данных: " + e.getMessage());
            currentData.clear();
        }
    }

    private List<Object> loadManualData() {
        ConsoleReader consoleReader = new ConsoleReader();

        return switch (selectedClassType) {
            case 1 -> new ArrayList<>(consoleReader.readUser(
                    "Введите количество пользователей: ",
                    "Введите имя пользователя %d: ",
                    "Введите пароль пользователя %d: ",
                    "Введите email пользователя %d: "
            ));
            case 2 -> new ArrayList<>(consoleReader.readStudent(
                    "Введите количество студентов: ",
                    "Введите номер группы студента %d: ",
                    "Введите средний балл студента %d: ",
                    "Введите номер зачетки студента %d: "
            ));
            case 3 -> new ArrayList<>(consoleReader.readCar(
                    "Введите количество автомобилей: ",
                    "Введите мощность автомобиля %d: ",
                    "Введите модель автомобиля %d: ",
                    "Введите год выпуска автомобиля %d: "
            ));
            case 4 -> new ArrayList<>(consoleReader.readBus(
                    "Введите количество автобусов: ",
                    "Введите номер автобуса %d: ",
                    "Введите модель автобуса %d: ",
                    "Введите пробег автобуса %d: "
            ));
            case 5 -> new ArrayList<>(consoleReader.readBarrel(
                    "Введите количество бочек: ",
                    "Введите объем бочки %d: ",
                    "Введите хранимый материал бочки %d: ",
                    "Введите материал бочки %d: "
            ));
            default -> throw new IllegalArgumentException("Неизвестный класс: " + selectedClassType);
        };
    }

    private List<Object> loadRandomData() {
        Generator generator = new Generator();

        return switch (selectedClassType) {
            case 1 -> new ArrayList<>(generator.readUsers(collectionSize));
            case 2 -> new ArrayList<>(generator.readStudents(collectionSize));
            case 3 -> new ArrayList<>(generator.readCars(collectionSize));
            case 4 -> new ArrayList<>(generator.readBus(collectionSize));
            case 5 -> new ArrayList<>(generator.readBarrels(collectionSize));
            default -> throw new IllegalArgumentException("Неизвестный класс: " + selectedClassType);
        };
    }

    private List<Object> loadFileData() throws IOException {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Путь к файлу не указан");
        }

        try {
            return switch (selectedClassType) {
                case 1 -> new ArrayList<>(FileReader.readUser(filePath));
                case 2 -> new ArrayList<>(FileReader.readStudent(filePath));
                case 3 -> new ArrayList<>(FileReader.readCar(filePath));
                case 4 -> new ArrayList<>(FileReader.readBus(filePath));
                case 5 -> new ArrayList<>(FileReader.readBarrel(filePath));
                default -> throw new IllegalArgumentException("Неизвестный класс: " + selectedClassType);
            };
        } catch (FileNotFoundException e) {
            throw new IOException("Файл не найден: " + filePath);
        } catch (IOException e) {
            throw new IOException("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private boolean validateParametersInteractive() {
        if (selectedClassType == 0) {
            MenuPrinter.printInfo("Не выбран класс");
            MenuPrinter.printClassSelectionMenu();
            selectedClassType = getIntInRange("Класс (1-5): ", 1, 5);
        }
        if (selectedDataSourceType == 0) {
            MenuPrinter.printInfo("Не выбран источник");
            MenuPrinter.printDataSourceMenu();
            selectedDataSourceType = getIntInRange("Источник (1-3): ", 1, 3);
        }
        if (collectionSize == 0) {
            collectionSize = getIntInRange("Размер (1-10000): ", 1, 10000);
        }
        if (selectedFieldIndex == 0) {
            MenuPrinter.printInfo("Не выбрано поле");
            MenuPrinter.printFieldSelectionMenu(selectedClassType);
            selectedFieldIndex = getIntInRange("Поле (1-3): ", 1, 3);
        }
        if (selectedSortType == null) {
            MenuPrinter.printInfo("Не выбран тип сортировки");
            MenuPrinter.printSortTypeMenu();
            int choice = getIntInRange("Тип сортировки (1-2): ", 1, 2);
            selectedSortType = (choice == 1) ? SortType.NORMAL : SortType.EVEN;
        }
        if (selectedAlgorithm == null) {
            MenuPrinter.printInfo("Не выбран алгоритм");
            MenuPrinter.printAlgorithmMenu();
            int choice = getIntInRange("Алгоритм (1-3): ", 1, 3);
            selectedAlgorithm = SortAlgorithm.fromCode(choice);
        }
        if (selectedDataSourceType == 3 && (filePath == null || filePath.isBlank())) {
            System.out.print("Введите путь к файлу: ");
            filePath = scanner.nextLine().trim();
        }
        return true;
    }

    private int getIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Значение должно быть от " + min + " до " + max);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число");
            }
        }
    }

    private Class<?> getModelClass(int type) {
        return switch (type) {
            case 1 -> User.class;
            case 2 -> Student.class;
            case 3 -> Car.class;
            case 4 -> Bus.class;
            case 5 -> Barrel.class;
            default -> throw new IllegalArgumentException("Неизвестный тип: " + type);
        };
    }

    private String getClassName(int type) {
        return switch (type) {
            case 1 -> "User";
            case 2 -> "Student";
            case 3 -> "Car";
            case 4 -> "Bus";
            case 5 -> "Barrel";
            default -> "Неизвестно";
        };
    }

    private String getDataSourceName(int type) {
        return switch (type) {
            case 1 -> "Ручной ввод";
            case 2 -> "Случайные данные";
            case 3 -> "Из файла";
            default -> "Неизвестно";
        };
    }

    private String getFieldName(int classType, int fieldIndex) {
        String[] fields = switch (classType) {
            case 1 -> new String[]{"name", "password", "email"};
            case 2 -> new String[]{"groupNumber", "averageGrade", "recordBookNumber"};
            case 3 -> new String[]{"power", "model", "year"};
            case 4 -> new String[]{"number", "model", "mileage"};
            case 5 -> new String[]{"volume", "storedMaterial", "material"};
            default -> new String[]{"field1", "field2", "field3"};
        };
        return fields[fieldIndex - 1];
    }

    private void resetState() {
        selectedClassType = 0;
        selectedDataSourceType = 0;
        collectionSize = 0;
        selectedFieldIndex = 0;
        selectedSortType = SortType.NORMAL;
        selectedAlgorithm = SortAlgorithm.SMART;
        filePath = null;
        currentData.clear();
    }

    private void printWelcomeMessage() {
        System.out.println("""
                
                ╔════════════════════════════════════════╗
                ║  ПРОГРАММА СОРТИРОВКИ ДАННЫХ           ║
                ║   Версия 5.0                           ║
                ║   Java 17                              ║
                ╚════════════════════════════════════════╝
                
                Поддерживаемые форматы файлов: CSV, JSON, XML
                Введите 'help' для справки.
                """);
    }
}