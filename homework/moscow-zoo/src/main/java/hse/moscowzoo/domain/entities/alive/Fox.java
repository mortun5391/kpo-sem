package hse.moscowzoo.domain.entities.alive;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий лису в зоопарке.
 * Лиса является хищником с характерным окрасом и хвостом.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Fox extends Predator {
    @Getter
    private final String furColor;
    @Getter
    private final double tailLength;
    private static final int DEFAULT_FOOD_CONSUMPTION = 2;

    /**
     * Конструктор для создания лисы.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя лисы
     * @param furColor цвет меха лисы
     * @param tailLength длина хвоста в метрах
     */
    public Fox(int inventoryNumber, String name, String furColor, double tailLength) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION);
        this.furColor = furColor;
        this.tailLength = tailLength;
    }

}