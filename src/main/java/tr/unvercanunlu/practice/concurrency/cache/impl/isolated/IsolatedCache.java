package tr.unvercanunlu.practice.concurrency.cache.impl.isolated;

import java.util.HashMap;
import java.util.Map;
import tr.unvercanunlu.practice.concurrency.cache.ICache;

// Isolated-State Thread-Safety
// ThreadLocal: each thread has own isolated data
public class IsolatedCache<K, V> implements ICache<K, V> {

  // isolated data for each thread
  private final ThreadLocal<Map<K, V>> localMap = ThreadLocal.withInitial(HashMap::new);

  @Override
  public void set(K key, V value) {
    this.localMap.get().put(key, value);
  }

  @Override
  public V get(K key) {
    return this.localMap.get().get(key);
  }

  @Override
  public void remove(K key) {
    this.localMap.get().remove(key);
  }

  @Override
  public boolean contains(K key) {
    return this.localMap.get().keySet().contains(key);
  }

  @Override
  public void clear() {
    this.localMap.get().clear();
  }

  public void complete() {
    this.localMap.remove();
  }

}
