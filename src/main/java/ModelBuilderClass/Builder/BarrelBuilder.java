package ModelBuilderClass.Builder;

import ModelBuilderClass.ModelClass.Barrel;

public class BarrelBuilder extends Barrel {

    public BarrelBuilder() {
        super();
    }

    public BarrelBuilder setVolume(double volume) {
        this.volume = volume;
        return this;
    }

    public BarrelBuilder setStoredMaterial(String storedMaterial) {
        this.storedMaterial = storedMaterial;
        return this;
    }

    public BarrelBuilder setMaterial(String material) {
        this.material = material;
        return this;
    }
}