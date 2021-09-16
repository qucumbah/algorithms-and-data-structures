package prep.karprabin;

import prep.Test;

public class KarpRabinTest extends Test {
  public void runTests() {
    runTest(this::test1, "KarpRabin value check");
  }

  private void test1() {
    var karprabin = new KarpRabin();
    assertion(karprabin.value() == 12, "KarpRabin value should be 12");
  }
}
