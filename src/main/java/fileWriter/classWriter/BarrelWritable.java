package fileWriter.classWriter;

import daryaClassStream.ModelBuilderClass.Barrel;
import dataSource.fileReader.HeadersConst;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BarrelWritable implements Writable<Barrel> {
    private Barrel barrel;

    public BarrelWritable() {}

    public void setItem(Barrel barrel) {
        this.barrel = barrel;
    }

    @Override
    public String getCsvHeaders(String separator) {
        return HeadersConst.BARREL_VOLUME + separator + HeadersConst.BARREL_STORED + separator + HeadersConst.BARREL_MATERIAL + "\n";
    }

    @Override
    public String getCsvLine(String separator) {
        return barrel.getVolume() + separator + barrel.getStoredMaterial() + separator + barrel.getMaterial() + "\n";
    }

    @Override
    public Element getXmlRoot(Document dom) {
        return dom.createElement("barrels");
    }

    @Override
    public Element getXmlElement(Document dom) {
        Element elem = dom.createElement("barrel");
        elem.setAttribute(HeadersConst.BARREL_VOLUME, String.valueOf(barrel.getVolume()));
        elem.setAttribute(HeadersConst.BARREL_STORED, barrel.getStoredMaterial());
        elem.setAttribute(HeadersConst.BARREL_MATERIAL, barrel.getMaterial());
        return elem;
    }
}
