package hse.moscowzoo.domain.entities.alive;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий кролика в зоопарке.
 * Кролик является травоядным животным с длинными ушами и различными типами меха.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Rabbit extends Herbo {
    @Getter
    private final double earLength;
    @Getter
    private final String furType;
    private static final int DEFAULT_FOOD_CONSUMPTION = 1;

    /**
     * Конструктор для создания кролика.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя кролика
     * @param kindnessLevel уровень доброты кролика
     * @param earLength длина ушей в сантиметрах
     * @param furType тип меха кролика
     */
    public Rabbit(int inventoryNumber, String name, KindnessLevel kindnessLevel,
                  double earLength, String furType) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION, kindnessLevel);
        this.earLength = earLength;
        this.furType = furType;
    }

}