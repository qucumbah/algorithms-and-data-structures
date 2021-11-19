package prep.hashtable;

import java.util.function.Supplier;

import prep.Test;

public class HashTableTest extends Test {
  private Supplier<HashTable<Integer, Integer>> hashTableConstructor;

  public void runTests() {
    hashTableConstructor = HashTableLinearProbing::new;

    runTest(this::test1, "Capacity, size, set, get, remove");
    runTest(this::test2, "Linear probing removing 1");
    runTest(this::test3, "Linear probing removing 2");
    runTest(this::test4, "Linear probing removing 3");
    runTest(this::test5, "Linear probing removing 4");
    runTest(this::test6, "Linear probing removing 5");
    runTest(this::test7, "Linear probing removing 6");

    for (int i = 0; i < 10; i += 1) {
      runTest(this::test8, "Random test " + (i + 1));
    }

    runTest(this::test9, "Generated test 1");
    runTest(this::test10, "Generated test 2");
  }

  private void test1() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();
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

    hashtable.set(0, 0);

    assertEquals(hashtable.getCapacity(), 16, "HashTable capacity should still be 16 after replacing an element");
    assertEquals(hashtable.getSize(), 9, "HashTable size should still be 9 after replacing an element");

    for (int i = 0; i < 7; i += 1) {
      assertEquals(hashtable.remove(i), i, "Removal of " + i + "th element should happen");
    }

    assertEquals(hashtable.getCapacity(), 8, "HashTable capacity should half after removing 7/9 elements");
    assertEquals(hashtable.getSize(), 2, "HashTable size should be 2 after removing 7/9 elements");

    for (int i = 0; i < 7; i += 1) {
      assertEquals(hashtable.get(i), null, "HashTable should not have any inaccessible elements");
    }

    assertEquals(hashtable.get(7), 7, "HashTable should not have removed other elements");
    assertEquals(hashtable.get(8), 8, "HashTable should not have removed other elements");
  }

  private void test2() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    hashtable.set(0, 0);

    assertion(hashtable.get(8) == null, "Should not get inaccessible element");

    hashtable.set(8, 8);

    assertEquals(hashtable.getSize(), 2, "Hash table should have 2 elements");
    assertEquals(hashtable.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable.get(8), 8, "Hash table elements should be correct");

    hashtable.set(1, 1);

    assertEquals(hashtable.getSize(), 3, "Hash table should have 3 elements");
    assertEquals(hashtable.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable.get(1), 1, "Hash table elements should be correct");
    assertEquals(hashtable.get(8), 8, "Hash table elements should be correct");

    hashtable.remove(0);
    assertEquals(hashtable.get(1), 1, "Hash table elements should be correct after removing");
    assertEquals(hashtable.get(8), 8, "Hash table elements should be correct after removing");
  }

  private void test3() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    hashtable.set(0, 0);
    hashtable.set(1, 1);
    hashtable.set(8, 8);

    hashtable.remove(0);
    assertEquals(hashtable.get(1), 1, "Hash table elements should be correct after removing");
    assertEquals(hashtable.get(8), 8, "Hash table elements should be correct after removing");
  }

  private void test4() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    hashtable.set(0, 0);
    hashtable.set(1, 1);
    hashtable.set(7, 7);
    hashtable.set(15, 15);

    assertEquals(hashtable.getSize(), 4, "Hash table should have 4 elements");
    assertEquals(hashtable.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable.get(1), 1, "Hash table elements should be correct");
    assertEquals(hashtable.get(7), 7, "Hash table elements should be correct");
    assertEquals(hashtable.get(15), 15, "Hash table elements should be correct");

    hashtable.remove(7);

    assertEquals(hashtable.getSize(), 3, "Hash table should have 3 elements");
    assertEquals(hashtable.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable.get(1), 1, "Hash table elements should be correct");
    assertEquals(hashtable.get(15), 15, "Hash table elements should be correct");
  }

  private void test5() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    hashtable.set(6, 6);
    hashtable.set(14, 14);
    hashtable.set(22, 22);
    hashtable.set(7, 7);

    hashtable.remove(22);

    assertEquals(hashtable.getSize(), 3, "Hash table should have 3 elements");
    assertEquals(hashtable.get(6), 6, "Hash table elements should be correct");
    assertEquals(hashtable.get(14), 14, "Hash table elements should be correct");
    assertEquals(hashtable.get(7), 7, "Hash table elements should be correct");
  }

  private void test6() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    hashtable.set(6, 6);
    hashtable.set(7, 7);
    hashtable.set(8, 8);

    hashtable.remove(6);

    assertEquals(hashtable.getSize(), 2, "Hash table should have 2 elements");
    assertEquals(hashtable.get(7), 7, "Hash table elements should be correct");
    assertEquals(hashtable.get(8), 8, "Hash table elements should be correct");
  }

  private void test7() {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    hashtable.set(6, 6);
    hashtable.set(14, 14);
    hashtable.set(0, 0);
    hashtable.set(8, 8);
    hashtable.set(22, 22);
    hashtable.set(30, 30);

    hashtable.remove(6);

    assertEquals(hashtable.get(6), null, "Hash table should not contain removed element");

    assertEquals(hashtable.getSize(), 5, "Hash table should have 5 elements");
    assertEquals(hashtable.get(14), 14, "Hash table elements should be correct");
    assertEquals(hashtable.get(0), 0, "Hash table elements should be correct");
    assertEquals(hashtable.get(8), 8, "Hash table elements should be correct");
    assertEquals(hashtable.get(22), 22, "Hash table elements should be correct");
    assertEquals(hashtable.get(30), 30, "Hash table elements should be correct");
  }

  private static class Test {
    int[] additions;
    int[] removals;

    public Test(int[] additions, int[] removals) {
      this.additions = additions;
      this.removals = removals;
    }

    public static Test generate() {
      int numberOfElementsToAdd = randomInt(16);
      int[] additions = new int[numberOfElementsToAdd];
      for (int i = 0; i < numberOfElementsToAdd; i += 1) {
        int value = randomInt(32);
        additions[i] = value;
      }

      int numberOfElementsToRemove = randomInt(16);
      int[] removals = new int[numberOfElementsToRemove];
      for (int i = 0; i < numberOfElementsToRemove; i += 1) {
        int value = randomInt(32);
        removals[i] = value;
      }

      return new Test(additions, removals);
    }

    private static int randomInt(int range) {
      return (int)(Math.random() * range);
    }

    private void print() {
      for (int i = 0; i < additions.length; i += 1) {
        System.out.print(additions[i] + ", ");
      }
      System.out.println();
      for (int i = 0; i < removals.length; i += 1) {
        System.out.print(removals[i] + ", ");
      }
      System.out.println();
    }
  }

  private void test8() {
    randomTest(Test.generate());
  }

  private boolean randomTest(Test test) {
    HashTable<Integer, Integer> hashtable = hashTableConstructor.get();

    var addedElements = new java.util.HashSet<Integer>();

    for (int i = 0; i < test.additions.length; i += 1) {
      int itemToAdd = test.additions[i];
      hashtable.set(itemToAdd, itemToAdd);
      addedElements.add(itemToAdd);
    }

    assertEquals(hashtable.getSize(), addedElements.size(), "Hash table should have correct number of elements");

    for (int i = 0; i < 32; i += 1) {
      if (addedElements.contains(i)) {
        assertEquals(hashtable.get(i), i, "Should have all added elements");
      } else {
        assertEquals(hashtable.get(i), null, "Should not have inaccessible elements");
      }
    }

    var removedElements = new java.util.HashSet<Integer>();

    for (int i = 0; i < test.removals.length; i += 1) {
      int elementToRemove = test.removals[i];
      if (removedElements.contains(elementToRemove) || !addedElements.contains(elementToRemove)) {
        var a = hashtable.remove(elementToRemove);
        assertEquals(a, null, "Should not remove an element twice");
      } else {
        assertEquals(hashtable.remove(elementToRemove), elementToRemove, "Should remove correctly");
        removedElements.add(elementToRemove);
      }
    }

    assertEquals(hashtable.getSize(), addedElements.size() - removedElements.size(), "Should remove correct number of elements");

    for (int i = 0; i < 32; i += 1) {
      if (addedElements.contains(i) && !removedElements.contains(i)) {
        assertEquals(hashtable.get(i), i, "Should have all added elements and should not have any removed elements");
      } else {
        assertEquals(hashtable.get(i), null, "Should not have inaccessible elements");
      }
    }

    if (haveAssertionsFailed()) {
      System.out.println("Failed test case:");
      test.print();
    }

    return haveAssertionsFailed();
  }

  private void test9() {
    var test = new Test(
      new int[] { 8, 24, 27 },
      new int[] { 0, 29, 20 }
    );
    randomTest(test);
  }

  private void test10() {
    var test = new Test(
      new int[] { 21, 6, 6 },
      new int[] { 6 }
    );
    randomTest(test);
  }
}
