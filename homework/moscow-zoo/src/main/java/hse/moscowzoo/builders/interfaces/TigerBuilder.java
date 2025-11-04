package hse.moscowzoo.builders.interfaces;

/**
 * Интерфейс строителя тигров.
 * Определяет методы для создания объектов тигров с дополнительными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface TigerBuilder extends AnimalBuilder<TigerBuilder> {
    /**
     * Устанавливает количество полос у тигра.
     *
     * @param stripeCount количество полос
     * @return текущий строитель
     */
    TigerBuilder withStripeCount(int stripeCount);

    /**
     * Устанавливает узор полос тигра.
     *
     * @param stripePattern узор полос
     * @return текущий строитель
     */
    TigerBuilder withStripePattern(String stripePattern);
}