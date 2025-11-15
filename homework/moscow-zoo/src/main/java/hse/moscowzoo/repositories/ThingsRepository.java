package hse.moscowzoo.repositories;

import hse.moscowzoo.domain.entities.things.Thing;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для работы с предметами инвентаря в зоопарке.
 * Определяет основные операции для управления данными о предметах.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public interface ThingsRepository {
    /**
     * Сохраняет предмет в репозитории.
     *
     * @param thing объект предмета для сохранения
     */
    void save(Thing thing);

    /**
     * Находит предмет по инвентарному номеру.
     *
     * @param inventoryNumber инвентарный номер предмета
     * @return Optional с найденным предметом или empty, если предмет не найден
     */
    Optional<Thing> findById(int inventoryNumber);

    /**
     * Возвращает список всех предметов в репозитории.
     *
     * @return список всех предметов
     */
    List<Thing> findAll();
}