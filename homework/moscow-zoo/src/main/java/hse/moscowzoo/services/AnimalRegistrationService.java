package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.repositories.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис для регистрации животных в зоопарке.
 * Обеспечивает проверку здоровья животных перед регистрацией.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class AnimalRegistrationService {
    private final AnimalRepository animalRepository;
    private final VeterinaryClinic veterinaryClinic;

    /**
     * Регистрирует животное в системе после проверки здоровья.
     *
     * @param animal животное для регистрации
     * @return true если животное прошло проверку и зарегистрировано, false в противном случае
     */
    public boolean registerAnimal(Animal animal) {
        if (veterinaryClinic.acceptAnimal(animal)) {
            animalRepository.save(animal);
            return true;
        }
        return false;
    }
}