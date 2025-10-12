package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя волков.
 * Определяет методы для создания объектов волков с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface WolfBuilder extends AnimalBuilder<WolfBuilder> {
    /**
     * Устанавливает роль волка в стае.
     *
     * @param packRole роль в стае
     * @return текущий строитель
     */
    WolfBuilder withPackRole(String packRole);

    /**
     * Устанавливает громкость воя волка.
     *
     * @param howlVolume громкость воя
     * @return текущий строитель
     */
    WolfBuilder withHowlVolume(double howlVolume);
}