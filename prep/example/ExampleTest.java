package prep.example;

import prep.Test;

public class ExampleTest extends Test {
  public void runTests() {
    runTest(this::test1, "Example value check");
  }

  private void test1() {
    var example = new Example();
    assertion(example.value() == 12, "Example value should be 12");
  }
}
