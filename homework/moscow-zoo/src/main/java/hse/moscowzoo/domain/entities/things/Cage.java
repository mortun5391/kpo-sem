package hse.moscowzoo.domain.entities.things;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий клетку для животных в зоопарке.
 * Клетка имеет определенную площадь и материал изготовления.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Cage extends Thing {
    @Getter
    private final double area;
    @Getter
    private final String material;

    /**
     * Конструктор для создания клетки.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name название клетки
     * @param area площадь клетки в квадратных метрах
     * @param material материал изготовления клетки
     */
    public Cage(int inventoryNumber, String name, double area, String material) {
        super(inventoryNumber, name);
        this.area = area;
        this.material = material;
    }

}