// InputProvider.java
package hse.hsebank.utils;

import org.springframework.stereotype.Component;

/**
 * Интерфейс для поставщика ввода данных.
 */
@Component
public interface InputProvider {
    int nextInt();
    double nextDouble();
    boolean nextBoolean();
    String nextLine();
    long nextLong();
}