package dataSource.fileReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ModelBuilderClass.*;
import ivans.task.validators.objects.ObjectValidatorFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonReader {
    protected static List<Barrel> readBarrel(File file) throws IOException {
        List<Barrel> items = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        Iterator<JsonNode> nodes = mapper.readTree(file).elements();
        JsonNode node;
        while (nodes.hasNext()) {
            node = nodes.next();
            if (!(node.has(HeadersConst.BARREL_VOLUME) && node.get(HeadersConst.BARREL_VOLUME).isDouble()) ||
                    !(node.has(HeadersConst.BARREL_STORED) && node.get(HeadersConst.BARREL_STORED).isTextual()) ||
                    !(node.has(HeadersConst.BARREL_MATERIAL) && node.get(HeadersConst.BARREL_MATERIAL).isTextual())) {
                continue;
            }

            Barrel item = Barrel.builder()
                    .setVolume(node.get(HeadersConst.BARREL_VOLUME).asDouble())
                    .setStoredMaterial(node.get(HeadersConst.BARREL_STORED).asText())
                    .setMaterial(node.get(HeadersConst.BARREL_MATERIAL).asText())
                    .build();

            ObjectValidatorFactory.getValidator().validate(item);

            items.add(item);
        }

        return items;
    }

    protected static List<Bus> readBus(File file) throws IOException {
        List<Bus> items = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        Iterator<JsonNode> nodes = mapper.readTree(file).elements();
        JsonNode node;
        while (nodes.hasNext()) {
            node = nodes.next();
            if (!(node.has(HeadersConst.BUS_MILEAGE) && node.get(HeadersConst.BUS_MILEAGE).isInt()) ||
                    !(node.has(HeadersConst.BUS_MODEL) && node.get(HeadersConst.BUS_MODEL).isTextual()) ||
                    !(node.has(HeadersConst.BUS_NUMBER) && node.get(HeadersConst.BUS_NUMBER).isInt())) {
                continue;
            }

            Bus item = Bus.builder()
                    .setMileage(node.get(HeadersConst.BUS_MILEAGE).asInt())
                    .setModel(node.get(HeadersConst.BUS_MODEL).asText())
                    .setNumber(node.get(HeadersConst.BUS_NUMBER).asText())
                    .build();

            ObjectValidatorFactory.getValidator().validate(item);

            items.add(item);
        }

        return items;
    }

    protected static List<Car> readCar(File file) throws IOException {
        List<Car> items = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        Iterator<JsonNode> nodes = mapper.readTree(file).elements();
        JsonNode node;
        while (nodes.hasNext()) {
            node = nodes.next();
            if (!(node.has(HeadersConst.CAR_POWER) && node.get(HeadersConst.CAR_POWER).isInt()) ||
                    !(node.has(HeadersConst.CAR_MODEL) && node.get(HeadersConst.CAR_MODEL).isTextual()) ||
                    !(node.has(HeadersConst.CAR_YEAR) && node.get(HeadersConst.CAR_YEAR).isInt())) {
                continue;
            }

            Car item = Car.builder()
                    .setPower(node.get(HeadersConst.CAR_POWER).asInt())
                    .setModel(node.get(HeadersConst.CAR_MODEL).asText())
                    .setYear(node.get(HeadersConst.CAR_YEAR).asInt())
                    .build();

            ObjectValidatorFactory.getValidator().validate(item);

            items.add(item);
        }

        return items;
    }

    protected static List<Student> readStudent(File file) throws IOException {
        List<Student> items = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        Iterator<JsonNode> nodes = mapper.readTree(file).elements();
        JsonNode node;
        while (nodes.hasNext()) {
            node = nodes.next();
            if (!(node.has(HeadersConst.STUDENT_GROUP) && node.get(HeadersConst.STUDENT_GROUP).isTextual()) ||
                    !(node.has(HeadersConst.STUDENT_GPA) && node.get(HeadersConst.STUDENT_GPA).isDouble()) ||
                    !(node.has(HeadersConst.STUDENT_NUMBER) && node.get(HeadersConst.STUDENT_NUMBER).isTextual())) {
                continue;
            }

            Student item = Student.builder()
                    .setGroupNumber(node.get(HeadersConst.STUDENT_GROUP).asText())
                    .setAverageGrade(node.get(HeadersConst.STUDENT_GPA).asDouble())
                    .setRecordBookNumber(node.get(HeadersConst.STUDENT_NUMBER).asText())
                    .build();

            ObjectValidatorFactory.getValidator().validate(item);

            items.add(item);
        }

        return items;
    }

    protected static List<User> readUser(File file) throws IOException {
        List<User> items = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();

        Iterator<JsonNode> nodes = mapper.readTree(file).elements();
        JsonNode node;
        while (nodes.hasNext()) {
            node = nodes.next();
            if (!(node.has(HeadersConst.USER_NAME) && node.get(HeadersConst.USER_NAME).isTextual()) ||
                    !(node.has(HeadersConst.USER_PASSWORD) && node.get(HeadersConst.USER_PASSWORD).isTextual()) ||
                    !(node.has(HeadersConst.USER_EMAIL) && node.get(HeadersConst.USER_EMAIL).isTextual())) {
                continue;
            }

            User item = User.builder()
                    .setName(node.get(HeadersConst.USER_NAME).asText())
                    .setPassword(node.get(HeadersConst.USER_PASSWORD).asText())
                    .setEmail(node.get(HeadersConst.USER_EMAIL).asText())
                    .build();

            ObjectValidatorFactory.getValidator().validate(item);

            items.add(item);
        }

        return items;
    }
}
