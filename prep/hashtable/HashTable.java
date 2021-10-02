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
    // We keep all keys sorted
    // If we insert a new element, we:
    // 1. If the element's place is empty, then just put it there
    // 2. Otherwise look for two elements between which it should fit

    int hashValue = hash(key);
    int positionToInsert = hashValue;

    // Simple check
    if (inner[positionToInsert] == null) {
      inner[positionToInsert] = new Item(key, value);
      size += 1;
      return;
    }

    // Our place is taken; move along the array to find where the element should be inserted to
    while (true) {
      // This means we've inserted a key which is larger than all keys already stored in this line
      // This condition is bound to happen eventually even if the other one doesn't
      if (inner[positionToInsert] == null) {
        inner[positionToInsert] = new Item(key, value);
        size += 1;
        return;
      }

      // Check if we're in the right position
      int curIndex = positionToInsert;
      int prevIndex = getInBoundsIndex(curIndex - 1);
      int curHash = hash(inner[curIndex].key);
      int prevHash = hash(inner[prevIndex].key);

      if (curHash < prevHash) {
        curHash += getCapacity();
      }

      if (hashValue < curHash) {
        hashValue += getCapacity();
      }

      if (hashValue > prevHash && hashValue <= curHash) {
        // We've found the right position; now we just have to shift all the following elements one place to the right
        shiftToTheRight(curIndex);

        // And insert our element
        inner[curIndex] = new Item(key, value);
        return;
      }

      positionToInsert = getInBoundsIndex(positionToInsert + 1);
    }
  }

  private void shiftToTheRight(int index) {
    Item prev = inner[index];
    inner[index] = null;
    int curIndex = getInBoundsIndex(index + 1);
    while (inner[curIndex] != null) {
      Item cur = inner[curIndex];
      inner[curIndex] = prev;
      prev = cur;
      curIndex = getInBoundsIndex(curIndex + 1);
    }
    inner[curIndex] = prev;
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
    int hashValue = hash(key);
    int position = hashValue;
    while (true) {
      if (inner[position] == null) {
        return null;
      }

      if (inner[position].key.compareTo(key) == 0) {
        V result = inner[position].value;
        inner[position] = null;
        shiftToTheLeft(getInBoundsIndex(position + 1));

        size -= 1;
        resizeIfNeeded(size);
        return result;
      }

      position = getInBoundsIndex(position + 1);

      if (position == hashValue) {
        return null;
      }
    }
  }

  private void shiftToTheLeft(int index) {
    int curIndex = index;
    while (true) {
      if (inner[curIndex] == null) {
        return;
      }

      Item curItem = inner[curIndex];
      int curHash = hash(curItem.key);
      if (curHash == curIndex) {
        return;
      }

      int prevIndex = getInBoundsIndex(curIndex - 1);
      inner[prevIndex] = curItem;
      inner[curIndex] = null;

      curIndex += 1;
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
  private void resizeIfNeeded(int newSize) {
    boolean shouldIncreaseSize = newSize > inner.length;
    // Don't decrease capacity to less than 8
    boolean shouldDecreaseSize = newSize < inner.length / 4 && inner.length != 8;

    if (!shouldIncreaseSize && !shouldDecreaseSize) {
      return;
    }

    if (isResizing) {
      return;
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
  }
}
