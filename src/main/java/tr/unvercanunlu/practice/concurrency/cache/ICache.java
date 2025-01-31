package tr.unvercanunlu.practice.concurrency.cache;

public interface ICache<K, V> {

  void set(K key, V value);

  void remove(K key);

  void clear();

  V get(K key);

  boolean contains(K key);

}
