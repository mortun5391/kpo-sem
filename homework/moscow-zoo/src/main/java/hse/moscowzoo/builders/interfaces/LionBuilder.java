package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя львов.
 * Определяет методы для создания объектов львов с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface LionBuilder extends AnimalBuilder<LionBuilder> {
    /**
     * Устанавливает наличие гривы у льва.
     *
     * @param hasMane флаг наличия гривы
     * @return текущий строитель
     */
    LionBuilder withHasMane(boolean hasMane);

    /**
     * Устанавливает длину гривы льва.
     *
     * @param maneLength длина гривы
     * @return текущий строитель
     */
    LionBuilder withManeLength(double maneLength);
}