package dataSource;

import daryaClassStream.ModelBuilderClass.*;
import dataSource.fileReader.FileReader;
import dataSource.randomData.Generator;

import java.util.List;

public class DataSourceTest {
    public static void main(String[] args) {
        int errors = 0;
        errors += testGenerator();
        errors += testCsv();
        errors += testJson();
        errors += testXml();

        if (errors > 0) {
            System.out.printf("Test ended with %d errors!", errors);
        } else {
            System.out.print("Test ended successfully!");
        }
    }

    private static int testGenerator() {
        try {
            System.out.println("Generator:");
            Generator generator = new Generator();
            List<Barrel> a = generator.readBarrels(2);
            List<Bus> b = generator.readBus(3);
            List<Car> c = generator.readCars(4);
            List<Student> s = generator.readStudents(5);
            List<User> u = generator.readUsers(6);

            writeArray("Barrel", a);
            writeArray("Bus", b);
            writeArray("Car", c);
            writeArray("Student", s);
            writeArray("User", u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    private static int testCsv() {
        try {
            System.out.println("Read from CSV:");
            List<Barrel> a = FileReader.readBarrel("src\\main\\java\\dataSource\\fileReader\\examples\\barrel.csv");
            List<Bus> b = FileReader.readBus("src\\main\\java\\dataSource\\fileReader\\examples\\bus.csv");
            List<Car> c = FileReader.readCar("src\\main\\java\\dataSource\\fileReader\\examples\\car.csv");
            List<Student> s = FileReader.readStudent("src\\main\\java\\dataSource\\fileReader\\examples\\student.csv");
//            List<User> u = FileReader.readUser("src\\main\\java\\dataSource\\fileReader\\examples\\user.csv");

            writeArray("Barrel", a);
            writeArray("Bus", b);
            writeArray("Car", c);
            writeArray("Student", s);
//            writeArray("User", u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    private static int testJson() {

        try {
            System.out.println("Read from JSON:");
            List<Barrel> a = FileReader.readBarrel("src\\main\\java\\dataSource\\fileReader\\examples\\barrel.json");
            List<Bus> b = FileReader.readBus("src\\main\\java\\dataSource\\fileReader\\examples\\bus.json");
            List<Car> c = FileReader.readCar("src\\main\\java\\dataSource\\fileReader\\examples\\car.json");
            List<Student> s = FileReader.readStudent("src\\main\\java\\dataSource\\fileReader\\examples\\student.json");
//            List<User> u = FileReader.readUser("src\\main\\java\\dataSource\\fileReader\\examples\\user.json");
            List<User> u = FileReader.readUser("src\\main\\java\\dataSource\\fileReader\\examples\\user_valid.json");

            writeArray("Barrel", a);
            writeArray("Bus", b);
            writeArray("Car", c);
            writeArray("Student", s);
            writeArray("User", u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    private static int testXml() {
        try {
            System.out.println("Read from XML:");
            List<Barrel> a = FileReader.readBarrel("src\\main\\java\\dataSource\\fileReader\\examples\\barrel.xml");
            List<Bus> b = FileReader.readBus("src\\main\\java\\dataSource\\fileReader\\examples\\bus.xml");
            List<Car> c = FileReader.readCar("src\\main\\java\\dataSource\\fileReader\\examples\\car.xml");
            List<Student> s = FileReader.readStudent("src\\main\\java\\dataSource\\fileReader\\examples\\student.xml");
            List<User> u = FileReader.readUser("src\\main\\java\\dataSource\\fileReader\\examples\\user.xml");

            writeArray("Barrel", a);
            writeArray("Bus", b);
            writeArray("Car", c);
            writeArray("Student", s);
            writeArray("User", u);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    private static <T> void writeArray(String className, List<T> array) {
        System.out.println(className + " " + array.size());
        array.forEach(System.out::println);
        System.out.println();
    }
}
