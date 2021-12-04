package prep.substringsearch;

public class NaiveSearch implements SearchAlgorithm {
  @Override
  public int find(String string, String text) {
    if (string.length() == 0) {
      return 0;
    }

    if (string.length() > text.length()) {
      return -1;
    }

    for (int i = 0; i < text.length() - string.length() + 1; i += 1) {
      boolean error = false;
      for (int j = 0; j < string.length(); j += 1) {
        if (text.charAt(i + j) != string.charAt(j)) {
          error = true;
          break;
        }
      }

      if (!error) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public String getName() {
    return "Naive Search";
  }
}
