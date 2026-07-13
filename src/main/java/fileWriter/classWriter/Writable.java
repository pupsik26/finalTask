package fileWriter.classWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Writable<T> {
    void setItem(T object);
    String getCsvHeaders(String separator);
    String getCsvLine(String separator);

    Element getXmlRoot(Document dom);
    Element getXmlElement(Document dom);
}
