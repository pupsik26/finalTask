package fileWriter;


import daryaClassStream.ModelBuilderClass.Barrel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileWriterTest {
    public static void main(String[] args) {
        testBarrels();
    }

    private static void testBarrels() {
        List<Barrel> b = new ArrayList<>();
        b.add(Barrel.builder()
                .setVolume(1)
                .setStoredMaterial("A")
                .setMaterial("B")
                .build());
        b.add(Barrel.builder()
                .setVolume(2)
                .setStoredMaterial("C")
                .setMaterial("D")
                .build());

        try {
            FileWriter writer = new FileWriter();
            writer.writeCsv("src\\main\\java\\fileWriter\\test\\barrel.csv", b);
            writer.writeJson("src\\main\\java\\fileWriter\\test\\barrel.json", b);
            writer.writeXml("src\\main\\java\\fileWriter\\test\\barrel.xml", b);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
