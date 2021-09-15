package prep.dequeue;

import prep.Test;

public class DequeueTest extends Test {
  public void runTests() {
    runTest(this::test, "Dequeue tests");
  }

  private void test() {
    var dequeue = new Dequeue<Integer>(3);
    assertion(dequeue.isEmpty(), "Dequeue should be empty initially");
    assertion(!dequeue.isFull(), "Dequeue should not be full initially");

    dequeue.pushEnd(1);
    dequeue.pushEnd(2);

    assertion(!dequeue.isEmpty(), "Dequeue should not be empty after adding two elements");
    assertion(!dequeue.isFull(), "Dequeue should not be full after adding two elements");

    dequeue.pushStart(0);

    assertion(!dequeue.isEmpty(), "Dequeue should not be empty after adding three elements");
    assertion(dequeue.isFull(), "Dequeue should be full after adding three elements");

    try {
      dequeue.pushEnd(4);
      assertion(false, "Should not be able to push an element to a full dequeue");
    } catch (Error e) {

    }

    try {
      dequeue.pushStart(4);
      assertion(false, "Should not be able to push an element to a full dequeue");
    } catch (Error e) {
      
    }

    for (int i = 0; i < 3; i += 1) {
      assertEquals(dequeue.peekStart(), i, "Dequeue should have correct element order");
      assertEquals(dequeue.popStart(), i, "Dequeue should have correct element order");
    }

    assertion(dequeue.isEmpty(), "Dequeue should be empty after popping all elements");
    assertion(!dequeue.isFull(), "Dequeue should be not be full after popping all elements");

    try {
      dequeue.popEnd();
      assertion(false, "Should not be able to pop an element from a full dequeue");
    } catch (Error e) {
      
    }

    try {
      dequeue.peekStart();
      assertion(false, "Should not be able to peek an element of a full dequeue");
    } catch (Error e) {
      
    }
  }
}
