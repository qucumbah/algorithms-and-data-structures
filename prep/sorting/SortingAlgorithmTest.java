package prep.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import prep.Test;

public class SortingAlgorithmTest extends Test {
  public void runTests() {
    var algorithms = new ArrayList<SortingAlgorithm<Integer>>();
    algorithms.add(new HeapSort<Integer>());
    algorithms.add(new QuickSort<Integer>());
    algorithms.add(new MergeSort<Integer>());

    for (SortingAlgorithm<Integer> algorithm : algorithms) {
      runTest(() -> this.test1(algorithm), algorithm.getName() + " check");
    }

    for (int i = 1; i <= 10; i += 1) {
      runTest(() -> this.test2(algorithms), "Sorting algorithms random test " + i);
    }

    runTest(() -> this.test3(algorithms), "Generic test 1");
    runTest(() -> this.test4(algorithms), "Generic test 2");
    runTest(() -> this.test5(algorithms), "Generic test 3");
  }

  private void test1(SortingAlgorithm<Integer> sortingAlgorithm) {
    Integer[] array1 = new Integer[10];
    for (int i = 0; i < array1.length; i += 1) {
      array1[i] = i;
    }

    sortingAlgorithm.sort(array1);
    assertion(isSorted(array1), sortingAlgorithm.getName() + " changed an already sorted array");

    Integer[] array2 = new Integer[10];
    for (int i = 0; i < array2.length; i += 1) {
      array2[i] = 10 - i;
    }

    sortingAlgorithm.sort(array2);
    assertion(isSorted(array2), sortingAlgorithm.getName() + " failed to sort an opposite of a sorted array");
  }

  private void test2(List<SortingAlgorithm<Integer>> sortingAlgorithms) {
    Integer[] array = generateRandomArray(10 + (int)(Math.random() * 10), 10);

    for (SortingAlgorithm<Integer> algorithm : sortingAlgorithms) {
      Integer[] arrayClone = Arrays.copyOf(array, array.length);
      algorithm.sort(arrayClone);
      assertion(
        isSorted(arrayClone),
        algorithm.getName()
        + " failed to sort array "
        + Arrays.toString(array)
        + ". Sorting result: "
        + Arrays.toString(arrayClone)
      );
    }
  }

  private void test3(List<SortingAlgorithm<Integer>> sortingAlgorithms) {
    Integer[] array = new Integer[] { 0, 4, 9, 4 };

    for (SortingAlgorithm<Integer> algorithm : sortingAlgorithms) {
      Integer[] arrayClone = Arrays.copyOf(array, array.length);
      algorithm.sort(arrayClone);
      assertion(
        isSorted(arrayClone),
        algorithm.getName()
        + " failed to sort array "
        + Arrays.toString(array)
        + ". Sorting result: "
        + Arrays.toString(arrayClone)
      );
    }
  }

  private void test4(List<SortingAlgorithm<Integer>> sortingAlgorithms) {
    Integer[] array = new Integer[] { 5, 5, 3, 1 };

    for (SortingAlgorithm<Integer> algorithm : sortingAlgorithms) {
      Integer[] arrayClone = Arrays.copyOf(array, array.length);
      algorithm.sort(arrayClone);
      assertion(
        isSorted(arrayClone),
        algorithm.getName()
        + " failed to sort array "
        + Arrays.toString(array)
        + ". Sorting result: "
        + Arrays.toString(arrayClone)
      );
    }
  }

  private void test5(List<SortingAlgorithm<Integer>> sortingAlgorithms) {
    Integer[] array = new Integer[] { 6, 2, 1, 5, 5 };

    for (SortingAlgorithm<Integer> algorithm : sortingAlgorithms) {
      Integer[] arrayClone = Arrays.copyOf(array, array.length);
      algorithm.sort(arrayClone);
      assertion(
        isSorted(arrayClone),
        algorithm.getName()
        + " failed to sort array "
        + Arrays.toString(array)
        + ". Sorting result: "
        + Arrays.toString(arrayClone)
      );
    }
  }

  private Integer[] generateRandomArray(int size, int maxValue) {
    Integer[] result = new Integer[size];
    for (int i = 0; i < size; i += 1) {
      result[i] = (int)(Math.random() * maxValue);
    }
    return result;
  }

  private boolean isSorted(Integer[] array) {
    for (int i = 1; i < array.length; i += 1) {
      if (array[i].compareTo(array[i - 1]) < 0) {
        return false;
      }
    }

    return true;
  }
}
