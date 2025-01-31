package practice.concurrency.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import tr.unvercanunlu.practice.concurrency.cache.ICache;
import tr.unvercanunlu.practice.concurrency.cache.impl.Cache;

public class CacheTest {

  @SneakyThrows
  @Test
  void testThreadNotIsolated() {
    CountDownLatch latch = new CountDownLatch(1);

    ICache<String, String> cache = new Cache<>();

    Runnable task1 = () -> {
      cache.set("key", "task1");
      latch.countDown(); // signal that task 1 done
    };

    Runnable task2 = () -> {
      try {
        latch.await(); // wait for task 1 done
        cache.set("key", "task2");
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    };

    Thread thread1 = new Thread(task1);
    Thread thread2 = new Thread(task2);

    thread1.start();
    thread2.start();

    thread1.join();
    thread2.join();

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
