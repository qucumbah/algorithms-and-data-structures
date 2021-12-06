package prep.sorting;

import prep.heap.MaxHeap;

public class HeapSort<T extends Comparable<T>> implements SortingAlgorithm<T> {
  @Override
  public void sort(T[] array) {
    MaxHeap.heapSort(array);
  }

  @Override
  public String getName() {
    return "Heap sort";
  }
}
