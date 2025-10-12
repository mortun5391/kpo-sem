package hse.moscowzoo.domain.entities.alive;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий жирафа в зоопарке.
 * Жираф является травоядным животным с длинной шеей и уникальным узором пятен.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Giraffe extends Herbo {
    @Getter
    private final double neckLength;
    @Getter
    private final String spotPattern;
    private static final int DEFAULT_FOOD_CONSUMPTION = 30;

    /**
     * Конструктор для создания жирафа.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя жирафа
     * @param kindnessLevel уровень доброты жирафа
     * @param neckLength длина шеи в метрах
     * @param spotPattern узор пятен на шкуре
     */
    public Giraffe(int inventoryNumber, String name, KindnessLevel kindnessLevel,
                   double neckLength, String spotPattern) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION, kindnessLevel);
        this.neckLength = neckLength;
        this.spotPattern = spotPattern;
    }

}