package daryaClassStream.daryaStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BarrelStream {
    private final double volume;
    private final String storedMaterial;
    private final String material;

    private BarrelStream(Builder builder) {
        this.volume = builder.volume;
        this.storedMaterial = builder.storedMaterial;
        this.material = builder.material;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double getVolume() { return volume; }
    public String getStoredMaterial() { return storedMaterial; }
    public String getMaterial() { return material; }

    @Override
    public String toString() {
        return "Barrel{volume=" + volume + ", stores='" + storedMaterial +
                "', madeOf='" + material + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        BarrelStream barrel = (BarrelStream) o;
        return Double.compare(barrel.volume, volume) == 0 &&
                Objects.equals(storedMaterial, barrel.storedMaterial) &&
                Objects.equals(material, barrel.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, storedMaterial, material);
    }

    public static List<BarrelStream> loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            return StreamSupport.stream(rootNode.spliterator(), false)
                    .filter(JsonNode::isObject)
                    .map(node -> BarrelStream.builder()
                            .setVolume(node.get("volume").asDouble())
                            .setStoredMaterial(node.get("storedMaterial").asText().trim())
                            .setMaterial(node.get("material").asText().trim())
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    public static class Builder {
        private double volume;
        private String storedMaterial;
        private String material;

        public Builder setVolume(double volume) {
            this.volume = volume;
            return this;
        }

        public Builder setStoredMaterial(String storedMaterial) {
            this.storedMaterial = storedMaterial;
            return this;
        }

        public Builder setMaterial(String material) {
            this.material = material;
            return this;
        }

        public BarrelStream build() {
            return new BarrelStream(this);
        }
    }
}