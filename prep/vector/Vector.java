package prep.vector;

public class Vector<T extends Comparable<T>> {
  private T[] inner;
  private int nextItemIndex = 0;

  @SuppressWarnings("unchecked")
  public Vector(int capacity) {
    if (capacity <= 0) {
      throw new Error("Capacity should be a positive integer");
    }

    int actualCapacity = 16;

    if (capacity > 16) {
      actualCapacity = (int)Math.round(Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2))));
    }

    inner = (T[])new Comparable[actualCapacity];
  }

  public Vector() {
    this(16);
  }

  public int getSize() {
    return nextItemIndex;
  }

  public int getCapacity() {
    return inner.length;
  }

  public boolean isEmpty() {
    return getSize() == 0;
  }

  public T getItem(int index) {
    if (index < 0 || index >= nextItemIndex) {
      throw new Error("Array index out of bounds");
    }

    return inner[index];
  }

  public void push(T item) {
    resizeIfNeeded(getSize() + 1);
    inner[nextItemIndex] = item;
    nextItemIndex += 1;
  }

  public void insert(int index, T item) {
    if (index < 0 || index > getSize()) {
      throw new Error("Index out of bounds");
    }

    if (index == getSize()) {
      push(item);
      return;
    }

    resizeIfNeeded(getSize() + 1);
    for (int i = nextItemIndex; i > index; i -= 1) {
      inner[i] = inner[i - 1];
    }

    inner[index] = item;
    nextItemIndex += 1;
  }

  public void prepend(T item) {
    insert(0, item);
  }

  public T pop() {
    if (getSize() == 0) {
      throw new Error("No elements present in the vector");
    }

    T result = inner[nextItemIndex - 1];
    resizeIfNeeded(nextItemIndex - 1);
    nextItemIndex -= 1;

    return result;
  }

  public T delete(int index) {
    if (index < 0 || index >= getSize()) {
      throw new Error("Index out of bounds");
    }

    T item = inner[index];

    for (int i = index; i < getSize() - 1; i += 1) {
      inner[i] = inner[i + 1];
    }

    resizeIfNeeded(getSize() - 1);

    nextItemIndex -= 1;

    return item;
  }

  public void remove(T item) {
    int back = 0;
    int forw = 0;

    while (forw < getSize()) {
      inner[back] = inner[forw];

      if (inner[forw].compareTo(item) != 0) {
        back += 1;
      }
      forw += 1;
    }

    resizeIfNeeded(back);
    nextItemIndex = back;
  }

  public int find(T item) {
    for (int i = 0; i < getSize(); i += 1) {
      if (inner[i].compareTo(item) == 0) {
        return i;
      }
    }

    return -1;
  }

  private void resizeIfNeeded(int newSize) {
    int capacity = getCapacity();

    if (newSize > capacity) {
      resize(capacity * 2);
    } else if (newSize <= capacity / 4) {
      resize(capacity / 2);
    }
  }

  @SuppressWarnings("unchecked")
  private void resize(int capacity) {
    if (capacity < 16) {
      return;
    }

    T[] newInner = (T[])new Comparable[capacity];
    for (int i = 0; i < getSize(); i += 1) {
      newInner[i] = inner[i];
    }

    inner = newInner;
  }
}
