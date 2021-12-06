package prep.sorting;

public interface SortingAlgorithm<T extends Comparable<T>> {
  public void sort(T[] array);
  public String getName();
}
