package makarSorting;

import makarSorting.Strategy.SmartSorter;

import java.util.Comparator;
import java.util.List;

public class SortFacade {

    private final ComparatorRegister comparatorRegister =
            new ComparatorRegister();

    private final EvenFieldSorter evenFieldSorter =
            new EvenFieldSorter();

    public <T> void sort(
            List<Object> list,
            Class<?> className,
            String fieldName) {

        Comparator<T> comparator =
                (Comparator<T>) comparatorRegister
                        .findComparator(className, fieldName);

        SmartSorter<T> sorter = new SmartSorter<>();

        sorter.sort((List<T>) list, comparator);
    }

    public <T> void sortEven(
            List<Object> list,
            Class<?> className,
            String fieldName) {

        Comparator<T> comparator =
                (Comparator<T>) comparatorRegister.findComparator(className, fieldName);

        evenFieldSorter.sort(
                (List<T>) list,
                comparator,
                (Class<T>) className,
                fieldName
        );
    }
}