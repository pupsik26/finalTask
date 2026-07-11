package Sort;

import java.util.Comparator;
import java.util.List;

public class SortFacade {

    private final ComparatorRegister comparatorRegister =
            new ComparatorRegister();

    public <T> void sort(
            List<T> list,
            Class<T> className,
            String fieldName) {

        Objects.requireNonNull(list, "Список не должен быть null");
        Objects.requireNonNull(className, "Класс не должен быть null");
        Objects.requireNonNull(fieldName, "Имя поля не должно быть null");

        Comparator<T> comparator =
                comparatorRegister
                        .findComparator(className, fieldName);

        SmartSorter<T> sorter = new SmartSorter<>();

        sorter.sort(list, comparator);
    }

    public <T> void sortEven(
            List<T> list,
            Class<T> className,
            String fieldName) {

        Comparator<T> comparator =
                comparatorRegister
                        .findComparator(className, fieldName);

        evenFieldSorter.sort(list, comparator, className, fieldName);
    }
}