package hse.moscowzoo.domain.entities.alive;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Абстрактный класс для травоядных животных.
 * Травоядные животные имеют уровень доброты, который определяет их пригодность
 * для контактного зоопарка.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public abstract class Herbo extends Animal {
    @Getter
    @Setter
    private KindnessLevel kindnessLevel;

    /**
     * Конструктор для создания травоядного животного.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя животного
     * @param dailyFoodConsumption суточное потребление пищи
     * @param kindnessLevel уровень доброты животного
     */
    public Herbo(int inventoryNumber, String name, int dailyFoodConsumption, KindnessLevel kindnessLevel) {
        super(inventoryNumber, name, dailyFoodConsumption);
        this.kindnessLevel = kindnessLevel;
    }

    /**
     * Определяет, может ли травоядное животное содержаться в контактном зоопарке.
     * Зависит от уровня доброты животного.
     *
     * @return true если уровень доброты больше 5, false в противном случае
     */
    @Override
    public boolean canBeInContactZoo() {
        return getKindnessLevel().isSuitableForContactZoo();
    }

}