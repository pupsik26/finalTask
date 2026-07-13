package ivans.task.validators.objects;

import ModelBuilderClass.Barrel;
import ivans.task.exceptions.InvalidDataException;

class BarrelValidator implements TypeValidator<Barrel> {

    static final int MIN_VOLUME = 0;
    static final double MAX_VOLUME = 50_000;

    @Override
    public void validate(Barrel barrel) throws InvalidDataException {
        ValidationUtils.requireNonNull(barrel, "Barrel");
        validateVolume(barrel.getVolume());
        validateStoredMaterial(barrel.getStoredMaterial());
        validateMaterial(barrel.getMaterial());
    }

    private void validateVolume(double volume) throws InvalidDataException {
        if (volume <= MIN_VOLUME || volume > MAX_VOLUME) {
            throw new InvalidDataException("Объём бочки должен быть в диапазоне от " + MIN_VOLUME + " до " + MAX_VOLUME + ", получено: " + volume);
        }
    }

    private void validateStoredMaterial(String storedMaterial) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(storedMaterial, "Хранимый материал", ValidationUtils.TEXT_LETTERS_ONLY);
    }

    private void validateMaterial(String material) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(material, "Материал бочки", ValidationUtils.TEXT_LETTERS_ONLY);
    }
}
