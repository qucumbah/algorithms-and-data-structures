package prep.substringsearch;

import java.util.ArrayList;
import java.util.List;

import prep.Test;

public class SearchAlgorithmTest extends Test {
  public void runTests() {
    var algorithms = new ArrayList<SearchAlgorithm>();
    algorithms.add(new NaiveSearch());
    algorithms.add(new KarpRabin(1023, 1000000123));

    for (SearchAlgorithm algorithm : algorithms) {
      runTest(() -> this.test1(algorithm), algorithm.getName() + " search check");
    }

    for (int i = 1; i <= 10; i += 1) {
      runTest(() -> this.test2(algorithms), "Search algorithms random test " + i);
    }
  }

  private void test1(SearchAlgorithm algorithm) {
    assertEquals(algorithm.find("entry", "entry"), 0, "Search error");
    assertEquals(algorithm.find("entry", "entry123"), 0, "Search error");
    assertEquals(algorithm.find("entry", "123entry"), 3, "Search error");
    assertEquals(algorithm.find("entry", "123entry123"), 3, "Search error");

    assertEquals(algorithm.find("entry", "nowhere"), -1, "Search error");
    assertEquals(algorithm.find("entry", "etnry"), -1, "Search error");
    assertEquals(algorithm.find("entry", "1"), -1, "Search error");
    
    assertEquals(algorithm.find("", ""), 0, "Search error on empty strings");
  }

  private void test2(List<SearchAlgorithm> algorithms) {
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

    // Can't compare with stringStart since there could be more than one 'string' in the 'text'
    int firstAlgorithmResult = algorithms.get(0).find(string, text);
    for (SearchAlgorithm algorithm : algorithms) {
      assertEquals(
        algorithm.find(string, text),
        firstAlgorithmResult,
        algorithm.getName() + " algorithm gave a different result compared to " + algorithms.get(0).getName());
    }

    if (haveAssertionsFailed()) {
      System.out.println("Text: " + text);
      System.out.println("String: " + string);
    }
  }
}
