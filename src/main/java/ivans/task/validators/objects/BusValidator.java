package ivans.task.validators.objects;

import daryaClassStream.ModelBuilderClass.Bus;
import ivans.task.exceptions.InvalidDataException;

class BusValidator implements TypeValidator<Bus> {

    static final int MIN_NUMBER = 0;
    static final int MIN_MILEAGE = 0;

    @Override
    public void validate(Bus bus) throws InvalidDataException {
        ValidationUtils.requireNonNull(bus, "Bus");
        validateNumber(Integer.parseInt(bus.getNumber()));
        validateModel(bus.getModel());
        validateMileage(bus.getMileage());
    }

    private void validateNumber(int number) throws InvalidDataException {
        if (number <= MIN_NUMBER) {
            throw new InvalidDataException("Номер автобуса должен быть положительным, получено: " + number);
        }
    }

    private void validateModel(String model) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(model, "Модель автобуса", ValidationUtils.TEXT_WITH_DIGITS);
    }

    private void validateMileage(int mileage) throws InvalidDataException {
        if (mileage < MIN_MILEAGE) {
            throw new InvalidDataException("Пробег должен быть больше " + MIN_MILEAGE + ", получено: " + mileage);
        }
    }
}
