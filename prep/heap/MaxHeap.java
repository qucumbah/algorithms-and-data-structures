package prep.heap;

public class MaxHeap<T extends Comparable<T>> {
  private T[] inner;
  private int nextItemIndex;

  @SuppressWarnings("unchecked")
  public MaxHeap() {
    inner = (T[])new Comparable[16];
    nextItemIndex = 0;
  }

  public void insert(T item) {
    resizeIfNeeded(nextItemIndex + 1);
    inner[nextItemIndex] = item;
    nextItemIndex += 1;
    bubbleUp(nextItemIndex - 1);
  }

  public T extractMax() {
    if (nextItemIndex == 0) {
      return null;
    }

    T max = inner[0];

    remove(max);

    return max;
  }

  public void remove(T item) {
    int itemIndex = findItem(item, 0);

    if (itemIndex == -1) {
      return;
    }

    nextItemIndex -= 1;
    T replacement = inner[nextItemIndex];
    inner[itemIndex] = replacement;
    resizeIfNeeded(nextItemIndex);

    if (replacement.compareTo(item) > 0) {
      bubbleUp(itemIndex);
    } else {
      bubbleDown(itemIndex);
    }
  }

  private int findItem(T item, int from) {
    if (from >= nextItemIndex) {
      return -1;
    }

    if (item.compareTo(inner[from]) == 0) {
      return from;
    } else if (item.compareTo(inner[from]) > 0) {
      return -1;
    }

    int leftSubtreeSearchResult = findItem(item, getLeftChildIndex(from));
    if (leftSubtreeSearchResult != -1) {
      return leftSubtreeSearchResult;
    }

    int rightSubtreeSearchResult = findItem(item, getRightChildIndex(from));
    if (rightSubtreeSearchResult != -1) {
      return rightSubtreeSearchResult;
    }

    return -1;
  }

  private void build() {
    for (int i = nextItemIndex / 2 - 1; i >= 0; i -= 1) {
      bubbleDown(i);
    }
  }

  private void bubbleUp(int itemIndex) {
    if (itemIndex == 0) {
      return;
    }

    int parentIndex = getParentIndex(itemIndex);
    if (inner[parentIndex].compareTo(inner[itemIndex]) >= 0) {
      return;
    }

    T child = inner[itemIndex];
    inner[itemIndex] = inner[parentIndex];
    inner[parentIndex] = child;

    bubbleDown(itemIndex);
    bubbleUp(parentIndex);
  }

  private void bubbleDown(int itemIndex) {
    int leftChildIndex = getLeftChildIndex(itemIndex);

    if (leftChildIndex >= nextItemIndex) {
      return;
    }

    int rightChildIndex = getRightChildIndex(itemIndex);

    T parent = inner[itemIndex];
    T leftChild = inner[leftChildIndex];

    if (rightChildIndex == nextItemIndex) {
      if (leftChild.compareTo(parent) > 0) {
        inner[itemIndex] = leftChild;
        inner[leftChildIndex] = parent;
      }

      return;
    }

    T rightChild = inner[rightChildIndex];

    int maxChildIndex = (leftChild.compareTo(rightChild) > 0) ? leftChildIndex : rightChildIndex;
    T maxChild = inner[maxChildIndex];
    if (parent.compareTo(maxChild) > 0) {
      return;
    }

    inner[maxChildIndex] = parent;
    inner[itemIndex] = maxChild;
    bubbleDown(maxChildIndex);
  }

  private int getLeftChildIndex(int parent) {
    return parent * 2 + 1;
  }
  private int getRightChildIndex(int parent) {
    return parent * 2 + 2;
  }
  private int getParentIndex(int child) {
    return (child - 1) / 2;
  }

  public T getMax() {
    if (nextItemIndex == 0) {
      return null;
    }

    return inner[0];
  }

  public int getSize() {
    return nextItemIndex;
  }

  private boolean resizingBlocked = false;

  @SuppressWarnings("unchecked")
  private void resizeIfNeeded(int newSize) {
    if (resizingBlocked) {
      return;
    }

    boolean shouldIncreaseSize = newSize >= inner.length;
    boolean shouldDecreaseSize = inner.length != 16 && newSize <= inner.length / 4;

    if (!shouldIncreaseSize && !shouldDecreaseSize) {
      return;
    }

    int newInnerLength = shouldIncreaseSize ? inner.length * 2 : inner.length / 2;
    T[] newInner = (T[])new Comparable[newInnerLength];
    for (int i = 0; i < nextItemIndex; i += 1) {
      newInner[i] = inner[i];
    }

    inner = newInner;
  }

  public static <T extends Comparable<T>> void heapSort(T[] items) {
    var heap = new MaxHeap<T>();
    heap.inner = items;
    heap.nextItemIndex = items.length;
    heap.resizingBlocked = true;

    heap.build();

    for (int i = items.length - 1; i > 0; i -= 1) {
      items[i] = heap.extractMax();
    }
  }
}
