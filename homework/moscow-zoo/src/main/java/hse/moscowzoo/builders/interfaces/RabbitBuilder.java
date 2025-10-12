package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя кроликов.
 * Определяет методы для создания объектов кроликов с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface RabbitBuilder extends AnimalBuilder<RabbitBuilder> {
    /**
     * Устанавливает длину ушей кролика.
     *
     * @param earLength длина ушей
     * @return текущий строитель
     */
    RabbitBuilder withEarLength(double earLength);

    /**
     * Устанавливает тип меха кролика.
     *
     * @param furType тип меха
     * @return текущий строитель
     */
    RabbitBuilder withFurType(String furType);
}