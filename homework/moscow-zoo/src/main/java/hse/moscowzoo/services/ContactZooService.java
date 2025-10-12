package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.repositories.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с контактным зоопарком.
 * Предоставляет информацию о животных, подходящих для контактного зоопарка.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class ContactZooService {
    private final AnimalRepository animalRepository;

    /**
     * Возвращает список животных, подходящих для контактного зоопарка.
     *
     * @return список животных для контактного зоопарка
     */
    public List<Animal> getContactZooAnimals() {
        return animalRepository.findContactZooEligible();
    }
}