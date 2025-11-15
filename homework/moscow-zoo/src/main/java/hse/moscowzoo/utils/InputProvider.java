package hse.moscowzoo.utils;

/**
 * Интерфейс для поставщика ввода данных.
 * Определяет методы для получения различных типов данных от пользователя.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface InputProvider {
    /**
     * Считывает целое число.
     *
     * @return введенное целое число
     */
    int nextInt();

    /**
     * Считывает дробное число.
     *
     * @return введенное дробное число
     */
    double nextDouble();

    /**
     * Считывает логическое значение.
     *
     * @return введенное логическое значение
     */
    boolean nextBoolean();

    /**
     * Считывает строку.
     *
     * @return введенная строка
     */
    String nextLine();
}