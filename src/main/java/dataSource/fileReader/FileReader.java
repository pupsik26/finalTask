package dataSource.fileReader;

import daryaClassStream.ModelBuilderClass.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public final class FileReader {
    private final static String EXT_CSV = ".csv";
    private final static String EXT_JSON = ".json";
    private final static String EXT_XML = ".xml";

    private final static String EXCEPTION_FILE_NOT_FOUND = "Файл по пути '%s' не найден.";
    private final static String EXCEPTION_EXTENSION_NOT_SUPPORTED = "Расширение %s не поддерживается.";

    public static List<Barrel> readBarrel(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format(EXCEPTION_FILE_NOT_FOUND, file.getAbsolutePath()));
        }
        String ext = getFileExtension(file);
        if (ext.equals(EXT_CSV)) {
            return CsvReader.readBarrel(file);
        } else if (ext.equals(EXT_JSON)) {
            return JsonReader.readBarrel(file);
        } else if (ext.equals(EXT_XML)) {
            return XmlReader.readBarrel(file);
        } else {
            throw new IOException(String.format(EXCEPTION_EXTENSION_NOT_SUPPORTED, ext));
        }
    }

    public static List<Bus> readBus(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format(EXCEPTION_FILE_NOT_FOUND, file.getAbsolutePath()));
        }
        String ext = getFileExtension(file);
        if (ext.equals(EXT_CSV)) {
            return CsvReader.readBus(file);
        } else if (ext.equals(EXT_JSON)) {
            return JsonReader.readBus(file);
        } else if (ext.equals(EXT_XML)) {
            return XmlReader.readBus(file);
        } else {
            throw new IOException(String.format(EXCEPTION_EXTENSION_NOT_SUPPORTED, ext));
        }
    }

    public static List<Car> readCar(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format(EXCEPTION_FILE_NOT_FOUND, file.getAbsolutePath()));
        }
        String ext = getFileExtension(file);
        if (ext.equals(EXT_CSV)) {
            return CsvReader.readCar(file);
        } else if (ext.equals(EXT_JSON)) {
            return JsonReader.readCar(file);
        } else if (ext.equals(EXT_XML)) {
            return XmlReader.readCar(file);
        } else {
            throw new IOException(String.format(EXCEPTION_EXTENSION_NOT_SUPPORTED, ext));
        }
    }

    public static List<Student> readStudent(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format(EXCEPTION_FILE_NOT_FOUND, file.getAbsolutePath()));
        }
        String ext = getFileExtension(file);
        if (ext.equals(EXT_CSV)) {
            return CsvReader.readStudent(file);
        } else if (ext.equals(EXT_JSON)) {
            return JsonReader.readStudent(file);
        } else if (ext.equals(EXT_XML)) {
            return XmlReader.readStudent(file);
        } else {
            throw new IOException(String.format(EXCEPTION_EXTENSION_NOT_SUPPORTED, ext));
        }
    }

    public static List<User> readUser(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format(EXCEPTION_FILE_NOT_FOUND, file.getAbsolutePath()));
        }
        String ext = getFileExtension(file);
        if (ext.equals(EXT_CSV)) {
            return CsvReader.readUser(file);
        } else if (ext.equals(EXT_JSON)) {
            return JsonReader.readUser(file);
        } else if (ext.equals(EXT_XML)) {
            return XmlReader.readUser(file);
        } else {
            throw new IOException(String.format(EXCEPTION_EXTENSION_NOT_SUPPORTED, ext));
        }
    }

    private static String getFileExtension(File file) {
        int dotIndex = file.getName().lastIndexOf('.');
        return dotIndex <= 0 ? "" : file.getName().substring(dotIndex);
    }
}
