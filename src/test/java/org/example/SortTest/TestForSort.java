package org.example.SortTest;

import makarSorting.Strategy.MergeSortStrategy;
import makarSorting.Strategy.QuickSortStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestForSort {

    @Test
    void shouldSortIntegers() {

        List<Integer> quickList = new ArrayList<>(List.of(
                -5, 3, 0, -1, 8, -10, 2, -1, -5, 0, 8
        ));

        List<Integer> mergeList = new ArrayList<>(quickList);

        QuickSortStrategy<Integer> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<Integer> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(quickList, Comparator.naturalOrder());
        mergeSorter.sort(mergeList, Comparator.naturalOrder());

        List<Integer> expected = List.of(
                -10, -5, -5, -1, -1, 0, 0, 2, 3, 8, 8
        );

        assertEquals(expected, quickList);
        assertEquals(expected, mergeList);
    }

    @Test
    void shouldSortStrings() {

        List<String> quickList = new ArrayList<>(List.of(
                "apple",
                "orange",
                "apple",
                "banana",
                "banana",
                "kiwi"
        ));

        List<String> mergeList = new ArrayList<>(quickList);

        QuickSortStrategy<String> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<String> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(quickList, Comparator.naturalOrder());
        mergeSorter.sort(mergeList, Comparator.naturalOrder());

        List<String> expected = List.of(
                "apple",
                "apple",
                "banana",
                "banana",
                "kiwi",
                "orange"
        );

        assertEquals(expected, quickList);
        assertEquals(expected, mergeList);
    }

    @Test
    void shouldSortEmptyList() {

        List<Integer> quickList = new ArrayList<>();
        List<Integer> mergeList = new ArrayList<>();

        QuickSortStrategy<Integer> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<Integer> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(quickList, Comparator.naturalOrder());
        mergeSorter.sort(mergeList, Comparator.naturalOrder());

        assertTrue(quickList.isEmpty());
        assertTrue(mergeList.isEmpty());
    }

    @Test
    void shouldSortListWithOneElement() {

        List<Integer> quickList = new ArrayList<>(List.of(8));
        List<Integer> mergeList = new ArrayList<>(List.of(8));

        QuickSortStrategy<Integer> quickSorter = new QuickSortStrategy<>();
        MergeSortStrategy<Integer> mergeSorter = new MergeSortStrategy<>();

        quickSorter.sort(quickList, Comparator.naturalOrder());
        mergeSorter.sort(mergeList, Comparator.naturalOrder());

        assertEquals(List.of(8), quickList);
        assertEquals(List.of(8), mergeList);
    }
}