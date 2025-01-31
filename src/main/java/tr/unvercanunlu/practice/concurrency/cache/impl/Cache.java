package tr.unvercanunlu.practice.concurrency.cache.impl;

import java.util.HashMap;
import java.util.Map;
import tr.unvercanunlu.practice.concurrency.cache.ICache;

// Not Thread-Safe
public class Cache<K, V> implements ICache<K, V> {

  private final Map<K, V> map = new HashMap<>();

  @Override
  public void set(K key, V value) {
    this.map.put(key, value);
  }

  @Override
  public V get(K key) {
    return this.map.get(key);
  }

  @Override
  public void remove(K key) {
    this.map.remove(key);
  }

  @Override
  public boolean contains(K key) {
    return this.map.keySet().contains(key);
  }

  @Override
  public void clear() {
    this.map.clear();
  }

}
