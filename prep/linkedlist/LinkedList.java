package prep.linkedlist;

/**
 * Other ideas:
 * 1. List where size is stored as an int
 * 2. List with tail pointer
 * 3. Doubly-linked list
 */

public class LinkedList<T extends Comparable<T>> {
  public class Link {
    T value;
    Link next;

    public Link(T value) {
      this.value = value;
      next = null;
    }
  }

  Link head = null;

  public LinkedList() { }

  public int size() {
    Link cur = head;
    int result = 0;

    while (cur != null) {
      result += 1;
      cur = cur.next;
    }

    return result;
  }

  public boolean isEmpty() {
    return head == null;
  }

  public T getItem(int index) {
    if (head == null) {
      throw new Error("Index out of bounds");
    }

    Link cur = head;

    for (int i = 0; i < index; i += 1) {
      if (cur == null) {
        throw new Error("Index out of bounds");
      }

      cur = cur.next;
    }

    return cur.value;
  }

  public void pushFront(T item) {
    Link oldHead = head;
    head = new Link(item);
    head.next = oldHead;
  }

  public T popFront() {
    if (head == null) {
      throw new Error("Index out of bounds");
    }

    T result = head.value;
    head = head.next;

    return result;
  }

  public void pushBack(T item) {
    if (head == null) {
      head = new Link(item);
      return;
    }

    Link cur = head;
    while (cur.next != null) {
      cur = cur.next;
    }

    cur.next = new Link(item);
  }

  public T popBack() {
    if (head == null) {
      throw new Error("Index out of bounds");
    }

    if (head.next == null) {
      T result = head.value;
      head = null;
      return result;
    }

    Link cur = head;
    while (cur.next.next != null) {
      cur = cur.next;
    }

    T result = cur.next.value;
    cur.next = null;

    return result;
  }

  public T front() {
    if (head == null) {
      throw new Error("Index out of bounds");
    }

    return head.value;
  }

  public T back() {
    if (head == null) {
      throw new Error("Index out of bounds");
    }

    Link cur = head;
    while (cur.next != null) {
      cur = cur.next;
    }

    T result = cur.value;

    return result;
  }

  public void insert(int index, T item) {
    if (index == 0) {
      pushFront(item);
      return;
    }

    // Find the element before the inserted one
    Link cur = head;
    for (int i = 0; i < index - 1; i += 1) {
      if (cur == null) {
        throw new Error("Index out of bounds");
      }

      cur = cur.next;
    }

    Link oldNext = cur.next;
    cur.next = new Link(item);
    cur.next.next = oldNext;
  }

  public T erase(int index) {
    if (index == 0) {
      return popFront();
    }

    // Find the element before the erased one
    Link cur = head;
    for (int i = 0; i < index - 1; i += 1) {
      if (cur == null) {
        throw new Error("Index out of bounds");
      }

      cur = cur.next;
    }

    if (cur.next == null) {
      throw new Error("Index out of bounds");
    }

    T result = cur.next.value;
    cur.next = cur.next.next;

    return result;
  }

  // 0-based
  public T getItemFromEnd(int index) {
    // 0 1 2 3 4
    // 5
    Link forw = head;
    for (int i = 0; i < index; i += 1) {
      if (forw == null) {
        throw new Error("Index out of bounds");
      }

      forw = forw.next;
    }

    // System.out.println(forw.value);

    if (forw == null) {
      throw new Error("Index out of bounds");
    }

    Link back = head;
    while (forw.next != null) {
      forw = forw.next;
      back = back.next;
    }

    return back.value;
  }

  public void reverse() {
    // 0 1 2 3 4
    if (head == null || head.next == null) {
      return;
    }

    Link prev = head;
    Link cur = head.next;
    while (cur != null) {
      Link next = cur.next;
      cur.next = prev;

      prev = cur;
      cur = next;
    }

    head.next = null;
    head = prev;
  }

  public int remove(T item) {
    if (head == null) {
      return -1;
    }

    if (head.next == null) {
      if (head.value.compareTo(item) != 0) {
        return -1;
      }

      head = null;
      return 0;
    }

    // Find the element before the removed one
    Link cur = head;
    int curIndex = 0;
    while (cur.next != null && cur.next.value.compareTo(item) != 0) {
      cur = cur.next;
      curIndex += 1;
    }

    if (cur.next == null) {
      return -1;
    }

    cur.next = cur.next.next;

    return curIndex + 1;
  }
}
