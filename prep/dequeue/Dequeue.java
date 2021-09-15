package prep.dequeue;

/**
 * Ideas:
 * 1. Resize when needed
 */
public class Dequeue<T> {
  private T[] inner;

  private int start = 0; // Inclusive
  private int end = 0; // Exclusive
  private int size = 0;

  @SuppressWarnings("unchecked")
  public Dequeue(int size) {
    inner = (T[])new Object[size];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean isFull() {
    return size == inner.length;
  }

  private int getIndex(int index) {
    return (index + inner.length) % inner.length;
  }

  public void pushStart(T item) {
    if (isFull()) {
      throw new Error("Dequeue is full");
    }

    size += 1;

    int newStart = getIndex(start - 1);
    inner[newStart] = item;
    start = newStart;
  }

  public void pushEnd(T item) {
    if (isFull()) {
      throw new Error("Dequeue is full");
    }

    size += 1;

    int newEnd = getIndex(end + 1);
    inner[end] = item;
    end = newEnd;
  }

  public T peekStart() {
    if (isEmpty()) {
      throw new Error("Dequeue is empty");
    }

    return inner[start];
  }

  public T peekEnd() {
    if (isEmpty()) {
      throw new Error("Dequeue is empty");
    }

    return inner[getIndex(end - 1)];
  }

  public T popStart() {
    if (isEmpty()) {
      throw new Error("Dequeue is empty");
    }

    T result = inner[start];

    start = getIndex(start + 1);
    size -= 1;

    return result;
  }

  public T popEnd() {
    if (isEmpty()) {
      throw new Error("Dequeue is empty");
    }

    int newEnd = getIndex(end - 1);
    T result = inner[newEnd];

    end = newEnd;
    size -= 1;

    return result;
  }
}
