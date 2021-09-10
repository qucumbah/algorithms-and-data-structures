package prep;

public abstract class Test {
  private boolean assertionsFailed = false;

  protected void runTest(Runnable test, String testName) {
    test.run();

    System.out.println((assertionsFailed ? "X" : "V") + " " + testName);

    assertionsFailed = false;
  }

  protected void assertion(boolean condition, String message) {
    if (!condition) {
      assertionsFailed = true;
      System.err.println("Assertion failed: " + message);
    }
  }

  protected <T extends Comparable<T>> void assertEquals(T a, T b, String message) {
    if (a.compareTo(b) != 0) {
      System.err.println("Assertion failed: " + message);
      System.err.println("Expected: " + b + "; got: " + a);
    }
  }
}
