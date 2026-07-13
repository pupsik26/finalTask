package daryaClassStream.daryaStream;

import daryaClassStream.ModelBuilderClass.Car;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CarStream {

    private final ObjectMapper mapper;

    public CarStream() {
        this.mapper = new ObjectMapper();
    }

    public void loadFromJsonWithFilter(String filePath, int minPower) {
        System.out.println("Загрузка автомобилей (мощность >= " + minPower + ")");

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            var cars = StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(this::parseCar)
                    .filter(Objects::nonNull)
                    .filter(car -> car.getPower() >= minPower)
                    .sorted(Comparator.comparing(Car::getYear).reversed())
                    .collect(Collectors.toList());

            cars.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Car parseCar(JsonNode node) {
        try {
            int power = node.get("power").asInt();
            String model = node.get("model").asText().trim();
            int year = node.get("year").asInt();

            return Car.builder()
                    .setPower(power)
                    .setModel(model)
                    .setYear(year)
                    .build();
        } catch (Exception e) {
            System.out.println("Ошибка парсинга автомобиля: " + e.getMessage());
            return null;
        }
    }
}
