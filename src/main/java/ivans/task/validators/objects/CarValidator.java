package ivans.task.validators.objects;

import ModelBuilderClass.ModelClass.Car;
import ivans.task.exceptions.InvalidDataException;

import java.time.Year;

class CarValidator implements TypeValidator<Car> {

    static final int MIN_YEAR = 1900;
    static final int MIN_POWER = 0;
    static final int MAX_POWER = 2000;

    @Override
    public void validate(Car car) throws InvalidDataException {
        ValidationUtils.requireNonNull(car, "Car");
        validatePower(car.getPower());
        validateModel(car.getModel());
        validateYear(car.getYear());
    }

    private void validatePower(int power) throws InvalidDataException {
        if (power <= MIN_POWER || power > MAX_POWER) {
            throw new InvalidDataException("Мощность должна быть в диапазоне от " + MIN_POWER + " до " + MAX_POWER + ", получено: " + power);
        }
    }

    private void validateModel(String model) throws InvalidDataException {
        ValidationUtils.requireNonEmptyText(model, "Модель автомобиля", ValidationUtils.TEXT_WITH_DIGITS);
    }

    private void validateYear(int year) throws InvalidDataException {
        if (year < MIN_YEAR || year > Year.now().getValue()) {
            throw new InvalidDataException("Год производства должен быть в диапазоне " + MIN_YEAR + "-" + Year.now().getValue() + ", получено: " + year);
        }
    }
}
