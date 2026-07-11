package ui;

import ModelBuilderClass.Builder.*;
import ModelBuilderClass.ModelClass.*;
import ivans.task.validators.objects.ObjectValidator;
import ivans.task.validators.objects.ObjectValidatorFactory;
import makarSorting.SortFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Консольный интерфейс пользователя.
 * Интегрируется с SortFacade для сортировки.
 */
public class ConsoleUI {

    private final Scanner scanner;
    private final ObjectValidator validator;
    private final SortFacade sortFacade;
    private final Random random;

    // Состояние приложения
    private int selectedClassType;
    private int selectedDataSourceType;
    private int collectionSize;
    private int selectedFieldIndex;
    private String filePath;
    private List<Object> currentData;

    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
        this.validator = ObjectValidatorFactory.getValidator();
        this.sortFacade = new SortFacade();
        this.random = new Random();
        this.currentData = new ArrayList<>();
    }

    public void run() {
        printWelcomeMessage();

        boolean isRunning = true;

        while (isRunning) {
            MenuPrinter.printMainMenu(
                    selectedClassType, selectedDataSourceType,
                    collectionSize, selectedFieldIndex
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

        } catch (CommandParser.CommandParseException e) {
            MenuPrinter.printError(e.getMessage());
            MenuPrinter.printInfo("Введите 'help' для справки");
            return true;
        }
    }

    private boolean handleStartCommand(Command command) {
        command.getClassType().ifPresent(v -> selectedClassType = v);
        command.getDataSourceType().ifPresent(v -> selectedDataSourceType = v);
        command.getCollectionSize().ifPresent(v -> collectionSize = v);
        command.getFieldIndex().ifPresent(v -> selectedFieldIndex = v);
        command.getFilePath().ifPresent(v -> filePath = v);

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
                case 1 -> {
                    handleClassSelection();
                    yield true;
                }
                case 2 -> {
                    handleDataSourceSelection();
                    yield true;
                }
                case 3 -> {
                    handleCollectionSizeSelection();
                    yield true;
                }
                case 4 -> {
                    handleFieldSelection();
                    yield true;
                }
                case 5 -> {
                    handleSortExecution();
                    yield true;
                }
                case 6 -> {
                    System.out.println("\nДо свидания!");
                    yield false;
                }
                default -> {
                    MenuPrinter.printError("Выберите пункт от 1 до 6");
                    yield true;
                }
            };
        } catch (NumberFormatException e) {
            MenuPrinter.printError("Неверный ввод. Введите цифру или команду");
            return true;
        }
    }

    private void handleClassSelection() {
        MenuPrinter.printClassSelectionMenu();
        selectedClassType = getIntInRange("Выберите класс (1-5): ", 1, 5);
        selectedFieldIndex = 0;
        currentData.clear();
        MenuPrinter.printSuccess("Выбран класс: " + (MenuPrinter.class.getDeclaredMethods().length > 0));
    }

    private void handleDataSourceSelection() {
        if (selectedClassType == 0) {
            MenuPrinter.printError("Сначала выберите класс!");
            return;
        }
        MenuPrinter.printDataSourceMenu();
        selectedDataSourceType = getIntInRange("Выберите источник (1-3): ", 1, 3);

        if (selectedDataSourceType == 3) {
            System.out.print("Введите путь к файлу: ");
            filePath = scanner.nextLine().trim();
        }

        currentData.clear();
        MenuPrinter.printSuccess("Выбран источник: " +
                (selectedDataSourceType == 1 ? "Ручной ввод" :
                        selectedDataSourceType == 2 ? "Случайные данные" : "Из файла"));
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

    private void handleSortExecution() {
        if (!validateParametersInteractive()) {
            return;
        }
        executeSort();
    }

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

            sortFacade.sort((List<Object>) currentData, modelClass, fieldName);

            long endTime = System.nanoTime();

            MenuPrinter.printSortResults(currentData, fieldName, endTime - startTime);

        } catch (Exception e) {
            MenuPrinter.printError("Ошибка при сортировке: " + e.getMessage());
            e.printStackTrace();
        }
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

    private List<Object> loadManualData() throws Exception {
        List<Object> data = new ArrayList<>();

        System.out.println("\nВведите " + collectionSize + " объектов:");

        for (int i = 0; i < collectionSize; i++) {
            System.out.println("\n--- Объект " + (i + 1) + " ---");
            Object obj = createObjectFromInput(selectedClassType);
            validator.validate(obj);
            data.add(obj);
        }

        return data;
    }

    private List<Object> loadRandomData() throws Exception {
        List<Object> data = new ArrayList<>();

        for (int i = 0; i < collectionSize; i++) {
            Object obj = createRandomObject(selectedClassType);
            validator.validate(obj);
            data.add(obj);
        }

        return data;
    }

    private List<Object> loadFileData() {
        // TODO: Реализуйте чтение из файла
        MenuPrinter.printError("Чтение из файла пока не реализовано");
        return new ArrayList<>();
    }

    private Object createObjectFromInput(int classType) {
        return switch (classType) {
            case 1 -> {
                System.out.print("Имя: ");
                String name = scanner.nextLine();
                System.out.print("Пароль: ");
                String password = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                yield new UserBuilder().setName(name).setPassword(password).setEmail(email).build();
            }
            case 2 -> {
                System.out.print("Группа: ");
                String group = scanner.nextLine();
                System.out.print("Средний балл: ");
                double gpa = Double.parseDouble(scanner.nextLine());
                System.out.print("Номер зачетки: ");
                String bookNum = scanner.nextLine();
                yield new StudentBuilder()
                        .setGroupNumber(group)
                        .setAverageGrade(gpa)
                        .setRecordBookNumber(bookNum)
                        .build();
            }
            case 3 -> {
                System.out.print("Мощность: ");
                int power = Integer.parseInt(scanner.nextLine());
                System.out.print("Модель: ");
                String model = scanner.nextLine();
                System.out.print("Год: ");
                int year = Integer.parseInt(scanner.nextLine());
                yield new CarBuilder()
                        .setPower(power)
                        .setModel(model)
                        .setYear(year)
                        .build();
            }
            case 4 -> {
                System.out.print("Номер: ");
                int number = scanner.nextInt();
                System.out.print("Модель: ");
                String model = scanner.nextLine();
                System.out.print("Пробег: ");
                int mileage = Integer.parseInt(scanner.nextLine());
                yield new BusBuilder()
                        .setNumber(number)
                        .setModel(model)
                        .setMileage(mileage)
                        .build();
            }
            case 5 -> {
                System.out.print("Объем: ");
                double volume = Double.parseDouble(scanner.nextLine());
                System.out.print("Хранимый материал: ");
                String stored = scanner.nextLine();
                System.out.print("Материал: ");
                String material = scanner.nextLine();
                yield new BarrelBuilder()
                        .setVolume(volume)
                        .setStoredMaterial(stored)
                        .setMaterial(material)
                        .build();
            }
            default -> throw new IllegalArgumentException("Неизвестный класс: " + classType);
        };
    }

    private Object createRandomObject(int classType) {
        return switch (classType) {
            case 1 -> new UserBuilder()
                    .setName("User" + random.nextInt(1000))
                    .setPassword("pass" + random.nextInt(9999))
                    .setEmail("user" + random.nextInt(1000) + "@mail.com")
                    .build();
            case 2 -> new StudentBuilder()
                    .setGroupNumber("Группа-" + (100 + random.nextInt(90)))
                    .setAverageGrade(Math.round((2.0 + random.nextDouble() * 3.0) * 100.0) / 100.0)
                    .setRecordBookNumber("RB" + random.nextInt(900000) + 100000)
                    .build();
            case 3 -> new CarBuilder()
                    .setPower(50 + random.nextInt(350))
                    .setModel(new String[]{"Toyota", "BMW", "Audi", "Mercedes"}[random.nextInt(4)])
                    .setYear(1990 + random.nextInt(34))
                    .build();
            case 4 -> new BusBuilder()
                    .setNumber(Integer.parseInt(String.valueOf(100 + random.nextInt(900))))
                    .setModel(new String[]{"Mercedes", "Volvo", "MAN"}[random.nextInt(3)])
                    .setMileage(random.nextInt(500000))
                    .build();
            case 5 -> new BarrelBuilder()
                    .setVolume(Math.round((10.0 + random.nextDouble() * 990.0) * 100.0) / 100.0)
                    .setStoredMaterial(new String[]{"Вода", "Нефть", "Газ"}[random.nextInt(3)])
                    .setMaterial(new String[]{"Сталь", "Пластик", "Дерево"}[random.nextInt(3)])
                    .build();
            default -> throw new IllegalArgumentException("Неизвестный класс: " + classType);
        };
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

    private String getFieldName(int classType, int fieldIndex) {
        String[] fields = switch (classType) {
            case 1 -> new String[]{"name", "password", "email"};
            case 2 -> new String[]{"groupNumber", "gpa", "recordBookNumber"};
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
        filePath = null;
        currentData.clear();
    }

    private void printWelcomeMessage() {
        System.out.println("""
                
                ╔════════════════════════════════════════╗
                ║   ПРОГРАММА СОРТИРОВКИ ДАННЫХ          ║
                ║   Версия 2.0 (с SortFacade)            ║
                ║   Java 17                              ║
                ╚════════════════════════════════════════╝
                
                Введите 'help' для справки.
                """);
    }
}