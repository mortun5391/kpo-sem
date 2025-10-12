package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.LionBuilder;
import hse.moscowzoo.domain.entities.alive.Lion;

/**
 * Реализация строителя львов.
 * Позволяет создавать объекты львов с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class LionBuilderImpl extends BaseAnimalBuilder<LionBuilder> implements LionBuilder {
    private boolean hasMane = true;
    private double maneLength = 0.3;

    /**
     * Устанавливает наличие гривы у льва.
     *
     * @param hasMane флаг наличия гривы
     * @return текущий строитель
     */
    @Override
    public LionBuilder withHasMane(boolean hasMane) {
        this.hasMane = hasMane;
        return this;
    }

    /**
     * Устанавливает длину гривы льва.
     *
     * @param maneLength длина гривы
     * @return текущий строитель
     * @throws IllegalArgumentException если длина гривы не положительная
     */
    @Override
    public LionBuilder withManeLength(double maneLength) {
        if (maneLength <= 0) throw new IllegalArgumentException("Mane length must be positive");
        this.maneLength = maneLength;
        return this;
    }

    /**
     * Создает объект льва с установленными параметрами.
     *
     * @return созданный объект льва
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Lion build() {
        validateBase();
        return new Lion(inventoryNumber, name, hasMane, maneLength);
    }
}