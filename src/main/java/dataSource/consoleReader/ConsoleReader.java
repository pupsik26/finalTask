package dataSource.consoleReader;

import daryaClassStream.ModelBuilderClass.*;
import ivans.task.exceptions.InvalidInputException;
import ivans.task.validators.manual.input.ManualInputValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ConsoleReader {
    private String readLineFromConsole(String request, int row, Scanner scanner) {
        do {
            System.out.printf(request, row);
            try {
                return ManualInputValidator.requireNonBlank(scanner.nextLine(), "");
            } catch(InvalidInputException e) {
                System.out.printf(e.getMessage());
            }
        } while (true);
    }

    private int readIntFromConsole(String request, int row, Scanner scanner) {
        do {
            System.out.printf(request, row);
            try {
                return ManualInputValidator.readInt(scanner.nextLine(), "");
            } catch(InvalidInputException e) {
                System.out.printf(e.getMessage());
            }
        } while (true);
    }

    private double readDoubleFromConsole(String request, int row, Scanner scanner) {
        do {
            System.out.printf(request, row);
            try {
                return ManualInputValidator.readDouble(scanner.nextLine(), "");
            } catch(InvalidInputException e) {
                System.out.printf(e.getMessage());
            }
        } while (true);
    }

    public List<Barrel> readBarrel(String requestQuantity, String requestVolume, String requestStoredMaterial, String requestMaterial) {
        List<Barrel> barrels = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print(requestQuantity);
        int quantity = scanner.nextInt();
        double volume;
        String storedMaterial;
        String material;

        for (int i = 1; i <= quantity; i++) {
            volume = readDoubleFromConsole(requestVolume, i, scanner);
            storedMaterial = readLineFromConsole(requestStoredMaterial, i, scanner);
            material = readLineFromConsole(requestMaterial, i, scanner);

            barrels.add(
                    Barrel.builder()
                            .setVolume(volume)
                            .setStoredMaterial(storedMaterial)
                            .setMaterial(material)
                            .build()
            );
        }

        return barrels;
    }

    public List<Bus> readBus(String requestQuantity, String requestNumber, String requestModel, String requestMileage) {
        List<Bus> busses = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print(requestQuantity);
        int quantity = scanner.nextInt();
        int number;
        String model;
        int mileage;

        for (int i = 1; i <= quantity; i++) {
            number = readIntFromConsole(requestNumber, i, scanner);
            model = readLineFromConsole(requestModel, i, scanner);
            mileage = readIntFromConsole(requestMileage, i, scanner);

            busses.add(
                    Bus.builder()
                            .setNumber(String.valueOf(number))
                            .setModel(model)
                            .setMileage(mileage)
                            .build()
            );
        }

        return busses;
    }

    public List<Car> readCar(String requestQuantity, String requestPower, String requestModel, String requestYear) {
        List<Car> cars = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print(requestQuantity);
        int quantity = scanner.nextInt();
        int power;
        String model;
        int year;

        for (int i = 1; i <= quantity; i++) {
            power = readIntFromConsole(requestPower, i, scanner);
            model = readLineFromConsole(requestModel, i, scanner);
            year = readIntFromConsole(requestYear, i, scanner);

            cars.add(
                    Car.builder()
                            .setPower(power)
                            .setModel(model)
                            .setYear(year)
                            .build()
            );
        }

        return cars;
    }

    public List<Student> readStudent(String requestQuantity, String requestGroupNumber, String requestGpa, String requestRecordBookNumber) {
        List<Student> students = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print(requestQuantity);
        int quantity = scanner.nextInt();
        String groupNumber;
        double gpa;
        String recordBookNumber;

        for (int i = 1; i <= quantity; i++) {
            groupNumber = readLineFromConsole(requestGroupNumber, i, scanner);
            gpa = readDoubleFromConsole(requestGpa, i, scanner);
            recordBookNumber = readLineFromConsole(requestRecordBookNumber, i, scanner);

            students.add(
                    Student.builder()
                            .setGroupNumber(groupNumber)
                            .setAverageGrade(gpa)
                            .setRecordBookNumber(recordBookNumber)
                            .build()
            );
        }

        return students;
    }

    public List<User> readUser(String requestQuantity, String requestName, String requestPassword, String requestEmail) {
        List<User> users = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print(requestQuantity);
        int quantity = scanner.nextInt();
        String name;
        String password;
        String email;

        for (int i = 1; i <= quantity; i++) {
            name = readLineFromConsole(requestName, i, scanner);
            password = readLineFromConsole(requestPassword, i, scanner);
            email = readLineFromConsole(requestEmail, i, scanner);

            users.add(
                    User.builder()
                            .setName(name)
                            .setPassword(password)
                            .setEmail(email)
                            .build()
            );
        }

        return users;
    }
}
