package prep.heap;

import java.util.Arrays;

import prep.Test;
import prep.hashtable.HashTableChaining;

public class MaxHeapTest extends Test {
  public void runTests() {
    runTest(this::test1, "Insertion and extraction");
    runTest(this::test2, "Removal");

    for (int i = 1; i <= 10; i += 1) {
      runTest(this::test3, "Insertion random test " + i);
    }

    for (int i = 1; i <= 100; i += 1) {
      runTest(this::test4, "Removal random test " + i);
    }

    runTest(
      () -> this.genericTest(
        new int[] { 3, 0, 5, 4, 9, 7, 8, 6, 1, 2 },
        9
      ),
      "Generic test 1"
    );
  }

  private void test1() {
    var heap = new MaxHeap<Integer>();

    assertEquals(heap.getSize(), 0, "Heap size is invalid from the start");

    heap.insert(2);
    heap.insert(5);
    heap.insert(3);
    heap.insert(1);
    heap.insert(4);

    assertEquals(heap.getSize(), 5, "Heap size is invalid after adding 5 elements");

    for (int i = 5; i >= 1; i -= 1) {
      assertEquals(heap.getMax(), i, "Heap has returned invalid maximum element");
      assertEquals(heap.extractMax(), i, "Heap has extracted invalid maximum element");
    }
    
    assertEquals(heap.getSize(), 0, "Heap size is invalid after extracting all elements");
  }

  private void test2() {
    var heap = new MaxHeap<Integer>();

    heap.insert(5);
    heap.insert(4);
    heap.insert(6);
    heap.insert(2);
    heap.insert(3);
    heap.insert(1);

    assertEquals(heap.getSize(), 6, "Invalid heap size after construction");

    heap.remove(3);

    assertEquals(heap.getSize(), 5, "Invalid heap size after removal");

    heap.remove(3);

    assertEquals(heap.getSize(), 5, "Invalid heap size after removing an already removed element");

    heap.remove(1);

    assertEquals(heap.getSize(), 4, "Invalid heap size after removal");

    heap.remove(1);

    assertEquals(heap.getSize(), 4, "Invalid heap size after removing an already removed element");

    heap.remove(0);

    assertEquals(heap.getSize(), 4, "Invalid heap size after removing a non-existing element");
  }

  private void test3() {
    int size = 10 + (int)(Math.random() * 10);
    int[] items = new int[size];

    for (int i = 0; i < size; i += 1) {
      items[i] = i;
    }

    shuffle(items);
    
    var heap = new MaxHeap<Integer>();
    for (int item : items) {
      heap.insert(item);
    }

    assertEquals(heap.getSize(), size, "Constructed heap has invalid size");

    for (int i = size - 1; i >= 0; i -= 1) {
      assertEquals(heap.getMax(), i, "Heap has returned invalid maximum element");
      assertEquals(heap.extractMax(), i, "Heap has extracted invalid maximum element");
      assertEquals(heap.getSize(), i, "Heap has invalid size after removing an element");
    }

    if (haveAssertionsFailed()) {
      System.out.println("Failing test case:");
      System.out.println("Insertions: " + Arrays.toString(items));
    }
  }

  private void test4() {
    int size = 10 + (int)(Math.random() * 10);
    int[] items = new int[size];

    for (int i = 0; i < size; i += 1) {
      items[i] = i;
    }

    shuffle(items);
    
    int numberOfElementsToRemove = (int)(Math.random() * size);

    genericTest(items, numberOfElementsToRemove);

    if (haveAssertionsFailed()) {
      System.out.println("Failing test case:");
      System.out.println("Insertions: " + Arrays.toString(items));
      System.out.println("Removed " + numberOfElementsToRemove + " first inserted elements");
    }
  }

  private void shuffle(int[] items) {
    for (int i = items.length - 1; i > 0; i -= 1) {
      int j = (int)(Math.random() * (i + 1));

      int temp = items[i];
      items[i] = items[j];
      items[j] = temp;
    }
  }

  private void genericTest(int[] items, int numberOfElementsToRemove) {
    var heap = new MaxHeap<Integer>();
    
    for (int item : items) {
      heap.insert(item);
    }

    assertEquals(heap.getSize(), items.length, "Constructed heap has invalid size");

    var removed = new HashTableChaining<Integer, Integer>();

    for (int i = 0; i < numberOfElementsToRemove; i += 1) {
      heap.remove(items[i]);
      assertEquals(heap.getSize(), items.length - (i + 1), "Heap has invalid size after removing an element");
      removed.set(items[i], i);
    }

    assertEquals(heap.getSize(), items.length - numberOfElementsToRemove, "Heap has invalid size after removing elements");

    for (int i = items.length - 1; i >= 0; i -= 1) {
      if (removed.get(i) != null) {
        int sizeBefore = heap.getSize();
        heap.remove(i);
        assertEquals(heap.getSize(), sizeBefore, "Heap size has changed after removing a non-existing element");
      } else {
        assertEquals(heap.getMax(), i, "Heap has returned invalid maximum element");
        assertEquals(heap.extractMax(), i, "Heap has extracted invalid maximum element");
      }
    }

    assertEquals(heap.getSize(), 0, "Heap should be empty after removing all elements");
  }
}
