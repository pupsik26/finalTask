package fileWriter.classWriter;

import ModelBuilderClass.Car;
import dataSource.fileReader.HeadersConst;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CarWritable implements Writable<Car> {
    private Car car;

    public CarWritable() {}

    public void setItem(Car car) {
        this.car = car;
    }

    @Override
    public String getCsvHeaders(String separator) {
        return HeadersConst.CAR_POWER + separator + HeadersConst.CAR_MODEL + separator + HeadersConst.CAR_YEAR + "\n";
    }

    @Override
    public String getCsvLine(String separator) {
        return car.getPower() + separator + car.getModel() + separator + car.getYear() + "\n";
    }

    @Override
    public Element getXmlRoot(Document dom) {
        return dom.createElement("cars");
    }

    @Override
    public Element getXmlElement(Document dom) {
        Element elem = dom.createElement("car");
        elem.setAttribute(HeadersConst.CAR_POWER, String.valueOf(car.getPower()));
        elem.setAttribute(HeadersConst.CAR_MODEL, car.getModel());
        elem.setAttribute(HeadersConst.CAR_YEAR, String.valueOf(car.getYear()));
        return elem;
    }
}
