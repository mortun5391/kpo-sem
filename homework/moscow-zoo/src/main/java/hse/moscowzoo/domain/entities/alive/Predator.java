package hse.moscowzoo.domain.entities.alive;

/**
 * Абстрактный класс для хищных животных.
 * Хищники не могут содержаться в контактном зоопарке из-за потенциальной опасности.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public abstract class Predator extends Animal{
    /**
     * Конструктор для создания хищного животного.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя животного
     * @param dailyFoodConsumption суточное потребление пищи
     */
    public Predator(int inventoryNumber, String name, int dailyFoodConsumption) {
        super(inventoryNumber, name, dailyFoodConsumption);
    }

    /**
     * Определяет, может ли хищное животное содержаться в контактном зоопарке.
     * Хищники не могут быть в контактном зоопарке из соображений безопасности.
     *
     * @return всегда false для хищников
     */
    @Override
    public boolean canBeInContactZoo() {
        return false;
    }

}