package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Barrel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BarrelStream {
    private final ObjectMapper mapper = new ObjectMapper();

    public void loadFromJsonWithStatistics(String filePath) {
        System.out.println("Статистика бочек из JSON");
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            var barrels = StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseBarrel)
                    .collect(Collectors.toList());

            System.out.println("Всего бочек: " + barrels.stream().count());
            System.out.println("Общий объём: " + barrels.stream().mapToDouble(Barrel::getVolume).sum() + " л");
            System.out.println("Средний объём: " + String.format("%.2f", barrels.stream().mapToDouble(Barrel::getVolume).average().orElse(0.0)) + " л");
            barrels.stream().max(Comparator.comparing(Barrel::getVolume)).ifPresent(max -> System.out.println("Максимальная бочка: " + max));
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Barrel parseBarrel(JsonNode node) {
        return Barrel.builder()
                .setVolume(node.get("volume ").asDouble())
                .setStoredMaterial(node.get("storedMaterial ").asText().trim())
                .setMaterial(node.get("material ").asText().trim())
                .build();
    }
}