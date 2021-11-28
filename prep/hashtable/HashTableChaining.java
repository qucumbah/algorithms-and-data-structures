package prep.hashtable;

import java.util.List;
import java.util.ArrayList;

import prep.bst.BinarySearchTree;

public class HashTableChaining<K extends Comparable<K>, V> implements HashTable<K, V> {
  private class Item implements Comparable<Item> {
    public K key;
    public V value;

    public Item(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public int compareTo(HashTableChaining<K, V>.Item other) {
      return this.key.compareTo(other.key);
    }
  }

  private List<BinarySearchTree<Item>> inner;
  private int size = 0;
  private int capacity = 8;

  public HashTableChaining() {
    inner = new ArrayList<BinarySearchTree<Item>>();
    for (int i = 0; i < capacity; i += 1) {
      inner.add(null);
    }
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public int getCapacity() {
    return capacity;
  }

  @Override
  public void set(K key, V value) {
    // Increase capacity to ensure O(1) runtime
    resizeIfNeeded(size + 1);

    int hashValue = hash(key);

    BinarySearchTree<Item> bst = inner.get(hashValue);
    if (bst == null) {
      bst = new BinarySearchTree<Item>();
    }

    var blankItem = new Item(key, value);

    if (!bst.contains(blankItem)) {
      size += 1;
    }

    bst.insert(blankItem);

    inner.set(hashValue, bst);
  }

  @Override
  public V get(K key) {
    int hashValue = hash(key);

    BinarySearchTree<Item> bst = inner.get(hashValue);
    if (bst == null) {
      return null;
    }

    Item result = bst.get(new Item(key, null));
    if (result == null) {
      return null;
    }

    return result.value;
  }

  @Override
  public V remove(K key) {
    int hashValue = hash(key);

    BinarySearchTree<Item> bst = inner.get(hashValue);
    if (bst == null) {
      return null;
    }

    Item result = bst.get(new Item(key, null));
    if (result == null) {
      return null;
    }

    bst.delete(result);
    size -= 1;

    resizeIfNeeded(size);

    return result.value;
  }

  private int hash(K key) {
    return key.hashCode() % getCapacity();
  }

  private boolean isResizing = false;

  private boolean resizeIfNeeded(int newSize) {
    boolean shouldIncreaseSize = newSize > getCapacity();
    // Don't decrease capacity to less than 8
    boolean shouldDecreaseSize = newSize < getCapacity() / 4 && getCapacity() != 8;

    if (!shouldIncreaseSize && !shouldDecreaseSize) {
      return false;
    }

    if (isResizing) {
      return false;
    }
    isResizing = true;

    List<BinarySearchTree<Item>> oldInner = inner;

    capacity = (shouldIncreaseSize) ? getCapacity() * 2 : getCapacity() / 2;
    size = 0;

    inner = new ArrayList<BinarySearchTree<Item>>();
    for (int i = 0; i < capacity; i += 1) {
      inner.add(null);
    }

    for (BinarySearchTree<Item> bst : oldInner) {
      if (bst == null) {
        continue;
      }

      for (Item item : bst) {
        set(item.key, item.value);
      }
    }

    isResizing = false;

    return true;
  }
}
