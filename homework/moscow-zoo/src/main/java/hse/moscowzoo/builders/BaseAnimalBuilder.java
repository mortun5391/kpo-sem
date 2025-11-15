package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.AnimalBuilder;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Базовый абстрактный класс для строителей животных.
 * Предоставляет общую функциональность для всех строителей животных,
 * включая управление инвентарными номерами и базовую валидацию.
 *
 * @param <T> конкретный тип строителя животных
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public abstract class BaseAnimalBuilder<T extends AnimalBuilder<T>> implements AnimalBuilder<T> {
    /**
     * Счетчик для автоматической генерации инвентарных номеров животных
     */
    protected static final AtomicInteger COUNTER = new AtomicInteger(1000);

    protected Integer inventoryNumber;
    protected String name;
    protected KindnessLevel kindnessLevel;

    /**
     * Устанавливает инвентарный номер для животного.
     *
     * @param number инвентарный номер
     * @return текущий строитель
     */
    @Override
    @SuppressWarnings("unchecked")
    public T withInventoryNumber(int number) {
        this.inventoryNumber = number;
        return (T) this;
    }

    /**
     * Устанавливает имя для животного.
     *
     * @param name имя животного
     * @return текущий строитель
     */
    @Override
    @SuppressWarnings("unchecked")
    public T withName(String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * Устанавливает уровень доброты для животного.
     *
     * @param kindnessLevel уровень доброты животного
     * @return текущий строитель
     */
    @Override
    @SuppressWarnings("unchecked")
    public T withKindness(KindnessLevel kindnessLevel) {
        this.kindnessLevel = kindnessLevel;
        return (T) this;
    }

    /**
     * Проверяет базовые поля животного.
     * Генерирует инвентарный номер, если он не установлен.
     *
     * @throws IllegalStateException если имя не установлено
     */
    protected void validateBase() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Animal name is required");
        }
        if (inventoryNumber == null) {
            this.inventoryNumber = COUNTER.getAndIncrement();
        }
    }

    /**
     * Сбрасывает счетчик инвентарных номеров животных к начальному значению.
     */
    public static void resetCounter() {
        COUNTER.set(1000);
    }
}