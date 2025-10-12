package hse.moscowzoo.services;

import hse.moscowzoo.repositories.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис для расчета потребления пищи животными в зоопарке.
 * Предоставляет информацию об общем потреблении пищи и количестве животных.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class FoodCalculationService {
    private final AnimalRepository animalRepository;

    /**
     * Вычисляет общее суточное потребление пищи всеми животными.
     *
     * @return общее потребление пищи в килограммах в сутки
     */
    public int calculateTotalFoodConsumption() {
        return animalRepository.getTotalFoodConsumption();
    }

    /**
     * Возвращает общее количество животных в зоопарке.
     *
     * @return количество животных
     */
    public int getAnimalCount() {
        return animalRepository.findAll().size();
    }
}