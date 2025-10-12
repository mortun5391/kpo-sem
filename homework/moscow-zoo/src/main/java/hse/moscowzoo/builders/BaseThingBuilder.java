package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.ThingBuilder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Базовый абстрактный класс для строителей вещей.
 * Предоставляет общую функциональность для всех строителей вещей.
 *
 * @param <T> конкретный тип строителя
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public abstract class BaseThingBuilder<T extends ThingBuilder<T>> implements ThingBuilder<T> {
    /**
     * Счетчик для автоматической генерации инвентарных номеров
     */
    protected static final AtomicInteger COUNTER = new AtomicInteger(2000);

    protected Integer inventoryNumber;
    protected String name;

    /**
     * Устанавливает инвентарный номер для вещи.
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
     * Устанавливает название для вещи.
     *
     * @param name название вещи
     * @return текущий строитель
     */
    @Override
    @SuppressWarnings("unchecked")
    public T withName(String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * Проверяет базовые поля вещи.
     * Генерирует инвентарный номер, если он не установлен.
     *
     * @throws IllegalStateException если название не установлено
     */
    protected void validateBase() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalStateException("Thing name is required");
        }
        if (inventoryNumber == null) {
            this.inventoryNumber = COUNTER.getAndIncrement();
        }
    }

    /**
     * Сбрасывает счетчик инвентарных номеров.
     */
    public static void resetCounter() {
        COUNTER.set(2000);
    }
}