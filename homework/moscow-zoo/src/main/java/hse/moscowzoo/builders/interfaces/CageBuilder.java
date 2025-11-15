package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя клеток.
 * Определяет методы для создания объектов клеток с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface CageBuilder extends ThingBuilder<CageBuilder> {
    /**
     * Устанавливает площадь клетки.
     *
     * @param area площадь клетки
     * @return текущий строитель
     */
    CageBuilder withArea(double area);

    /**
     * Устанавливает материал клетки.
     *
     * @param material материал клетки
     * @return текущий строитель
     */
    CageBuilder withMaterial(String material);
}