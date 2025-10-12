package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.GiraffeBuilder;
import hse.moscowzoo.domain.entities.alive.Giraffe;

/**
 * Реализация строителя жирафов.
 * Позволяет создавать объекты жирафов с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class GiraffeBuilderImpl extends BaseAnimalBuilder<GiraffeBuilder> implements GiraffeBuilder {
    private double neckLength = 2.5;
    private String spotPattern = "regular";

    /**
     * Устанавливает длину шеи жирафа.
     *
     * @param neckLength длина шеи
     * @return текущий строитель
     * @throws IllegalArgumentException если длина шеи не положительная
     */
    @Override
    public GiraffeBuilder withNeckLength(double neckLength) {
        if (neckLength <= 0) throw new IllegalArgumentException("Neck length must be positive");
        this.neckLength = neckLength;
        return this;
    }

    /**
     * Устанавливает узор пятен жирафа.
     *
     * @param spotPattern узор пятен
     * @return текущий строитель
     * @throws IllegalArgumentException если узор пятен пустой
     */
    @Override
    public GiraffeBuilder withSpotPattern(String spotPattern) {
        if (spotPattern == null || spotPattern.trim().isEmpty()) {
            throw new IllegalArgumentException("Spot pattern cannot be empty");
        }
        this.spotPattern = spotPattern;
        return this;
    }

    /**
     * Создает объект жирафа с установленными параметрами.
     *
     * @return созданный объект жирафа
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Giraffe build() {
        validateBase();
        if (kindnessLevel == null) {
            throw new IllegalStateException("KindnessLevel is required for giraffe");
        }
        return new Giraffe(inventoryNumber, name, kindnessLevel, neckLength, spotPattern);
    }
}