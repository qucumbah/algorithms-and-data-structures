package prep.sorting;

public class QuickSort<T extends Comparable<T>> implements SortingAlgorithm<T> {
  @Override
  public void sort(T[] array) {
    qsort(array, 0, array.length - 1);
  }

  private void qsort(T[] array, int l, int r) {
    if (r > l) {
      int m = partition(array, l, r);
      qsort(array, l, m);
      qsort(array, m + 1, r);
    }
  }

  private int partition(T[] array, int l, int r) {
    T pivot = array[(l + r) / 2];

    while (true) {
      while (array[l].compareTo(pivot) < 0) {
        l += 1;
      }

      while (array[r].compareTo(pivot) > 0) {
        r -= 1;
      }

      if (l >= r) {
        return r;
      }

      T temp = array[l];
      array[l] = array[r];
      array[r] = temp;

      l += 1;
      r -= 1;
    }
  }

  @Override
  public String getName() {
    return "Quick sort";
  }
}
