package hse.moscowzoo.domain.entities.alive;

import hse.moscowzoo.domain.interfaces.IAlive;
import hse.moscowzoo.domain.interfaces.IInventory;
import hse.moscowzoo.domain.valueobjects.HealthStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Абстрактный базовый класс для всех животных в зоопарке.
 * Реализует интерфейсы IAlive и IInventory, предоставляя базовую функциональность
 * для управления животными в системе инвентаризации.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public abstract class Animal implements IAlive, IInventory {
    private final int inventoryNumber;
    private final String name;

    @Setter
    private int dailyFoodConsumption;

    @Setter
    @Getter
    private HealthStatus healthStatus;

    /**
     * Конструктор для создания животного.
     *
     * @param inventoryNumber уникальный инвентарный номер животного
     * @param name имя животного
     * @param dailyFoodConsumption суточное потребление пищи в килограммах
     */
    public Animal(int inventoryNumber, String name, int dailyFoodConsumption) {
        this.inventoryNumber = inventoryNumber;
        this.name = name;
        this.dailyFoodConsumption = dailyFoodConsumption;
        this.healthStatus = HealthStatus.HEALTHY; // default
    }

    /**
     * Определяет, может ли животное содержаться в контактном зоопарке.
     * Реализация зависит от конкретного вида животного.
     *
     * @return true если животное может быть в контактном зоопарке, false в противном случае
     */
    public abstract boolean canBeInContactZoo();

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDailyFoodConsumption() {
        return dailyFoodConsumption;
    }

}