package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.TigerBuilder;
import hse.moscowzoo.domain.entities.alive.Tiger;

/**
 * Реализация строителя тигров.
 * Позволяет создавать объекты тигров с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class TigerBuilderImpl extends BaseAnimalBuilder<TigerBuilder> implements TigerBuilder {
    private int stripeCount = 100;
    private String stripePattern = "striped";

    /**
     * Устанавливает количество полос у тигра.
     *
     * @param stripeCount количество полос
     * @return текущий строитель
     * @throws IllegalArgumentException если количество полос не положительное
     */
    @Override
    public TigerBuilder withStripeCount(int stripeCount) {
        if (stripeCount <= 0) throw new IllegalArgumentException("Stripe count must be positive");
        this.stripeCount = stripeCount;
        return this;
    }

    /**
     * Устанавливает узор полос тигра.
     *
     * @param stripePattern узор полос
     * @return текущий строитель
     * @throws IllegalArgumentException если узор полос пустой
     */
    @Override
    public TigerBuilder withStripePattern(String stripePattern) {
        if (stripePattern == null || stripePattern.trim().isEmpty()) {
            throw new IllegalArgumentException("Stripe pattern cannot be empty");
        }
        this.stripePattern = stripePattern;
        return this;
    }

    /**
     * Создает объект тигра с установленными параметрами.
     *
     * @return созданный объект тигра
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Tiger build() {
        validateBase();
        return new Tiger(inventoryNumber, name, stripeCount, stripePattern);
    }
}