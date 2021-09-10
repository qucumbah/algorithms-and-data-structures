package prep.vector;

import prep.Test;

public class VectorTest extends Test {
  public void runTests() {
    runTest(this::creation, "Vector creation capacity check");
    runTest(this::capacityModification, "Vector capacity modification check");
    runTest(this::insertion, "Vector insertion check");
    runTest(this::deletion, "Vector deletion check");
    runTest(this::removal, "Vector removal check");
  }

  private void creation() {
    var v1 = new Vector<Integer>(5);
    assertEquals(v1.getCapacity(), 16, "Vector with initial capacity of 5 should have capacity of 16");
    assertEquals(v1.getSize(), 0, "Vector size should be 0");

    var v2 = new Vector<Integer>(16);
    assertEquals(v2.getCapacity(), 16, "Vector with initial capacity of 16 should have capacity of 16");

    var v3 = new Vector<Integer>(17);
    assertEquals(v3.getCapacity(), 32, "Vector with initial capacity of 17 should have capacity of 32");

    var v4 = new Vector<Integer>(63);
    assertEquals(v4.getCapacity(), 64, "Vector with initial capacity of 63 should have capacity of 64");
  }

  private void capacityModification() {
    var v1 = new Vector<Integer>(16);

    for (int i = 0; i < 128; i += 1) {
      v1.push(i);
    }

    assertEquals(v1.getCapacity(), 128, "Vector should have capacity of 128 after adding 128 elements");
    assertEquals(v1.getSize(), 128, "Vector size should be 128 after adding 16 elements");

    v1.push(0);

    assertEquals(v1.getCapacity(), 256, "Vector should have capacity of 256 after adding the 129th element");
    assertEquals(v1.getSize(), 129, "Vector size should be 129 after adding the 129th element");

    for (int i = 0; i < 70; i += 1) {
      v1.pop();
    }

    assertEquals(v1.getCapacity(), 128, "Vector should have capacity of 128 after size has reduced from 129 to 59");
    assertEquals(v1.getSize(), 59, "Vector size should be 59 after size has reduced from 129 to 59");

    var v2 = new Vector<Integer>();
    for (int i = 0; i < 1024; i += 1) {
      v2.push(i);
    }
    for (int i = 0; i < 768; i += 1) {
      v2.pop();
    }

    int firstWrongItemIndex = -1;
    for (int i = 0; i < v2.getSize(); i += 1) {
      if (v2.getItem(i) != i) {
        System.out.println(v2.getItem(i));
        firstWrongItemIndex = i;
        break;
      }
    }

    assertion(
      firstWrongItemIndex == -1,
      "Vector should have all elements in order. Wrong item index: " + firstWrongItemIndex
    );
  }

  private void insertion() {
    var v = new Vector<Integer>();
    for (int i = 0; i < 30; i += 1) {
      v.push(i);
    }

    v.insert(7, 15);
    assertEquals(v.getSize(), 31, "Vector size should be 31 after inserting a new element");
    assertEquals(v.getItem(6), 6, "Vector items before the inserted one should stay untouched");
    assertEquals(v.getItem(7), 15, "Vector item should have been inserted");
    assertEquals(v.getItem(8), 7, "Vector items after the inserted one should have shifted");
  }

  private void deletion() {
    var v1 = new Vector<Integer>();
    for (int i = 0; i < 30; i += 1) {
      v1.push(i);
    }

    v1.delete(7);
    assertEquals(v1.getSize(), 29, "Vector size should be 29 after deleting an element");
    assertEquals(v1.getItem(6), 6, "Vector items before the deleted one should stay untouched");
    assertEquals(v1.getItem(7), 8, "Vector items should have been shifted");
    assertEquals(v1.getItem(8), 9, "Vector items should have been shifted");

    try {
      v1.getItem(29);
      assertion(false, "Vector should fail on accessing invalid element");
    } catch (Error e) {

    }
  }

  private void removal() {
    var v1 = new Vector<Integer>();
    for (int i = 0; i < 30; i += 1) {
      v1.push(i);
    }

    v1.remove(7);
    assertEquals(v1.getSize(), 29, "Vector size should be 29 after removing an element");
    assertEquals(v1.getItem(6), 6, "Vector items before the removed one should stay untouched");
    assertEquals(v1.getItem(7), 8, "Vector items should have been shifted");
    assertEquals(v1.getItem(8), 9, "Vector items should have been shifted");

    try {
      v1.getItem(29);
      assertion(false, "Vector should fail on accessing invalid element");
    } catch (Error e) {

    }

    var v2 = new Vector<Integer>();
    v2.push(2);
    v2.push(3);
    v2.push(4);
    v2.push(2);
    v2.push(2);
    v2.push(6);
    v2.push(3);

    assertEquals(v2.getSize(), 7, "Vector size should be 7 initially");
    
    v2.remove(2);

    assertEquals(v2.getSize(), 4, "Vector size should be 4 after removing all '2' elements");

    assertEquals(v2.getItem(0), 3, "Vector elements have shifted incorrectly after removing multiple elements");
    assertEquals(v2.getItem(1), 4, "Vector elements have shifted incorrectly after removing multiple elements");
    assertEquals(v2.getItem(2), 6, "Vector elements have shifted incorrectly after removing multiple elements");
    assertEquals(v2.getItem(3), 3, "Vector elements have shifted incorrectly after removing multiple elements");
  }
}
