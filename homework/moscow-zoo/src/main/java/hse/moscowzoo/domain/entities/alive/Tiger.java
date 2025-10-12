package hse.moscowzoo.domain.entities.alive;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий тигра в зоопарке.
 * Тигр является хищником с характерными полосами на шкуре.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Tiger extends Predator {
    @Getter
    private final int stripeCount;
    @Getter
    private final String stripePattern;
    private static final int DEFAULT_FOOD_CONSUMPTION = 10;

    /**
     * Конструктор для создания тигра.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя тигра
     * @param stripeCount количество полос на шкуре
     * @param stripePattern узор расположения полос
     */
    public Tiger(int inventoryNumber, String name, int stripeCount, String stripePattern) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION);
        this.stripeCount = stripeCount;
        this.stripePattern = stripePattern;
    }
}