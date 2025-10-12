package hse.moscowzoo.repositories;

import hse.moscowzoo.domain.entities.things.Thing;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * In-memory реализация репозитория предметов.
 * Хранит данные о предметах инвентаря в памяти приложения.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Repository
public class InMemoryThingRepository implements ThingsRepository {
    private final List<Thing> things = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Thing thing) {
        things.add(thing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Thing> findById(int inventoryNumber) {
        return things.stream()
                .filter(thing -> thing.getInventoryNumber() == inventoryNumber)
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Thing> findAll() {
        return new ArrayList<>(things);
    }

}