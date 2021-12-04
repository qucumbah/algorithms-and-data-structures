package prep.substringsearch;

public class KarpRabin implements SearchAlgorithm {
  private int base;
  private int mod;

  public KarpRabin(int base, int mod) {
    this.base = base;
    this.mod = mod;
  }

  @Override
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
        if (stringHash == rollingHash && SearchAlgorithm.compare(string, text, start)) {
          return start;
        }
      }
    }

    return -1;
  }

  @Override
  public String getName() {
    return "Karp-Rabin";
  }
}
