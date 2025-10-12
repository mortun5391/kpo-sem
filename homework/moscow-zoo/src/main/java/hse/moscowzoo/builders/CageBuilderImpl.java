package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.CageBuilder;
import hse.moscowzoo.domain.entities.things.Cage;

/**
 * Реализация строителя клеток.
 * Позволяет создавать объекты клеток с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class CageBuilderImpl extends BaseThingBuilder<CageBuilder> implements CageBuilder {
    private Double area;
    private String material;

    /**
     * Устанавливает площадь клетки.
     *
     * @param area площадь клетки
     * @return текущий строитель
     */
    @Override
    public CageBuilder withArea(double area) {
        this.area = area;
        return this;
    }

    /**
     * Устанавливает материал клетки.
     *
     * @param material материал клетки
     * @return текущий строитель
     */
    @Override
    public CageBuilder withMaterial(String material) {
        this.material = material;
        return this;
    }

    /**
     * Создает объект клетки с установленными параметрами.
     *
     * @return созданный объект клетки
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Cage build() {
        validateBase();
        if (area == null) {
            throw new IllegalStateException("Cage area is required");
        }
        if (material == null || material.trim().isEmpty()) {
            throw new IllegalStateException("Cage material is required");
        }
        return new Cage(inventoryNumber, name, area, material);
    }
}