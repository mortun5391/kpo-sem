package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя обезьян.
 * Определяет методы для создания объектов обезьян с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface MonkeyBuilder extends AnimalBuilder<MonkeyBuilder> {
    /**
     * Устанавливает длину хвоста обезьяны.
     *
     * @param tailLength длина хвоста
     * @return текущий строитель
     */
    MonkeyBuilder withTailLength(double tailLength);

    /**
     * Устанавливает способность обезьяны использовать инструменты.
     *
     * @param canUseTools флаг способности использовать инструменты
     * @return текущий строитель
     */
    MonkeyBuilder withCanUseTools(boolean canUseTools);
}