package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.repositories.ThingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Сервис для регистрации предметов инвентаря в зоопарке.
 * Обеспечивает сохранение предметов в репозитории.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class ThingRegistrationService {
    private final ThingsRepository thingsRepository;

    /**
     * Регистрирует предмет в системе инвентаря.
     *
     * @param thing предмет для регистрации
     * @return всегда true, так как регистрация предметов всегда успешна
     */
    public boolean registerThing(Thing thing) {
        thingsRepository.save(thing);
        return true;
    }
}