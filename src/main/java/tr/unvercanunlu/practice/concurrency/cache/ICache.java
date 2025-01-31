package tr.unvercanunlu.practice.concurrency.cache;

public interface ICache<K, V> {

  // write operations
  void set(K key, V value);

  void remove(K key);

  void clear();

  // read operations
  V get(K key);

  boolean contains(K key);

}
