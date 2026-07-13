package dataSource.fileReader;

import daryaClassStream.ModelBuilderClass.*;
import ivans.task.validators.file.input.FileLineValidator;
import ivans.task.validators.objects.ObjectValidatorFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class XmlReader {
    private final static String EXCEPTION_XML = "Ошибка чтения XML файла. Проверьте корректность XML файла.";

    protected static List<Barrel> readBarrel(File file) throws IOException {
        List<Barrel> items = new LinkedList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("barrel");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element node = (Element) nodes.item(i);

                if (!node.hasAttribute(HeadersConst.BARREL_VOLUME) ||
                        !node.hasAttribute(HeadersConst.BARREL_STORED) ||
                        !node.hasAttribute(HeadersConst.BARREL_MATERIAL)) {
                    continue;
                }

                String[] fields = new String[] {
                        node.getAttribute(HeadersConst.BARREL_VOLUME),
                        node.getAttribute(HeadersConst.BARREL_STORED),
                        node.getAttribute(HeadersConst.BARREL_MATERIAL)
                };

                Barrel item = Barrel.builder()
                        .setVolume(FileLineValidator.parseDoubleField(fields, 0, HeadersConst.BARREL_VOLUME))
                        .setStoredMaterial(FileLineValidator.parseStringField(fields, 1, HeadersConst.BARREL_STORED))
                        .setMaterial(FileLineValidator.parseStringField(fields, 2, HeadersConst.BARREL_MATERIAL))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(EXCEPTION_XML);
        }

        return items;
    }

    protected static List<Bus> readBus(File file) throws IOException {
        List<Bus> items = new LinkedList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("bus");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element node = (Element) nodes.item(i);

                if (!node.hasAttribute(HeadersConst.BUS_MILEAGE) ||
                        !node.hasAttribute(HeadersConst.BUS_MODEL) ||
                        !node.hasAttribute(HeadersConst.BUS_NUMBER)) {
                    continue;
                }

                String[] fields = new String[] {
                        node.getAttribute(HeadersConst.BUS_MILEAGE),
                        node.getAttribute(HeadersConst.BUS_MODEL),
                        node.getAttribute(HeadersConst.BUS_NUMBER)
                };
                Bus item = Bus.builder()
                        .setMileage(FileLineValidator.parseIntField(fields, 0, HeadersConst.BUS_MILEAGE))
                        .setModel(FileLineValidator.parseStringField(fields, 1, HeadersConst.BUS_MODEL))
                        .setNumber(FileLineValidator.parseStringField(fields, 2, HeadersConst.BUS_NUMBER))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(EXCEPTION_XML);
        }

        return items;
    }

    protected static List<Car> readCar(File file) throws IOException {
        List<Car> items = new LinkedList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("car");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element node = (Element) nodes.item(i);

                if (!node.hasAttribute(HeadersConst.CAR_POWER) ||
                        !node.hasAttribute(HeadersConst.CAR_MODEL) ||
                        !node.hasAttribute(HeadersConst.CAR_YEAR)) {
                    continue;
                }

                String[] fields = new String[] {
                        node.getAttribute(HeadersConst.CAR_POWER),
                        node.getAttribute(HeadersConst.CAR_MODEL),
                        node.getAttribute(HeadersConst.CAR_YEAR)
                };

                Car item = Car.builder()
                        .setPower(FileLineValidator.parseIntField(fields, 0, HeadersConst.CAR_POWER))
                        .setModel(FileLineValidator.parseStringField(fields, 1, HeadersConst.CAR_MODEL))
                        .setYear(FileLineValidator.parseIntField(fields, 2, HeadersConst.CAR_YEAR))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(EXCEPTION_XML);
        }

        return items;
    }

    protected static List<Student> readStudent(File file) throws IOException {
        List<Student> items = new LinkedList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("student");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element node = (Element) nodes.item(i);

                if (!node.hasAttribute(HeadersConst.STUDENT_GROUP) ||
                        !node.hasAttribute(HeadersConst.STUDENT_GPA) ||
                        !node.hasAttribute(HeadersConst.STUDENT_NUMBER)) {
                    continue;
                }

                String[] fields = new String[] {
                        node.getAttribute(HeadersConst.STUDENT_GROUP),
                        node.getAttribute(HeadersConst.STUDENT_GPA),
                        node.getAttribute(HeadersConst.STUDENT_NUMBER)
                };

                Student item = Student.builder()
                        .setGroupNumber(FileLineValidator.parseStringField(fields, 0, HeadersConst.STUDENT_GROUP))
                        .setAverageGrade(FileLineValidator.parseDoubleField(fields, 1, HeadersConst.STUDENT_GPA))
                        .setRecordBookNumber(FileLineValidator.parseStringField(fields, 2, HeadersConst.STUDENT_NUMBER))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);
                items.add(item);
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(EXCEPTION_XML);
        }

        return items;
    }

    protected static List<User> readUser(File file) throws IOException {
        List<User> items = new LinkedList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("user");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element node = (Element) nodes.item(i);

                if (!node.hasAttribute(HeadersConst.USER_NAME) ||
                        !node.hasAttribute(HeadersConst.USER_PASSWORD) ||
                        !node.hasAttribute(HeadersConst.USER_EMAIL)) {
                    continue;
                }

                String[] fields = new String[] {
                        node.getAttribute(HeadersConst.USER_NAME),
                        node.getAttribute(HeadersConst.USER_PASSWORD),
                        node.getAttribute(HeadersConst.USER_EMAIL)
                };

                User item = User.builder()
                        .setName(FileLineValidator.parseStringField(fields, 0, HeadersConst.USER_NAME))
                        .setPassword(FileLineValidator.parseStringField(fields, 1, HeadersConst.USER_PASSWORD))
                        .setEmail(FileLineValidator.parseStringField(fields, 2, HeadersConst.USER_EMAIL))
                        .build();

                ObjectValidatorFactory.getValidator().validate(item);

                items.add(item);
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(EXCEPTION_XML);
        }

        return items;
    }
}