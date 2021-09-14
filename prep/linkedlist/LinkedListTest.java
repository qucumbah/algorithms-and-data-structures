package prep.linkedlist;

import prep.Test;

public class LinkedListTest extends Test {
  public void runTests() {
    runTest(this::sizePushPop, "Size, push, pop, front, back, isEmpty");
    runTest(this::insert, "Insert");
    runTest(this::getItemFromEnd, "GetItemFromEnd");
    runTest(this::reverse, "Reverse");
    runTest(this::remove, "Remove");
  }

  private void sizePushPop() {
    var list = new LinkedList<Integer>();
    assertion(list.isEmpty(), "List should be empty initially");
    list.pushFront(1);

    try {
      list.getItem(3);
      assertion(false, "Should fail on unaccessible element (5)");
    } catch (Throwable e) {

    }

    assertEquals(list.size(), 1, "List size should change after adding one element");
    list.pushBack(2);
    assertEquals(list.size(), 2, "List size should change after adding one element");
    list.pushFront(0);
    assertEquals(list.size(), 3, "List size should change after adding one element to the front");

    for (int i = 0; i < 3; i += 1) {
      assertEquals(list.getItem(i), i, "List items should match");
    }

    assertEquals(list.back(), 2, "list.back() method should return the correct value");
    assertEquals(list.front(), 0, "list.front() method should return the correct value");

    assertEquals(list.popBack(), 2, "Popping from the back of the list should return correct value");
    assertEquals(list.size(), 2, "List size should change after removing one element");
    assertEquals(list.popFront(), 0, "Popping from the front of the list should return correct value");
    assertEquals(list.size(), 1, "List size should change after removing one element");
    assertEquals(list.popFront(), 1, "Popping from the front of the list should return correct value");
    assertEquals(list.size(), 0, "List size should change after removing one element");

    for (int i = 0; i < 5; i += 1) {
      list.pushBack(i);
    }
    for (int i = 0; i < 5; i += 1) {
      assertEquals(list.getItem(i), i, "List items should be correct after pushing multiple items");
    }
  }

  private void insert() {
    var list = new LinkedList<Integer>();
    for (int i = 0; i < 5; i += 1) {
      list.pushBack(i);
    }
    list.insert(3, -3);
    assertEquals(list.getItem(2), 2, "Should not have shifted elements before the inserted one");
    assertEquals(list.getItem(3), -3, "Should have inserted the right element");
    assertEquals(list.getItem(4), 3, "Should have shifted elements after the inserted one");

    list.insert(6, 10);
    assertEquals(list.size(), 7, "After adding 2 elements list size should be 7");
    assertEquals(list.getItem(6), 10, "Should have inserted item at the end");

    list.insert(0, 15);
    assertEquals(list.size(), 8, "After adding 3 elements list size should be 8");
    assertEquals(list.getItem(7), 10, "Should have shifted items correctly");
    assertEquals(list.getItem(0), 15, "Should have inserted item at the start");
  }

  private void getItemFromEnd() {
    var l1 = new LinkedList<Integer>();
    for (int i = 0; i < 5; i += 1) {
      l1.pushBack(i);
    }

    for (int i = 0; i < 5; i += 1) {
      assertEquals(l1.getItemFromEnd(i), 5 - i - 1, "Should get the right item from end");
    }

    try {
      l1.getItemFromEnd(5);
      assertion(false, "Should fail on unaccessible element (5)");
    } catch (Throwable e) {

    }

    try {
      l1.getItemFromEnd(6);
      assertion(false, "Should fail on unaccessible element (6)");
    } catch (Throwable e) {

    }
  }

  private void reverse() {
    var l1 = new LinkedList<Integer>();
    l1.reverse();
    assertEquals(l1.size(), 0, "List with zero size should not change after reversing");

    var l2 = new LinkedList<Integer>();
    l2.pushFront(1);
    l2.reverse();
    assertEquals(l2.size(), 1, "List with size 1 should not change after reversing");
    assertEquals(l2.getItem(0), 1, "Items of list with size 1 should not change after reversing");

    var l3 = new LinkedList<Integer>();
    for (int i = 0; i < 15; i += 1) {
      l3.pushBack(i);
    }
    l3.reverse();
    for (int i = 0; i < 15; i += 1) {
      assertEquals(l3.getItem(i), 15 - i - 1, "Reversed list items should be correct");
    }
    l3.reverse();
    for (int i = 0; i < 15; i += 1) {
      assertEquals(l3.getItem(i), i, "Reversed twice list items should be correct");
    }
  }

  private void remove() {
    var l1 = new LinkedList<Integer>();
    assertEquals(l1.remove(15), -1, "Should not remove from an empty list");
    l1.pushBack(6);
    assertEquals(l1.remove(15), -1, "Should not remove a non-existing element");

    var l2 = new LinkedList<Integer>();
    for (int i = 0; i < 15; i += 1) {
      l2.pushBack(i);
    }

    assertEquals(l2.remove(3), 3, "Should remove at the right index");
    assertEquals(l2.size(), 14, "Size should decrease by 1 after removing an element");
    assertEquals(l2.getItem(3), 4, "Elements after the removed one should have shifted");
    assertEquals(l2.remove(3), -1, "Should not remove an element that has already been removed");

    l2.remove(14);
    assertEquals(l2.size(), 13, "Size should decrease by 1 after removing the last element");
    assertEquals(l2.getItem(12), 13, "The last element should have changed");
  }
}
