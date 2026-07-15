package dataSource.fileReader;

import daryaClassStream.ModelBuilderClass.*;
import ivans.task.validators.file.input.FileLineValidator;
import ivans.task.validators.objects.ObjectValidatorFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CsvReader {

    protected static List<Barrel> readBarrel(File file) throws IOException {
        List<Barrel> items = new LinkedList<>();

        // ИСПРАВЛЕНО: Используем современный и безопасный способ чтения файла
        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            String line = br.readLine();
            if (line == null) return items;

            String[] fields = FileLineValidator.splitFields(line, 3);
            final int volumeIndex = Arrays.asList(fields).indexOf(HeadersConst.BARREL_VOLUME),
                    storedIndex = Arrays.asList(fields).indexOf(HeadersConst.BARREL_STORED),
                    materialIndex = Arrays.asList(fields).indexOf(HeadersConst.BARREL_MATERIAL);

            if (volumeIndex == -1 || storedIndex == -1 || materialIndex == -1) {
                throw new IOException("Ошибка чтения заголовков в файле " + file.getAbsolutePath());
            }

            line = br.readLine();
            while (line != null) {
                fields = FileLineValidator.splitFields(line, 3);
                Barrel item = Barrel.builder()
                        .setVolume(FileLineValidator.parseDoubleField(fields, volumeIndex, HeadersConst.BARREL_VOLUME))
                        .setStoredMaterial(FileLineValidator.parseStringField(fields, storedIndex, HeadersConst.BARREL_STORED))
                        .setMaterial(FileLineValidator.parseStringField(fields, materialIndex, HeadersConst.BARREL_MATERIAL))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
                line = br.readLine();
            }
        }

        return items;
    }

    protected static List<Bus> readBus(File file) throws IOException {
        List<Bus> items = new LinkedList<>();

        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            String line = br.readLine();
            if (line == null) return items;

            String[] fields = FileLineValidator.splitFields(line, 3);
            final int mileageIndex = Arrays.asList(fields).indexOf(HeadersConst.BUS_MILEAGE),
                    modelIndex = Arrays.asList(fields).indexOf(HeadersConst.BUS_MODEL),
                    numberIndex = Arrays.asList(fields).indexOf(HeadersConst.BUS_NUMBER);

            if (mileageIndex == -1 || modelIndex == -1 || numberIndex == -1) {
                throw new IOException("Ошибка чтения заголовков в файле " + file.getAbsolutePath());
            }

            line = br.readLine();
            while (line != null) {
                fields = FileLineValidator.splitFields(line, 3);
                Bus item = Bus.builder()
                        .setMileage(FileLineValidator.parseIntField(fields, mileageIndex, HeadersConst.BUS_MILEAGE))
                        .setModel(FileLineValidator.parseStringField(fields, modelIndex, HeadersConst.BUS_MODEL))
                        .setNumber(FileLineValidator.parseStringField(fields, numberIndex, HeadersConst.BUS_NUMBER))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
                line = br.readLine();
            }
        }

        return items;
    }

    protected static List<Car> readCar(File file) throws IOException {
        List<Car> items = new LinkedList<>();

        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            String line = br.readLine();
            if (line == null) return items;

            String[] fields = FileLineValidator.splitFields(line, 3);

            final int powerIndex = Arrays.asList(fields).indexOf(HeadersConst.CAR_POWER),
                    modelIndex = Arrays.asList(fields).indexOf(HeadersConst.CAR_MODEL),
                    yearIndex = Arrays.asList(fields).indexOf(HeadersConst.CAR_YEAR);

            if (powerIndex == -1 || modelIndex == -1 || yearIndex == -1) {
                throw new IOException("Ошибка чтения заголовков в файле " + file.getAbsolutePath());
            }

            line = br.readLine();
            while (line != null) {
                fields = FileLineValidator.splitFields(line, 3);
                Car item = Car.builder()
                        .setPower(FileLineValidator.parseIntField(fields, powerIndex, HeadersConst.CAR_POWER))
                        .setModel(FileLineValidator.parseStringField(fields, modelIndex, HeadersConst.CAR_MODEL))
                        .setYear(FileLineValidator.parseIntField(fields, yearIndex, HeadersConst.CAR_YEAR))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
                line = br.readLine();
            }
        }

        return items;
    }

    protected static List<Student> readStudent(File file) throws IOException {
        List<Student> items = new LinkedList<>();

        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            String line = br.readLine();
            if (line == null) return items;

            String[] fields = FileLineValidator.splitFields(line, 3);
            final int groupNumberIndex = Arrays.asList(fields).indexOf(HeadersConst.STUDENT_GROUP),
                    gpaIndex = Arrays.asList(fields).indexOf(HeadersConst.STUDENT_GPA),
                    recordBookNumberIndex = Arrays.asList(fields).indexOf(HeadersConst.STUDENT_NUMBER);

            if (groupNumberIndex == -1 || gpaIndex == -1 || recordBookNumberIndex == -1) {
                throw new IOException("Ошибка чтения заголовков в файле " + file.getAbsolutePath());
            }

            line = br.readLine();
            while (line != null) {
                fields = FileLineValidator.splitFields(line, 3);
                Student item = Student.builder()
                        .setGroupNumber(FileLineValidator.parseStringField(fields, groupNumberIndex, HeadersConst.STUDENT_GROUP))
                        .setAverageGrade(FileLineValidator.parseDoubleField(fields, gpaIndex, HeadersConst.STUDENT_GPA))
                        .setRecordBookNumber(FileLineValidator.parseStringField(fields, recordBookNumberIndex, HeadersConst.STUDENT_NUMBER))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
                line = br.readLine();
            }
        }

        return items;
    }

    protected static List<User> readUser(File file) throws IOException {
        List<User> items = new LinkedList<>();

        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            String line = br.readLine();
            if (line == null) return items;

            String[] fields = FileLineValidator.splitFields(line, 3);
            final int nameIndex = Arrays.asList(fields).indexOf(HeadersConst.USER_NAME),
                    passwordIndex = Arrays.asList(fields).indexOf(HeadersConst.USER_PASSWORD),
                    emailIndex = Arrays.asList(fields).indexOf(HeadersConst.USER_EMAIL);

            if (nameIndex == -1 || passwordIndex == -1 || emailIndex == -1) {
                throw new IOException("Ошибка чтения заголовков в файле " + file.getAbsolutePath());
            }

            line = br.readLine();
            while (line != null) {
                fields = FileLineValidator.splitFields(line, 3);
                User item = User.builder()
                        .setName(FileLineValidator.parseStringField(fields, nameIndex, HeadersConst.USER_NAME))
                        .setPassword(FileLineValidator.parseStringField(fields, passwordIndex, HeadersConst.USER_PASSWORD))
                        .setEmail(FileLineValidator.parseStringField(fields, emailIndex, HeadersConst.USER_EMAIL))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
                line = br.readLine();
            }
        }

        return items;
    }
}