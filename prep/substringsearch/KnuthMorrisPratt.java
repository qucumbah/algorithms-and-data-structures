package prep.substringsearch;

public class KnuthMorrisPratt implements SearchAlgorithm {
  @Override
  public int find(String needle, String haystack) {
    int[] prefixes = getPrefixArray(needle);

    int i = 0;
    int j = 0;
    while (i < needle.length() && j < haystack.length()) {
      if (needle.charAt(i) == haystack.charAt(j)) {
        i += 1;
        j += 1;
      } else {
        if (i == 0) {
          j += 1;
        } else {
          i = prefixes[i - 1];
        }
      }
    }

    if (i == needle.length()) {
      return j - i;
    }

    return -1;
  }

  private int[] getPrefixArray(String needle) {
    int[] prefixes = new int[needle.length()];

    int l = 0;
    int r = 1;
    while (r < needle.length()) {
      if (needle.charAt(l) == needle.charAt(r)) {
        prefixes[r] = l + 1;

        l += 1;
        r += 1;
      } else {
        if (l == 0) {
          r += 1;
        } else {
          l = prefixes[l - 1];
        }
      }
    }

    return prefixes;
  }

  @Override
  public String getName() {
    return "Knuth-Morris-Pratt";
  }
}
