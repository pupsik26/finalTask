package ivans.task.validators.objects;

import daryaClassStream.ModelBuilderClass.*;
import ModelBuilderClass.*;
import ivans.task.exceptions.InvalidDataException;

import java.util.HashMap;
import java.util.Map;

public final class ObjectValidatorFactory implements ObjectValidator {

    private static final Map<Class<?>, TypeValidator<?>> validators = new HashMap<>();

    static {
        validators.put(Student.class, new StudentValidator());
        validators.put(Car.class, new CarValidator());
        validators.put(Bus.class, new BusValidator());
        validators.put(Barrel.class, new BarrelValidator());
        validators.put(User.class, new UserValidator());
    }

    private static final ObjectValidator INSTANCE = new ObjectValidatorFactory();

    private ObjectValidatorFactory() {
    }

    public static ObjectValidator getValidator() {
        return INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object object) throws InvalidDataException {
        ValidationUtils.requireNonNull(object, "Object");
        TypeValidator<Object> validator = (TypeValidator<Object>) validators.get(object.getClass());
        if (validator == null) {
            throw new InvalidDataException("Нет валидатора для типа: " + object.getClass().getSimpleName());
        }
        validator.validate(object);
    }
}
