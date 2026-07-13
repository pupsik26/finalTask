package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Bus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BusStream {

    private final ObjectMapper mapper;

    public BusStream() {
        this.mapper = new ObjectMapper();
    }

    public void loadFromJsonGroupedByModel(String filePath) {
        System.out.println("=== Загрузка автобусов с группировкой по модели ===");

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            var busesByModel = StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseBus)
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(Bus::getModel));

            busesByModel.forEach((model, list) ->
                    System.out.println("  Модель " + model + ": " + list.size() + " шт."));

        } catch (IOException e) {
            System.out.println("✗ Ошибка: " + e.getMessage());
        }
    }

    private Bus parseBus(JsonNode node) {
        try {
            int number = node.get("number").asInt();
            String model = node.get("model").asText().trim();
            int mileage = node.get("mileage").asInt();

            return Bus.builder()
                    .setNumber("BUS-" + number)
                    .setModel(model)
                    .setMileage(mileage)
                    .build();
        } catch (Exception e) {
            System.out.println("✗ Ошибка парсинга автобуса: " + e.getMessage());
            return null;
        }
    }
}
