package Sort;

import java.util.Comparator;
import java.util.List;

public class QuickSortStrategy<T> implements SortStrategy<T> {

    public void quickSort(List<T> list, int left, int right, Comparator<? super T> comparator) {

        if (left >= right) {
            return;
        }

        int i = left;
        int j = right;

        T pivot = list.get((left + right) / 2);

        while (i <= j) {

            while (comparator.compare(list.get(i), pivot)  < 0 ) {
                i++;
            }

            while (comparator.compare(list.get(j), pivot) > 0) {
                j--;
            }

            if (i <= j) {
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);

                i++;
                j--;
            }
        }

        if (left < j) {
            quickSort(list, left, j, comparator);
        }

        if (i < right) {
            quickSort(list, i, right, comparator);
        }
    }

    @Override
    public void sort(List<T> list, Comparator<? super T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }
}
