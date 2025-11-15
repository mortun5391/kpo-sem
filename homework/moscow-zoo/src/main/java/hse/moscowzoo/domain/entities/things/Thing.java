package hse.moscowzoo.domain.entities.things;

import hse.moscowzoo.domain.interfaces.IInventory;
import lombok.RequiredArgsConstructor;

/**
 * Абстрактный базовый класс для всех предметов в зоопарке.
 * Реализует интерфейс IInventory, предоставляя базовую функциональность
 * для управления предметами в системе инвентаризации.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@RequiredArgsConstructor
public abstract class Thing implements IInventory {
    private final int inventoryNumber;
    private final String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
}