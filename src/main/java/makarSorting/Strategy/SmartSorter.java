package makarSorting.Strategy;

import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

public class SmartSorter<T> implements SortStrategy<T> {

    private static final int MERGE_SORT_THRESHOLD = 100;

    private final SortStrategy<T> quick = new makarSorting.Strategy.QuickSortStrategy<>();
    private final SortStrategy<T> merge = new makarSorting.Strategy.MergeSortStrategy<>();

    public void sort(List<T> list, Comparator<? super T> comparator) {

        if (shouldUseMerge(list)) {
            merge.sort(list, comparator);
        } else {
            quick.sort(list, comparator);
        }
    }

    private boolean shouldUseMerge(List<T> list) {

        return list.size() > MERGE_SORT_THRESHOLD
                || !(list instanceof RandomAccess);
    }
}