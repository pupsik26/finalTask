package daryaClassStream.daryaStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BusStream {
    private final String number;
    private final String model;
    private final int mileage;

    private BusStream(Builder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getNumber() { return number; }
    public String getModel() { return model; }
    public int getMileage() { return mileage; }

    @Override
    public String toString() {
        return "Bus{number='" + number + "', model='" + model + "', mileage=" + mileage + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        BusStream bus = (BusStream) o;
        return mileage == bus.mileage &&
                Objects.equals(number, bus.number) &&
                Objects.equals(model, bus.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, model, mileage);
    }

    public static List<BusStream> loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            return StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(node -> BusStream.builder()
                            .setNumber(String.valueOf(node.get("number ").asInt()))
                            .setModel(node.get("model ").asText().trim())
                            .setMileage(node.get("mileage ").asInt())
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    public static class Builder {
        private String number;
        private String model;
        private int mileage;

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setMileage(int mileage) {
            this.mileage = mileage;
            return this;
        }

        public BusStream build() {
            return new BusStream(this);
        }
    }
}