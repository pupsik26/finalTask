package Sort;

import Model.SortedClass;
import Model.Student;
import Model.User;

import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;

public class ComparatorRegister {

    private final Map<Class<?>, Map<String, Comparator<?>>> registry = Map.of(
            User.class, Map.of(
                    "name", Comparator.comparing(User::getName),
                    "password", Comparator.comparing(User::getPassword),
                    "email", Comparator.comparing(User::getEmail)
            ),
            Student.class, Map.of(
                    "groupNumber", Comparator.comparing(Student::getGroupNumber),
                    "gpa", Comparator.comparing(Student::getGpa),
                    "recordBookNumber", Comparator.comparing(Student::getRecordBookNumber)
            ),
            Car.class, Map.of(
                    "power", Comparator.comparing(Car::getPower),
                    "model", Comparator.comparing(Car::getModel),
                    "year", Comparator.comparing(Car::getYear)
            ),
            Bus.class, Map.of(
                    "number", Comparator.comparing(Bus::getNumber),
                    "model", Comparator.comparing(Bus::getModel),
                    "mileage", Comparator.comparing(Bus::getMileage)
            ),
            Barrel.class, Map.of(
                    "volume", Comparator.comparing(Barrel::getVolume),
                    "storedMaterial", Comparator.comparing(Barrel::getStoredMaterial),
                    "material", Comparator.comparing(Barrel::getMaterial)
            )
    );

    public <T> Comparator<T> findComparator(Class<T> someClass,
                                            String someField) {

        Map<String, Comparator<?>> comparatorMap = registry.get(someClass);

        if (comparatorMap == null) {
            throw new NoSuchElementException("Ошибка в названии класса");
        }

        Comparator<?> comparator = comparatorMap.get(someField);

        if (comparator == null) {
            throw new NoSuchElementException("Ошибка в названии поля");
        }

        return (Comparator<T>) comparator;
    }
}
