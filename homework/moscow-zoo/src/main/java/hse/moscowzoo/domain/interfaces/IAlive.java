package hse.moscowzoo.domain.interfaces;

/**
 * Интерфейс для живых существ в зоопарке.
 * Определяет методы для работы с потреблением пищи.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface IAlive {
    /**
     * Возвращает суточное потребление пищи существом.
     *
     * @return суточное потребление пищи в килограммах
     */
    int getDailyFoodConsumption();

    /**
     * Устанавливает суточное потребление пищи существом.
     *
     * @param food новое значение суточного потребления пищи в килограммах
     */
    void setDailyFoodConsumption(int food);
}