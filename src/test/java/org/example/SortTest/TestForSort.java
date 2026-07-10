import Model.User;
import Sort.MergeSortStrategy;
import Sort.QuickSortStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestForSort {

    List<String> duplicateWordsForMergeSort = new ArrayList<>(List.of(
            "apple",
            "orange",
            "apple",
            "banana",
            "banana",
            "kiwi"
    ));
    List<String> duplicateWordsForQuickSort = new ArrayList<>(List.of(
            "apple",
            "orange",
            "apple",
            "banana",
            "banana",
            "kiwi"
    ));

    List<Integer> negativeNumbersForMergeSort = new ArrayList<>(List.of(
            -5, 3, 0, -1, 8, -10, 2, -1, -5, 0, 8
    ));
    List<Integer> negativeNumbersForQuickSort = new ArrayList<>(List.of(
            -5, 3, 0, -1, 8, -10, 2, -1, -5, 0, 8
    ));

    List<Integer> emptyList = new ArrayList<>();

    List<Integer> listWithOneElement = new ArrayList<>(List.of(8));

    @Test
    void shouldSortIntegers() {

        QuickSortStrategy<Integer> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<Integer> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(negativeNumbersForQuickSort, Comparator.naturalOrder());
        mergeSorter.sort(negativeNumbersForMergeSort, Comparator.naturalOrder());

        List<Integer> expected = List.of(
                -10, -5, -5, -1, -1, 0, 0, 2, 3, 8, 8
        );

        assertEquals(expected, negativeNumbersForQuickSort);
        assertEquals(expected, negativeNumbersForMergeSort);
    }

    @Test
    void shouldSortString() {

        QuickSortStrategy<String> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<String> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(duplicateWordsForQuickSort, Comparator.naturalOrder());
        mergeSorter.sort(duplicateWordsForMergeSort, Comparator.naturalOrder());

        List<String> expected = List.of(
                "apple",
                "apple",
                "banana",
                "banana",
                "kiwi",
                "orange"
        );

        assertEquals(expected, duplicateWordsForQuickSort);
        assertEquals(expected, duplicateWordsForMergeSort);
    }

    @Test
    void shouldSortEmptyList() {

        QuickSortStrategy<Integer> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<Integer> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(emptyList, Comparator.naturalOrder());
        mergeSorter.sort(emptyList, Comparator.naturalOrder());

        List<Integer> expected = List.of();

        assertEquals(expected, emptyList);
    }

    @Test
    void shouldSortListWithOneElement() {

        QuickSortStrategy<Integer> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<Integer> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(listWithOneElement, Comparator.naturalOrder());
        mergeSorter.sort(listWithOneElement, Comparator.naturalOrder());

        List<Integer> expected = List.of(8);

        assertEquals(expected, listWithOneElement);
    }
}