package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.valueobjects.HealthStatus;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Сервис ветеринарной клиники для проверки здоровья животных.
 * Проводит случайную проверку здоровья животных перед регистрацией.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
public class VeterinaryClinic {
    private final Random random = new Random();

    /**
     * Проверяет здоровье животного.
     * С вероятностью 80% животное считается здоровым, с вероятностью 20% - больным.
     *
     * @param animal животное для проверки
     * @return true если животное здорово, false если больно
     */
    public boolean examineAnimal(Animal animal) {
        boolean isHealthy = random.nextDouble() < 0.8;
        animal.setHealthStatus(isHealthy ? HealthStatus.HEALTHY : HealthStatus.SICK);
        return isHealthy;
    }

    /**
     * Принимает животное в зоопарк после проверки здоровья.
     *
     * @param animal животное для приема
     * @return true если животное прошло проверку здоровья, false в противном случае
     */
    public boolean acceptAnimal(Animal animal) {
        return examineAnimal(animal);
    }
}