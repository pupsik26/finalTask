package daryaClassStream.daryaStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CarStream {
    private final int power;
    private final String model;
    private final int year;

    private CarStream(Builder builder) {
        this.power = builder.power;
        this.model = builder.model;
        this.year = builder.year;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getPower() { return power; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return "Car{power=" + power + ", model='" + model + "', year=" + year + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        CarStream car = (CarStream) o;
        return power == car.power && year == car.year && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }

    public static List<CarStream> loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            return StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(node -> CarStream.builder()
                            .setPower(node.get("power ").asInt())
                            .setModel(node.get("model ").asText().trim())
                            .setYear(node.get("year ").asInt())
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    public static class Builder {
        private int power;
        private String model;
        private int year;

        public Builder setPower(int power) {
            this.power = power;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public CarStream build() {
            return new CarStream(this);
        }
    }
}