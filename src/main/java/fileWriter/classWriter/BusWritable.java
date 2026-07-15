package fileWriter.classWriter;

import daryaClassStream.ModelBuilderClass.Bus;
import dataSource.fileReader.HeadersConst;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BusWritable implements Writable<Bus> {
    private Bus bus;

    public BusWritable() {}

    public void setItem(Bus bus) {
        this.bus = bus;
    }

    @Override
    public String getCsvHeaders(String separator) {
        return HeadersConst.BUS_NUMBER + separator + HeadersConst.BUS_MODEL + separator + HeadersConst.BUS_MILEAGE + "\n";
    }

    @Override
    public String getCsvLine(String separator) {
        return bus.getNumber() + separator + bus.getModel() + separator + bus.getMileage() + "\n";
    }

    @Override
    public Element getXmlRoot(Document dom) {
        return dom.createElement("busses");
    }

    @Override
    public Element getXmlElement(Document dom) {
        Element elem = dom.createElement("bus");
        elem.setAttribute(HeadersConst.BUS_NUMBER, bus.getNumber());
        elem.setAttribute(HeadersConst.BUS_MODEL, bus.getModel());
        elem.setAttribute(HeadersConst.BUS_MILEAGE, String.valueOf(bus.getMileage()));
        return elem;
    }
}
