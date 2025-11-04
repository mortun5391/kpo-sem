package hse.moscowzoo.domain.entities.alive;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий обезьяну в зоопарке.
 * Обезьяна является травоядным животным, некоторые виды могут использовать инструменты.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Monkey extends Herbo {
    @Getter
    private final double tailLength;
    @Getter
    private final boolean canUseTools;
    private static final int DEFAULT_FOOD_CONSUMPTION = 3;

    /**
     * Конструктор для создания обезьяны.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя обезьяны
     * @param kindnessLevel уровень доброты обезьяны
     * @param tailLength длина хвоста в метрах
     * @param canUseTools способность использовать инструменты
     */
    public Monkey(int inventoryNumber, String name, KindnessLevel kindnessLevel,
                  double tailLength, boolean canUseTools) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION, kindnessLevel);
        this.tailLength = tailLength;
        this.canUseTools = canUseTools;
    }

}