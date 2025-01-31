# Isolated Cache

A thread-local caching implementation in Java to ensure data isolation between threads.

## Features

- **IsolatedCache**: Each thread has its own isolated cache using `ThreadLocal`.
- **Cache**: A shared cache for all threads.
- **Thread Safety**: Ensures data consistency and isolation where needed.
- **Unit Tests**: Validates thread isolation and shared cache behavior.

## Usage

```java
ICache<String, String> cache = new IsolatedCache<>();
cache.set("key", "value");
System.out.println(cache.get("key")); // Outputs "value" only for the current thread
```

## Running Tests

Use JUnit to run the test cases:

```sh
mvn test
```

## License

MIT

