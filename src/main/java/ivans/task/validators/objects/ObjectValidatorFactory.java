package ivans.task.validators.objects;

import ivans.task.common.classes.Barrel;
import ivans.task.common.classes.Bus;
import ivans.task.common.classes.Car;
import ivans.task.common.classes.Student;
import ivans.task.common.classes.User;

import java.util.HashMap;
import java.util.Map;

public final class ObjectValidatorFactory {

    private static final Map<Class<?>, ObjectValidator<?>> validators = new HashMap<>();

    static {
        validators.put(Student.class, new StudentValidator());
        validators.put(Car.class, new CarValidator());
        validators.put(Bus.class, new BusValidator());
        validators.put(Barrel.class, new BarrelValidator());
        validators.put(User.class, new UserValidator());
    }

    private ObjectValidatorFactory() {
    }

    public static <T> ObjectValidator<T> getValidator(Class<T> type) {
        ObjectValidator<?> validator = validators.get(type);
        if (validator == null) {
            throw new IllegalArgumentException("Нет валидатора для типа: " + type.getSimpleName());
        }
        return (ObjectValidator<T>) validator;
    }
}
