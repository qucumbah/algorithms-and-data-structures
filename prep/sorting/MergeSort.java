package prep.sorting;

public class MergeSort<T extends Comparable<T>> implements SortingAlgorithm<T> {
  @Override
  @SuppressWarnings("unchecked")
  public void sort(T[] array) {
    T[] source = array;
    T[] destination = (T[])new Comparable[source.length];

    for (int dist = 2; dist / 2 < array.length; dist *= 2) {
      for (int l = 0; l < array.length; l += dist) {
        int m = l + (dist / 2);

        if (m >= array.length) {
          for (int i = l; i < array.length; i += 1) {
            destination[i] = source[i];
          }

          break;
        }

        int r = l + dist - 1;

        if (r >= array.length) {
          r = array.length - 1;
        }

        merge(source, destination, l, m, r);
      }

      T[] oldDestination = destination;
      destination = source;
      source = oldDestination;
    }

    if (source != array) {
      for (int i = 0; i < array.length; i += 1) {
        array[i] = source[i];
      }
    }
  }

  private void merge(T[] from, T[] to, int start, int middle, int end) {
    int l = start;
    int r = middle;

    int i = 0;
    while (l != middle && r != end + 1) {
      if (from[l].compareTo(from[r]) <= 0) {
        to[start + i] = from[l];
        l += 1;
      } else {
        to[start + i] = from[r];
        r += 1;
      }

      i += 1;
    }

    while (l != middle) {
      to[start + i] = from[l];
      i += 1;
      l += 1;
    }

    while (r != end + 1) {
      to[start + i] = from[r];
      i += 1;
      r += 1;
    }
  }

  @Override
  public String getName() {
    return "Merge sort";
  }
}
