package hse.moscowzoo.builders.interfaces;

import hse.moscowzoo.domain.entities.things.Thing;

/**
 * Интерфейс строителя вещей.
 * Определяет методы для пошагового создания объектов вещей.
 *
 * @param <T> конкретный тип строителя вещей
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface ThingBuilder<T extends ThingBuilder<T>> {
    /**
     * Устанавливает инвентарный номер вещи.
     *
     * @param number инвентарный номер
     * @return текущий строитель
     */
    T withInventoryNumber(int number);

    /**
     * Устанавливает название вещи.
     *
     * @param name название вещи
     * @return текущий строитель
     */
    T withName(String name);

    /**
     * Создает объект вещи с установленными параметрами.
     *
     * @return созданный объект вещи
     */
    Thing build();
}