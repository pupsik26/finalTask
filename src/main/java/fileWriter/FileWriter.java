package fileWriter;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ModelBuilderClass.*;
import fileWriter.classWriter.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileWriter {
    private static final Map<Class<?>, Writable<?>> classWriters = new HashMap<>();

    static {
        classWriters.put(Barrel.class, new BarrelWritable());
        classWriters.put(Bus.class, new BusWritable());
        classWriters.put(Car.class, new CarWritable());
        classWriters.put(Student.class, new StudentWritable());
        classWriters.put(User.class, new UserWritable());
    }

    private static final String SEPARATOR = ";";

    public <T> void writeCsv(String filepath, List<T> array) throws IOException {
        Writable<T> classWriter = (Writable<T>) classWriters.get(array.getFirst().getClass());
        if (classWriter == null) {
            throw new IOException("Тип данных не поддерживается: " + array.getFirst().getClass().getSimpleName());
        }

        createPath(filepath);

        try (java.io.FileWriter fw = new java.io.FileWriter(filepath)) {
            classWriter.setItem(array.getFirst());
            fw.write(classWriter.getCsvHeaders(SEPARATOR));

            for (T t : array) {
                classWriter.setItem(t);
                fw.write(classWriter.getCsvLine(SEPARATOR));
            }
        }
    }

    public <T> void writeJson(String filepath, List<T> array) throws IOException {
        createPath(filepath);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(
                new DefaultPrettyPrinter().withArrayIndenter(
                        DefaultIndenter.SYSTEM_LINEFEED_INSTANCE
                )
        );
        writer.writeValue(new File(filepath), array);
    }

    public <T> void writeXml(String filepath, List<T> array) throws IOException {
        Writable<T> classWriter = (Writable<T>) classWriters.get(array.getFirst().getClass());
        if (classWriter == null) {
            throw new IOException("Тип данных не поддерживается: " + array.getFirst().getClass().getSimpleName());
        }

        createPath(filepath);

        try {
            Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            classWriter.setItem(array.getFirst());
            Element root = classWriter.getXmlRoot(dom);
            dom.appendChild(root);

            for (T t : array) {
                classWriter.setItem(t);
                root.appendChild(classWriter.getXmlElement(dom));
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(dom), new StreamResult(new File(filepath)));
        } catch (ParserConfigurationException e) {
            throw new IOException("Ошибка сборки XML структуры.");
        } catch (TransformerException e) {
            throw new IOException("Ошибка записи XML файла.");
        }
    }

    private void createPath(String filepath) {
        File file = new File(filepath);
        File dir = new File(file.getParent());
        dir.mkdirs();
    }
}
