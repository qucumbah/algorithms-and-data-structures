package prep.substringsearch;

public interface SearchAlgorithm {
  public int find(String needle, String haystack);

  public String getName();

  public static boolean compare(String string, String text, int from) {
    for (int i = 0; i < string.length(); i += 1) {
      if (string.charAt(i) != text.charAt(from + i)) {
        return false;
      }
    }

    return true;
  }
}
