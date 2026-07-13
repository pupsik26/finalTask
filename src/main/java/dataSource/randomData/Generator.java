package dataSource.randomData;

import ModelBuilderClass.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Generator {
    private final static double BARREL_VOLUME_MIN = 10;
    private final static double BARREL_VOLUME_MAX = 100;
    private final static String[] BARREL_MATERIAL = {
            "Дуб",
            "Ясень",
            "Бук",
            "Вишня",
            "Осина",
            "Лиственница",
            "Кедр",
            "Нержавеющая сталь",
            "Чугун",
            "Стеклопластик"
    };
    private final static String[] BARREL_STORED_MATERIAL = {
            "Вода",
            "Вино",
            "Мёд",
            "Соленья",
            "Масло",
            "Зерно",
            "Соль",
            "Сироп",
            "Виски",
            "Уксус"
    };

    private final static int BUS_NUMBER_MIN = 1;
    private final static int BUS_NUMBER_MAX = 50;
    private final static int BUS_MILEAGE_MIN = 1000;
    private final static int BUS_MILEAGE_MAX = 100000;
    private final static String[] BUS_MODEL = {
            "ЛиАЗ‑5292",
            "ПАЗ‑3205",
            "НефАЗ‑5299",
            "МАЗ‑203",
            "Volgabus‑5270",
            "КАвЗ‑4238",
            "Mercedes‑Benz Citaro",
            "Scania Citywide",
            "MAN Lion’s City",
            "Yutong ZK6122H9"
    };

    private final static int CAR_POWER_MIN = 100;
    private final static int CAR_POWER_MAX = 1000;
    private final static int CAR_YEAR_MIN = 1990;
    private final static int CAR_YEAR_MAX = 2025;
    private final static String[] CAR_MODEL = {
            "Toyota Camry",
            "Hyundai Solaris",
            "Kia Rio",
            "Volkswagen Polo",
            "Skoda Octavia",
            "Lada Vesta",
            "Renault Logan",
            "Ford Focus",
            "Nissan Qashqai",
            "Mazda CX‑5"
    };

    private final static String[] STUDENT_GROUP = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"
    };
    private final static double STUDENT_GPA_MIN = 2;
    private final static double STUDENT_GPA_MAX = 5;
    private final static int STUDENT_NUMBER_MIN = 1;
    private final static int STUDENT_NUMBER_MAX = 1000000;

    private final static String[] USER_NAME = {
            "Alexander",
            "Mary",
            "Dmitry",
            "Anna",
            "Sergei",
            "Elena",
            "Andrew",
            "Olga",
            "Igor",
            "Tatiana"
    };

    private final Random random;

    public Generator() {
        random = new Random();
    }

    public Generator(long seed) {
        random = new Random(seed);
    }

    private int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private double getRandomDouble(double min, double max) {
        double value = random.nextDouble() * (max - min) + min;
        return Math.round(value * 1000.0) / 1000.0;
    }

    private String getRandomString(String[] lines) {
        return lines[random.nextInt(lines.length)];
    }

    public List<Barrel> readBarrels(int quantity) {
        List<Barrel> barrels = new LinkedList<>();

        for (int i = 0; i < quantity; i++) {
            barrels.add(
                    Barrel.builder()
                            .setVolume(getRandomDouble(BARREL_VOLUME_MIN, BARREL_VOLUME_MAX))
                            .setStoredMaterial(getRandomString(BARREL_STORED_MATERIAL))
                            .setMaterial(getRandomString(BARREL_MATERIAL))
                            .build()
            );
        }

        return barrels;
    }

    public List<Bus> readBus(int quantity) {
        List<Bus> busses = new LinkedList<>();
        Random rnd = new Random();

        for (int i = 0; i < quantity; i++) {
            busses.add(
                    Bus.builder()
                            .setNumber(String.valueOf(getRandomInt(BUS_NUMBER_MIN, BUS_NUMBER_MAX)))
                            .setModel(getRandomString(BUS_MODEL))
                            .setMileage(getRandomInt(BUS_MILEAGE_MIN, BUS_MILEAGE_MAX))
                            .build()
            );
        }

        return busses;
    }

    public List<Car> readCars(int quantity) {
        List<Car> cars = new LinkedList<>();
        Random rnd = new Random();

        for (int i = 0; i < quantity; i++) {
            cars.add(
                    Car.builder()
                            .setPower(getRandomInt(CAR_POWER_MIN, CAR_POWER_MAX))
                            .setModel(getRandomString(CAR_MODEL))
                            .setYear(getRandomInt(CAR_YEAR_MIN, CAR_YEAR_MAX))
                            .build()
            );
        }

        return cars;
    }

    public List<Student> readStudents(int quantity) {
        List<Student> students = new LinkedList<>();
        Random rnd = new Random();

        for (int i = 0; i < quantity; i++) {
            students.add(
                    Student.builder()
                            .setGroupNumber(getRandomString(STUDENT_GROUP))
                            .setAverageGrade(getRandomDouble(STUDENT_GPA_MIN, STUDENT_GPA_MAX))
                            .setRecordBookNumber(String.valueOf(getRandomInt(STUDENT_NUMBER_MIN, STUDENT_NUMBER_MAX)))
                            .build()
            );
        }

        return students;
    }

    public List<User> readUsers(int quantity) {
        List<User> users = new LinkedList<>();
        Random rnd = new Random();

        for (int i = 0; i < quantity; i++) {
            users.add(
                    User.builder()
                            .setName(getRandomString(USER_NAME))
                            .setPassword("Qwerty")
                            .setEmail(getRandomString(USER_NAME) + "@example.com")
                            .build()
            );
        }

        return users;
    }
}
