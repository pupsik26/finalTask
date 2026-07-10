package org.example.makarSorting.Strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSortStrategy<T> implements SortStrategy<T> {

    public void mergeSort(List<T> list,
                          int left,
                          int right,
                          Comparator<? super T> comparator) {

        if (left >= right) {
            return;
        }

        int middle = (left + right) / 2;

        mergeSort(list, left, middle, comparator);
        mergeSort(list, middle + 1, right, comparator);

        merge(list, left, middle, right, comparator);
    }

    private void merge(List<T> list,
                       int left,
                       int middle,
                       int right,
                       Comparator<? super T> comparator) {

        List<T> temp = new ArrayList<>();

        int i = left;
        int j = middle + 1;

        while (i <= middle && j <= right) {

            if (comparator.compare(list.get(i), list.get(j)) <= 0) {
                temp.add(list.get(i));
                i++;
            } else {
                temp.add(list.get(j));
                j++;
            }
        }

        while (i <= middle) {
            temp.add(list.get(i));
            i++;
        }

        while (j <= right) {
            temp.add(list.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }

    @Override
    public void sort(List<T> list, Comparator<? super T> comparator) {
        mergeSort(list, 0, list.size() - 1, comparator);
    }
}