package prep.karprabin;

import prep.Test;

public class KarpRabinTest extends Test {
  public void runTests() {
    runTest(this::test1, "KarpRabin search check");
    for (int i = 1; i <= 10; i += 1) {
      runTest(this::test2, "KarpRabin random test " + i);
    }
  }

  private void test1() {
    var karprabin = new KarpRabin(1023, 1000000123);

    assertEquals(karprabin.find("entry", "entry"), 0, "Search error");
    assertEquals(karprabin.find("entry", "entry123"), 0, "Search error");
    assertEquals(karprabin.find("entry", "123entry"), 3, "Search error");
    assertEquals(karprabin.find("entry", "123entry123"), 3, "Search error");

    assertEquals(karprabin.find("entry", "nowhere"), -1, "Search error");
    assertEquals(karprabin.find("entry", "etnry"), -1, "Search error");
    assertEquals(karprabin.find("entry", "1"), -1, "Search error");
    
    assertEquals(karprabin.find("", ""), 0, "Search error on empty strings");
  }

  private void test2() {
    var karprabin = new KarpRabin(1023, 1000000123);
    
    int textLength = (int)(Math.random() * 2000);
    int stringLength = (int)(Math.random() * textLength);

    char[] textChars = new char[textLength];
    for (int i = 0; i < textLength; i += 1) {
      textChars[i] = (char)('a' + (int)(Math.random() * 26));
    }
    
    char[] stringChars = new char[stringLength];
    int stringStart = (int)(Math.random() * (textLength - stringLength + 1));
    for (int i = 0; i < stringLength; i += 1) {
      stringChars[i] = textChars[stringStart + i];
    }

    String text = new String(textChars);
    String string = new String(stringChars);

    assertEquals(
      karprabin.naiveSearch(string, text),
      karprabin.find(string, text),
      "Karp-Rabin and naive search got wrong results.\n"
      + "Text '" + text + "'; string '" + string + "'"
    );
  }
}
