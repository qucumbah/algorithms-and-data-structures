package prep.karprabin;

public class KarpRabin {
  private int base;
  private int mod;

  public KarpRabin(int base, int mod) {
    this.base = base;
    this.mod = mod;
  }

  public int find(String string, String text) {
    if (string.length() == 0) {
      return 0;
    }

    if (string.length() > text.length()) {
      return -1;
    }

    long stringHash = 0;
    long power = 1;
    for (int i = 0; i < string.length(); i += 1) {
      stringHash = (stringHash * base + string.charAt(i)) % mod;
      power = power * base % mod;
    }

    long rollingHash = 0;
    for (int i = 0; i < text.length(); i += 1) {
      if (i < string.length()) {
        rollingHash = (rollingHash * base + text.charAt(i)) % mod;
      } else {
        rollingHash = (rollingHash * base - text.charAt(i - string.length()) * power % mod + mod + text.charAt(i)) % mod;
      }

      if (i >= string.length() - 1) {
        int start = i - string.length() + 1;
        if (stringHash == rollingHash && compare(string, text, start)) {
          return start;
        }
      }
    }

    return -1;
  }

  public int naiveSearch(String string, String text) {
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

  public boolean compare(String string, String text, int from) {
    for (int i = 0; i < string.length(); i += 1) {
      if (string.charAt(i) != text.charAt(from + i)) {
        return false;
      }
    }

    return true;
  }
}
