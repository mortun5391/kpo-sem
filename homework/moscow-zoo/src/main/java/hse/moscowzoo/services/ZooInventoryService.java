package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.domain.interfaces.IInventory;
import hse.moscowzoo.repositories.AnimalRepository;
import hse.moscowzoo.repositories.ThingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления общим инвентарем зоопарка.
 * Предоставляет доступ ко всем объектам инвентаря (животным и предметам).
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class ZooInventoryService {
    private final AnimalRepository animalRepository;
    private final ThingsRepository thingsRepository;

    /**
     * Возвращает полный список всех объектов инвентаря зоопарка.
     *
     * @return список всех объектов инвентаря (животных и предметов)
     */
    public List<IInventory> getAllInventory() {
        List<IInventory> inventory = new ArrayList<>();
        inventory.addAll(animalRepository.findAll());
        inventory.addAll(thingsRepository.findAll());
        return inventory;
    }

    /**
     * Возвращает список всех животных в зоопарке.
     *
     * @return список животных
     */
    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Возвращает список всех предметов инвентаря в зоопарке.
     *
     * @return список предметов
     */
    public List<Thing> getThings() {
        return thingsRepository.findAll();
    }
}