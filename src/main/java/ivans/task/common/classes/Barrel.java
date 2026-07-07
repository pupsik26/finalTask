package ivans.task.common.classes;

public class Barrel {
    private double volume;
    private String storedMaterial;
    private String material;

    public Barrel(double volume, String storedMaterial, String material) {
        this.volume = volume;
        this.storedMaterial = storedMaterial;
        this.material = material;
    }

    public double getVolume() { return volume; }
    public String getStoredMaterial() { return storedMaterial; }
    public String getMaterial() { return material; }

    public void setVolume(double volume) { this.volume = volume; }
    public void setStoredMaterial(String storedMaterial) { this.storedMaterial = storedMaterial; }
    public void setMaterial(String material) { this.material = material; }

    @Override
    public String toString() {
        return "Barrel{volume=" + volume + ", storedMaterial='" + storedMaterial + "', material='" + material + "'}";
    }
}
