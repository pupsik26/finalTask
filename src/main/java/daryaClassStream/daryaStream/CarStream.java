package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Car;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CarStream {
    private final ObjectMapper mapper = new ObjectMapper();

    public void loadFromJsonWithFilter(String filePath, int minPower) {
        System.out.println("Загрузка автомобилей (мощность >= " + minPower + ")");
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseCar)
                    .filter(car -> car.getPower() >= minPower)
                    .sorted(Comparator.comparing(Car::getYear).reversed())
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Car parseCar(JsonNode node) {
        return Car.builder()
                .setPower(node.get("power ").asInt())
                .setModel(node.get("model ").asText().trim())
                .setYear(node.get("year ").asInt())
                .build();
    }
}