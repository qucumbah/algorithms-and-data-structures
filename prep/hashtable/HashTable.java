package prep.hashtable;

/**
 * Hash set with linear probing
 *
 * Other ideas:
 * 1. Chaining
 */
public class HashTable<K extends Comparable<K>, V> {
  private class Item {
    public K key;
    public V value;

    public Item(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private Item[] inner;
  private int size = 0;

  @SuppressWarnings("unchecked")
  public HashTable() {
    inner = new HashTable.Item[8];
  }

  public int getSize() {
    return size;
  }

  public int getCapacity() {
    return inner.length;
  }

  public void set(K key, V value) {
    // Increase capacity just in case the internall array is full
    // This will be for nothing if we replace existing key, but if we don't do that we'll have to
    // do an entire useless O(N) pass before we know for sure that we have to increase table size.
    resizeIfNeeded(size + 1);

    // High-level overview of the algorithm:
    // Insert the element if it's place is empty
    // Otherwise go forward to find the first empty cell and put it there
    // Since we've called `resizeIfNeeded(size + 1)` there is guaranteed to exist at least one empty cell

    int hashValue = hash(key);
    int positionToInsert = hashValue;

    // Find the first empty cell or cell with equal value; put our item there
    while (true) {
      // Adding an item; we are bound to hit this condition eventually since we've increased size
      if (inner[positionToInsert] == null) {
        inner[positionToInsert] = new Item(key, value);
        size += 1;
        return;
      }

      // Replacing an item; size does not change
      if (key.compareTo(inner[positionToInsert].key) == 0) {
        inner[positionToInsert] = new Item(key, value);
        return;
      }

      positionToInsert = getInBoundsIndex(positionToInsert + 1);
    }
  }

  public V get(K key) {
    int hashValue = hash(key);
    int position = hashValue;
    while (true) {
      if (inner[position] == null) {
        return null;
      }

      if (inner[position].key.compareTo(key) == 0) {
        return inner[position].value;
      }

      position = getInBoundsIndex(position + 1);

      if (position == hashValue) {
        return null;
      }
    }
  }

  public V remove(K key) {
    // Overview:
    // Go to the element's position and start going forward until we find the right element
    // If we find an empty cell until the element, then we don't have this key in the table
    // Otherwise we replace this cell with null. After that, we go forward until we find either:
    // 1. Empty cell - stop
    // 2. Item for which (item's hash value <= removed cell position)
    // In this case we move the item to the removed position and repeat the cycle
    V result = null;
    int hashValue = hash(key);
    int position = hashValue;
    while (true) {
      if (inner[position] == null) {
        return null;
      }

      if (inner[position].key.compareTo(key) == 0) {
        result = inner[position].value;
        inner[position] = null;
        break;
      }

      position = getInBoundsIndex(position + 1);

      // If we've gone the full circle, the item does not exist
      if (position == hashValue) {
        return null;
      }
    }

    size -= 1;
    boolean resized = resizeIfNeeded(size);

    if (resized) {
      return result;
    }

    int start = position;
    int cur = getInBoundsIndex(start + 1);
    while (true) {
      if (inner[cur] == null) {
        return result;
      }

      int curHash = hash(inner[cur].key);

      boolean overflow1 = cur < start;
      boolean overflow2 = cur < curHash;
      boolean smaller = curHash <= start;

      if (overflow1 ? (overflow2 && smaller) : (overflow2 || smaller)) {
        inner[start] = inner[cur];
        inner[cur] = null;
        start = cur;
        cur = getInBoundsIndex(start + 1);
      } else {
        cur = getInBoundsIndex(cur + 1);
      }
    }
  }

  private int hash(K key) {
    return key.hashCode() % inner.length;
  }

  private int getInBoundsIndex(int index) {
    return (index + inner.length) % inner.length;
  }

  private boolean isResizing = false;

  @SuppressWarnings("unchecked")
  private boolean resizeIfNeeded(int newSize) {
    boolean shouldIncreaseSize = newSize > inner.length;
    // Don't decrease capacity to less than 8
    boolean shouldDecreaseSize = newSize < inner.length / 4 && inner.length != 8;

    if (!shouldIncreaseSize && !shouldDecreaseSize) {
      return false;
    }

    if (isResizing) {
      return false;
    }
    isResizing = true;

    Item[] oldInner = inner;

    int newCapacity = (shouldIncreaseSize) ? inner.length * 2 : inner.length / 2;
    inner = (Item[])new HashTable.Item[newCapacity];
    size = 0;

    for (Item item : oldInner) {
      if (item == null) {
        continue;
      }

      set(item.key, item.value);
    }

    isResizing = false;

    return true;
  }
}
