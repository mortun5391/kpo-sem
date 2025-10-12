package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.FoxBuilder;
import hse.moscowzoo.domain.entities.alive.Fox;

/**
 * Реализация строителя лис.
 * Позволяет создавать объекты лис с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class FoxBuilderImpl extends BaseAnimalBuilder<FoxBuilder> implements FoxBuilder {
    private String furColor = "red";
    private double tailLength = 0.4;

    /**
     * Устанавливает цвет меха лисы.
     *
     * @param furColor цвет меха
     * @return текущий строитель
     * @throws IllegalArgumentException если цвет меха пустой
     */
    @Override
    public FoxBuilder withFurColor(String furColor) {
        if (furColor == null || furColor.trim().isEmpty()) {
            throw new IllegalArgumentException("Fur color cannot be empty");
        }
        this.furColor = furColor;
        return this;
    }

    /**
     * Устанавливает длину хвоста лисы.
     *
     * @param tailLength длина хвоста
     * @return текущий строитель
     * @throws IllegalArgumentException если длина хвоста отрицательная
     */
    @Override
    public FoxBuilder withTailLength(double tailLength) {
        if (tailLength < 0) throw new IllegalArgumentException("Tail length cannot be negative");
        this.tailLength = tailLength;
        return this;
    }

    /**
     * Создает объект лисы с установленными параметрами.
     *
     * @return созданный объект лисы
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Fox build() {
        validateBase();
        return new Fox(inventoryNumber, name, furColor, tailLength);
    }
}