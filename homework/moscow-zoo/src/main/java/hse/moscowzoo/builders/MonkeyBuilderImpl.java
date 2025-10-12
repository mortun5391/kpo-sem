package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.MonkeyBuilder;
import hse.moscowzoo.domain.entities.alive.Monkey;

/**
 * Реализация строителя обезьян.
 * Позволяет создавать объекты обезьян с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class MonkeyBuilderImpl extends BaseAnimalBuilder<MonkeyBuilder> implements MonkeyBuilder {
    private double tailLength = 0.5;
    private boolean canUseTools = false;

    /**
     * Устанавливает длину хвоста обезьяны.
     *
     * @param tailLength длина хвоста
     * @return текущий строитель
     * @throws IllegalArgumentException если длина хвоста отрицательная
     */
    @Override
    public MonkeyBuilder withTailLength(double tailLength) {
        if (tailLength < 0) throw new IllegalArgumentException("Tail length cannot be negative");
        this.tailLength = tailLength;
        return this;
    }

    /**
     * Устанавливает способность использовать инструменты.
     *
     * @param canUseTools флаг способности использовать инструменты
     * @return текущий строитель
     */
    @Override
    public MonkeyBuilder withCanUseTools(boolean canUseTools) {
        this.canUseTools = canUseTools;
        return this;
    }

    /**
     * Создает объект обезьяны с установленными параметрами.
     *
     * @return созданный объект обезьяны
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Monkey build() {
        validateBase();
        if (kindnessLevel == null) {
            throw new IllegalStateException("KindnessLevel is required for monkey");
        }
        return new Monkey(inventoryNumber, name, kindnessLevel, tailLength, canUseTools);
    }
}