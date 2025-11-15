package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.ElephantBuilder;
import hse.moscowzoo.domain.entities.alive.Elephant;

/**
 * Реализация строителя слонов.
 * Позволяет создавать объекты слонов с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class ElephantBuilderImpl extends BaseAnimalBuilder<ElephantBuilder> implements ElephantBuilder {
    private double trunkLength = 2.0;
    private double tuskLength = 1.5;

    /**
     * Устанавливает длину хобота слона.
     *
     * @param trunkLength длина хобота
     * @return текущий строитель
     * @throws IllegalArgumentException если длина хобота не положительная
     */
    @Override
    public ElephantBuilder withTrunkLength(double trunkLength) {
        if (trunkLength <= 0) throw new IllegalArgumentException("Trunk length must be positive");
        this.trunkLength = trunkLength;
        return this;
    }

    /**
     * Устанавливает длину бивней слона.
     *
     * @param tuskLength длина бивней
     * @return текущий строитель
     * @throws IllegalArgumentException если длина бивней отрицательная
     */
    @Override
    public ElephantBuilder withTuskLength(double tuskLength) {
        if (tuskLength < 0) throw new IllegalArgumentException("Tusk length cannot be negative");
        this.tuskLength = tuskLength;
        return this;
    }

    /**
     * Создает объект слона с установленными параметрами.
     *
     * @return созданный объект слона
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Elephant build() {
        validateBase();
        if (kindnessLevel == null) {
            throw new IllegalStateException("KindnessLevel is required for elephant");
        }
        return new Elephant(inventoryNumber, name, kindnessLevel, trunkLength, tuskLength);
    }
}