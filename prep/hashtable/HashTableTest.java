package prep.hashtable;

import prep.Test;

public class HashTableTest extends Test {
  public void runTests() {
    runTest(this::test1, "Capacity, size, set, get, remove");
    runTest(this::test2, "Linear probing");
  }

  private void test1() {
    var hashtable = new HashTable<Integer, Integer>();
    assertEquals(hashtable.getCapacity(), 8, "HashTable capacity should be 8 initially");
    assertEquals(hashtable.getSize(), 0, "HashTable size should be 0 initially");

    for (int i = 0; i < 8; i += 1) {
      hashtable.set(i, i);
    }

    assertEquals(hashtable.getCapacity(), 8, "HashTable capacity should be 8 after adding 8 elements");
    assertEquals(hashtable.getSize(), 8, "HashTable size should be 8 after adding 8 elements");

    hashtable.remove(0);

    assertEquals(hashtable.getCapacity(), 8, "HashTable capacity should still be 8 after removing 1 element");
    assertEquals(hashtable.getSize(), 7, "HashTable size should be 7 after removing 1 element");

    hashtable.set(0, 0);
    hashtable.set(8, 8);

    assertEquals(hashtable.getCapacity(), 16, "HashTable capacity should double after adding the 9th element");
    assertEquals(hashtable.getSize(), 9, "HashTable size should be 9 after adding the 9th element");

    for (int i = 0; i < 9; i += 1) {
      assertEquals(hashtable.get(i), i, "HashTable elements should be in order");
    }

    for (int i = 0; i < 7; i += 1) {
      assertion(hashtable.remove(i) == i, "Removal of " + i + "th element should happen");
    }

    assertEquals(hashtable.getCapacity(), 8, "HashTable capacity should half after removing 7/9 elements");
    assertEquals(hashtable.getSize(), 2, "HashTable size should be 2 after removing 7/9 elements");

    for (int i = 0; i < 7; i += 1) {
      assertion(hashtable.get(i) == null, "HashTable should not have any inaccessible elements");
    }

    assertEquals(hashtable.get(7), 7, "HashTable should not have removed other elements");
    assertEquals(hashtable.get(8), 8, "HashTable should not have removed other elements");
  }

  private void test2() {
    var hashtable1 = new HashTable<Integer, Integer>();

    hashtable1.set(0, 0);

    assertion(hashtable1.get(8) == null, "Should not get inaccessible element");

    hashtable1.set(8, 8);

    assertEquals(hashtable1.getSize(), 2, "Hash table should have 2 elements");
    assertEquals(hashtable1.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable1.get(8), 8, "Hash table elements should be correct");

    hashtable1.set(1, 1);

    assertEquals(hashtable1.getSize(), 3, "Hash table should have 3 elements");
    assertEquals(hashtable1.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable1.get(1), 1, "Hash table elements should be correct");
    assertEquals(hashtable1.get(8), 8, "Hash table elements should be correct");

    hashtable1.remove(0);
    assertEquals(hashtable1.get(1), 1, "Hash table elements should be correct after removing");
    assertEquals(hashtable1.get(8), 8, "Hash table elements should be correct after removing");

    var hashtable2 = new HashTable<Integer, Integer>();

    hashtable2.set(0, 0);
    hashtable2.set(1, 1);
    hashtable2.set(8, 8);

    hashtable2.remove(0);
    assertEquals(hashtable2.get(1), 1, "Hash table elements should be correct after removing");
    assertEquals(hashtable2.get(8), 8, "Hash table elements should be correct after removing");

    var hashtable3 = new HashTable<Integer, Integer>();

    hashtable3.set(0, 0);
    hashtable3.set(1, 1);
    hashtable3.set(7, 7);
    hashtable3.set(15, 15);

    assertEquals(hashtable1.getSize(), 4, "Hash table should have 4 elements");
    assertEquals(hashtable3.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable3.get(1), 1, "Hash table elements should be correct");
    assertEquals(hashtable3.get(7), 7, "Hash table elements should be correct");
    assertEquals(hashtable3.get(15), 15, "Hash table elements should be correct");

    hashtable3.remove(7);

    assertEquals(hashtable1.getSize(), 3, "Hash table should have 3 elements");
    assertEquals(hashtable3.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable3.get(1), 1, "Hash table elements should be correct");
    assertEquals(hashtable3.get(15), 15, "Hash table elements should be correct");
  }
}
