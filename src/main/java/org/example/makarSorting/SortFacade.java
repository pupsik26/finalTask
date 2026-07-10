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

        Comparator<T> comparator =
                comparatorRegister
                        .findComparator(className, fieldName);

        SmartSorter<T> sorter = new SmartSorter<>();

        sorter.sort(list, comparator);
    }
}