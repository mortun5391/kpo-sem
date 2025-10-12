package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя лис.
 * Определяет методы для создания объектов лис с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface FoxBuilder extends AnimalBuilder<FoxBuilder> {
    /**
     * Устанавливает цвет меха лисы.
     *
     * @param furColor цвет меха
     * @return текущий строитель
     */
    FoxBuilder withFurColor(String furColor);

    /**
     * Устанавливает длину хвоста лисы.
     *
     * @param tailLength длина хвоста
     * @return текущий строитель
     */
    FoxBuilder withTailLength(double tailLength);
}