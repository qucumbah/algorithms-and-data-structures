package prep.hashtable;

public interface HashTable<K extends Comparable<K>, V> {
  public int getSize();

  public int getCapacity();

  public void set(K key, V value);

  public V get(K key);

  public V remove(K key);
}
