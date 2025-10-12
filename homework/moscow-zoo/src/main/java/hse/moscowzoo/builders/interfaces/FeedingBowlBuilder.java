package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя мисок для кормления.
 * Определяет методы для создания объектов мисок для кормления с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface FeedingBowlBuilder extends ThingBuilder<FeedingBowlBuilder> {
    /**
     * Устанавливает вместимость миски для кормления.
     *
     * @param capacity вместимость миски
     * @return текущий строитель
     */
    FeedingBowlBuilder withCapacity(double capacity);

    /**
     * Устанавливает автоматический режим миски для кормления.
     *
     * @param isAutomatic флаг автоматического режима
     * @return текущий строитель
     */
    FeedingBowlBuilder withAutomatic(boolean isAutomatic);
}