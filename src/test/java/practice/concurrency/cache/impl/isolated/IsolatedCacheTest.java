package practice.concurrency.cache.impl.isolated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicReference;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import tr.unvercanunlu.practice.concurrency.cache.ICache;
import tr.unvercanunlu.practice.concurrency.cache.impl.isolated.IsolatedCache;

class IsolatedCacheTest {

  @SneakyThrows
  @Test
  void testThreadIsolation() {
    ICache<String, String> cache = new IsolatedCache<>();

    AtomicReference<String> value1 = new AtomicReference<>();
    AtomicReference<String> value2 = new AtomicReference<>();

    Runnable task1 = () -> {
      cache.set("name", "task1");
      value1.set(cache.get("name"));

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Runnable task2 = () -> {
      cache.set("name", "task2");
      value2.set(cache.get("name"));

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Thread thread1 = new Thread(task1);
    Thread thread2 = new Thread(task2);

    thread1.start();
    thread2.start();

    Thread.sleep(1000);

    assertNotEquals(value1.get(), value2.get());
    assertEquals("task1", value1.get());
    assertEquals("task2", value2.get());
  }

  @SneakyThrows
  @Test
  void testBasicOperations() {
    ICache<String, String> cache = new IsolatedCache<>();

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

  @SneakyThrows
  @Test
  void testCompleteRemovesThreadLocal() {
    IsolatedCache<String, String> cache = new IsolatedCache<>();

    cache.set("key1", "value1");
    cache.set("key2", "value2");

    cache.complete();
    assertFalse(cache.contains("key1"));
    assertFalse(cache.contains("key2"));
  }

}
