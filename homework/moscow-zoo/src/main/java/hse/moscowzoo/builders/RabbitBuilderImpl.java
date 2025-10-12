package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.RabbitBuilder;
import hse.moscowzoo.domain.entities.alive.Rabbit;

/**
 * Реализация строителя кроликов.
 * Позволяет создавать объекты кроликов с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class RabbitBuilderImpl extends BaseAnimalBuilder<RabbitBuilder> implements RabbitBuilder {
    private double earLength = 10.0;
    private String furType = "short";

    /**
     * Устанавливает длину ушей кролика.
     *
     * @param earLength длина ушей
     * @return текущий строитель
     * @throws IllegalArgumentException если длина ушей не положительная
     */
    @Override
    public RabbitBuilder withEarLength(double earLength) {
        if (earLength <= 0) throw new IllegalArgumentException("Ear length must be positive");
        this.earLength = earLength;
        return this;
    }

    /**
     * Устанавливает тип меха кролика.
     *
     * @param furType тип меха
     * @return текущий строитель
     * @throws IllegalArgumentException если тип меха пустой
     */
    @Override
    public RabbitBuilder withFurType(String furType) {
        if (furType == null || furType.trim().isEmpty()) {
            throw new IllegalArgumentException("Fur type cannot be empty");
        }
        this.furType = furType;
        return this;
    }

    /**
     * Создает объект кролика с установленными параметрами.
     *
     * @return созданный объект кролика
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Rabbit build() {
        validateBase();
        if (kindnessLevel == null) {
            throw new IllegalStateException("KindnessLevel is required for rabbit");
        }
        return new Rabbit(inventoryNumber, name, kindnessLevel, earLength, furType);
    }
}