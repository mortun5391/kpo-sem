package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.WolfBuilder;
import hse.moscowzoo.domain.entities.alive.Wolf;

/**
 * Реализация строителя волков.
 * Позволяет создавать объекты волков с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class WolfBuilderImpl extends BaseAnimalBuilder<WolfBuilder> implements WolfBuilder {
    private String packRole = "member";
    private double howlVolume = 90.0;

    /**
     * Устанавливает роль волка в стае.
     *
     * @param packRole роль в стае
     * @return текущий строитель
     * @throws IllegalArgumentException если роль в стае пустая
     */
    @Override
    public WolfBuilder withPackRole(String packRole) {
        if (packRole == null || packRole.trim().isEmpty()) {
            throw new IllegalArgumentException("Pack role cannot be empty");
        }
        this.packRole = packRole;
        return this;
    }

    /**
     * Устанавливает громкость воя волка.
     *
     * @param howlVolume громкость воя
     * @return текущий строитель
     * @throws IllegalArgumentException если громкость воя не положительная
     */
    @Override
    public WolfBuilder withHowlVolume(double howlVolume) {
        if (howlVolume <= 0) throw new IllegalArgumentException("Howl volume must be positive");
        this.howlVolume = howlVolume;
        return this;
    }

    /**
     * Создает объект волка с установленными параметрами.
     *
     * @return созданный объект волка
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Wolf build() {
        validateBase();
        return new Wolf(inventoryNumber, name, packRole, howlVolume);
    }
}