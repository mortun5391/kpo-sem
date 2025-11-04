package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.FeedingBowlBuilder;
import hse.moscowzoo.domain.entities.things.FeedingBowl;

/**
 * Реализация строителя мисок для кормления.
 * Позволяет создавать объекты мисок для кормления с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class FeedingBowlBuilderImpl extends BaseThingBuilder<FeedingBowlBuilder> implements FeedingBowlBuilder {
    private Double capacity;
    private Boolean isAutomatic;

    /**
     * Устанавливает вместимость миски.
     *
     * @param capacity вместимость миски
     * @return текущий строитель
     */
    @Override
    public FeedingBowlBuilder withCapacity(double capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * Устанавливает автоматический режим миски.
     *
     * @param isAutomatic флаг автоматического режима
     * @return текущий строитель
     */
    @Override
    public FeedingBowlBuilder withAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
        return this;
    }

    /**
     * Создает объект миски для кормления с установленными параметрами.
     *
     * @return созданный объект миски для кормления
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public FeedingBowl build() {
        validateBase();
        if (capacity == null) {
            throw new IllegalStateException("FeedingBowl capacity is required");
        }
        if (isAutomatic == null) {
            throw new IllegalStateException("FeedingBowl automatic flag is required");
        }
        return new FeedingBowl(inventoryNumber, name, capacity, isAutomatic);
    }
}