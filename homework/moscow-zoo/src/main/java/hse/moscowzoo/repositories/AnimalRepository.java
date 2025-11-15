package hse.moscowzoo.repositories;

import hse.moscowzoo.domain.entities.alive.Animal;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с животными в зоопарке.
 * Определяет основные операции для управления данными о животных.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface AnimalRepository {
    /**
     * Сохраняет животное в репозитории.
     *
     * @param animal объект животного для сохранения
     */
    void save(Animal animal);

    /**
     * Находит животное по инвентарному номеру.
     *
     * @param inventoryNumber инвентарный номер животного
     * @return Optional с найденным животным или empty, если животное не найдено
     */
    Optional<Animal> findById(int inventoryNumber);

    /**
     * Возвращает список всех животных в репозитории.
     *
     * @return список всех животных
     */
    List<Animal> findAll();

    /**
     * Находит животных, подходящих для контактного зоопарка.
     * Животные считаются подходящими, если они могут находиться в контактном зоопарке.
     *
     * @return список животных, подходящих для контактного зоопарка
     */
    List<Animal> findContactZooEligible();

    /**
     * Вычисляет общее суточное потребление пищи всеми животными.
     *
     * @return общее потребление пищи в килограммах в сутки
     */
    int getTotalFoodConsumption();
}