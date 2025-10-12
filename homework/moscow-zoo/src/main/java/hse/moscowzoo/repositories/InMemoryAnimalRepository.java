package hse.moscowzoo.repositories;

import hse.moscowzoo.domain.entities.alive.Animal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory реализация репозитория животных.
 * Хранит данные о животных в памяти приложения.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Repository
public class InMemoryAnimalRepository implements AnimalRepository{
    private final List<Animal> animals = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Animal animal) {
        animals.add(animal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Animal> findById(int inventoryNumber) {
        return animals.stream()
                .filter(animal -> animal.getInventoryNumber() == inventoryNumber)
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Animal> findContactZooEligible() {
        return animals.stream()
                .filter(Animal::canBeInContactZoo)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalFoodConsumption() {
        return animals.stream()
                .mapToInt(Animal::getDailyFoodConsumption)
                .sum();
    }

}