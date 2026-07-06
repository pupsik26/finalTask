package ModelClass;

import java.util.Objects;

public class Barrel {

    protected double volume;
    protected String storedMaterial;
    protected String material;

    protected Barrel() {
    }

    protected Barrel(double volume, String storedMaterial, String material) {
        this.volume = volume;
        this.storedMaterial = storedMaterial;
        this.material = material;
    }

    public double getVolume() {
        return volume;
    }

    public String getStoredMaterial() {
        return storedMaterial;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return "Barrel{volume=" + volume + ", stores='" + storedMaterial +
                "', madeOf='" + material + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barrel barrel = (Barrel) o;
        return Double.compare(barrel.volume, volume) == 0 &&
                Objects.equals(storedMaterial, barrel.storedMaterial) &&
                Objects.equals(material, barrel.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume, storedMaterial, material);
    }
}