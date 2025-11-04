package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя жирафов.
 * Определяет методы для создания объектов жирафов с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface GiraffeBuilder extends AnimalBuilder<GiraffeBuilder> {
    /**
     * Устанавливает длину шеи жирафа.
     *
     * @param neckLength длина шеи
     * @return текущий строитель
     */
    GiraffeBuilder withNeckLength(double neckLength);

    /**
     * Устанавливает узор пятен жирафа.
     *
     * @param spotPattern узор пятен
     * @return текущий строитель
     */
    GiraffeBuilder withSpotPattern(String spotPattern);
}