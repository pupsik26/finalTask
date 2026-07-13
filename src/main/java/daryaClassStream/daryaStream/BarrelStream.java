package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Barrel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BarrelStream {

    private final ObjectMapper mapper;

    public BarrelStream() {
        this.mapper = new ObjectMapper();
    }

    public void loadFromJsonWithStatistics(String filePath) {
        System.out.println("=== Статистика бочек из JSON ===");

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            var barrels = StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseBarrel)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            long totalCount = barrels.stream().count();
            double totalVolume = barrels.stream()
                    .mapToDouble(Barrel::getVolume)
                    .sum();
            double avgVolume = barrels.stream()
                    .mapToDouble(Barrel::getVolume)
                    .average()
                    .orElse(0.0);
            Barrel maxBarrel = barrels.stream()
                    .max(Comparator.comparing(Barrel::getVolume))
                    .orElse(null);

            System.out.println("Всего бочек: " + totalCount);
            System.out.println("Общий объём: " + totalVolume + " л");
            System.out.println("Средний объём: " + String.format("%.2f", avgVolume) + " л");
            System.out.println("Максимальная бочка: " + maxBarrel);

        } catch (IOException e) {
            System.out.println("✗ Ошибка: " + e.getMessage());
        }
    }

    private Barrel parseBarrel(JsonNode node) {
        try {
            double volume = node.get("volume").asDouble();
            String storedMaterial = node.get("storedMaterial").asText().trim();
            String material = node.get("material").asText().trim();

            return Barrel.builder()
                    .setVolume(volume)
                    .setStoredMaterial(storedMaterial)
                    .setMaterial(material)
                    .build();
        } catch (Exception e) {
            System.out.println("✗ Ошибка парсинга бочки: " + e.getMessage());
            return null;
        }
    }
}
