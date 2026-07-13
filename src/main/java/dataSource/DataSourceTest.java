package dataSource;

import ModelBuilderClass.*;
import dataSource.fileReader.FileReader;
import dataSource.randomData.Generator;

import java.util.List;

public class DataSourceTest {
    public static void main(String[] args) {
        int errors = 0;
        Generator generator = new Generator();
        List<Barrel> a = generator.readBarrels(2);
        List<Bus> b = generator.readBus(3);
        List<Car> c = generator.readCars(4);
        List<Student> s = generator.readStudents(5);
        List<User> u = generator.readUsers(6);

        if (a.size() != 2) {
            errors++;
            System.out.println("Barrel error");
        }
        if (a.size() != 3) {
            errors++;
            System.out.println("Bus error");
        }
        if (a.size() != 4) {
            errors++;
            System.out.println("Car error");
        }
        if (a.size() != 5) {
            errors++;
            System.out.println("Student error");
        }
        if (a.size() != 6) {
            errors++;
            System.out.println("User error");
        }

        try {
            a = FileReader.readBarrel("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\barrel.csv");
            b = FileReader.readBus("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\bus.csv");
            c = FileReader.readCar("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\car.csv");
            s = FileReader.readStudent("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\student.csv");
            u = FileReader.readUser("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\user.csv");

            System.out.println(a.size());
            System.out.println(b.size());
            System.out.println(c.size());
            System.out.println(s.size());
            System.out.println(u.size());
        } catch (Exception e) {
            errors++;
            System.out.println(e.getMessage());
        }

        try {
            a = FileReader.readBarrel("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\barrel.json");
            b = FileReader.readBus("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\bus.json");
            c = FileReader.readCar("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\car.json");
            s = FileReader.readStudent("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\student.json");
            u = FileReader.readUser("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\user.json");

            System.out.println(a.size());
            System.out.println(b.size());
            System.out.println(c.size());
            System.out.println(s.size());
            System.out.println(u.size());
        } catch (Exception e) {
            errors++;
            System.out.println(e.getMessage());
        }

        try {
            a = FileReader.readBarrel("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\barrel.xml");
            b = FileReader.readBus("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\bus.xml");
            c = FileReader.readCar("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\car.xml");
            s = FileReader.readStudent("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\student.xml");
            u = FileReader.readUser("F:\\JavaProjects\\FinalTask\\src\\main\\java\\dataSource\\fileReader\\examples\\user.xml");

            System.out.println(a.size());
            System.out.println(b.size());
            System.out.println(c.size());
            System.out.println(s.size());
            System.out.println(u.size());
        } catch (Exception e) {
            errors++;
            System.out.println(e.getMessage());
        }

        if (errors > 0) {
            System.out.printf("Test ended with %d errors!", errors);
        } else {
            System.out.print("Test ended successfully!");
        }
    }
}
