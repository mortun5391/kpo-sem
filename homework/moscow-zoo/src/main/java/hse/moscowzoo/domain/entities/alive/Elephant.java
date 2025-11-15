package hse.moscowzoo.domain.entities.alive;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий слона в зоопарке.
 * Слон является травоядным животным с высоким уровнем доброты.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Elephant extends Herbo {
    @Getter
    private final double trunkLength;
    @Getter
    private final double tuskLength;
    private static final int DEFAULT_FOOD_CONSUMPTION = 200;

    /**
     * Конструктор для создания слона.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя слона
     * @param kindnessLevel уровень доброты слона
     * @param trunkLength длина хобота в метрах
     * @param tuskLength длина бивней в метрах
     */
    public Elephant(int inventoryNumber, String name, KindnessLevel kindnessLevel,
                    double trunkLength, double tuskLength) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION, kindnessLevel);
        this.trunkLength = trunkLength;
        this.tuskLength = tuskLength;
    }

}