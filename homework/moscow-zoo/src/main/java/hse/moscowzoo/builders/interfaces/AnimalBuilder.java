package hse.moscowzoo.builders.interfaces;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;

/**
 * Интерфейс строителя животных.
 * Определяет методы для пошагового создания объектов животных.
 *
 * @param <T> конкретный тип строителя животных
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface AnimalBuilder<T extends AnimalBuilder<T>> {
    /**
     * Устанавливает инвентарный номер животного.
     *
     * @param number инвентарный номер
     * @return текущий строитель
     */
    T withInventoryNumber(int number);

    /**
     * Устанавливает имя животного.
     *
     * @param name имя животного
     * @return текущий строитель
     */
    T withName(String name);

    /**
     * Устанавливает уровень доброты животного.
     *
     * @param kindnessLevel уровень доброты
     * @return текущий строитель
     */
    T withKindness(KindnessLevel kindnessLevel);

    /**
     * Создает объект животного с установленными параметрами.
     *
     * @return созданный объект животного
     */
    Animal build();
}