package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя слонов.
 * Определяет методы для создания объектов слонов с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface ElephantBuilder extends AnimalBuilder<ElephantBuilder> {
    /**
     * Устанавливает длину хобота слона.
     *
     * @param trunkLength длина хобота
     * @return текущий строитель
     */
    ElephantBuilder withTrunkLength(double trunkLength);

    /**
     * Устанавливает длину бивней слона.
     *
     * @param tuskLength длина бивней
     * @return текущий строитель
     */
    ElephantBuilder withTuskLength(double tuskLength);
}