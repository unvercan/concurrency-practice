package practice.concurrency.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import tr.unvercanunlu.practice.concurrency.cache.ICache;
import tr.unvercanunlu.practice.concurrency.cache.impl.Cache;

public class CacheTest {

  @SneakyThrows
  @Test
  void testThreadNotIsolated() {
    ICache<String, String> cache = new Cache<>();

    Runnable task1 = () -> {
      cache.set("key", "task1");
    };

    Runnable task2 = () -> {
      cache.set("key", "task2");
    };

    Thread thread1 = new Thread(task1);
    Thread thread2 = new Thread(task2);

    thread1.start();
    Thread.sleep(1000);
    thread1.join(1000);

    Thread.sleep(1000);

    thread2.start();
    Thread.sleep(1000);
    thread2.join(1000);

    Thread.sleep(1000);

    assertEquals("task2", cache.get("key"));
  }

  @SneakyThrows
  @Test
  void testBasicOperations() {
    ICache<String, String> cache = new Cache<>();

    // action 1
    cache.set("key1", "value0");
    cache.set("key1", "value1");
    cache.set("key2", "value2");
    assertTrue(cache.contains("key1"));
    assertTrue(cache.contains("key2"));
    assertNotEquals("value0", cache.get("key1"));
    assertEquals("value1", cache.get("key1"));
    assertEquals("value2", cache.get("key2"));

    // action 2
    cache.remove("key2");
    assertTrue(cache.contains("key1"));
    assertEquals("value1", cache.get("key1"));
    assertFalse(cache.contains("key2"));

    // action 3
    cache.clear();
    assertFalse(cache.contains("key1"));
    assertFalse(cache.contains("key2"));
  }

}
