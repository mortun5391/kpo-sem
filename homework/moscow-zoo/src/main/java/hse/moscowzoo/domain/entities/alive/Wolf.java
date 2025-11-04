package hse.moscowzoo.domain.entities.alive;

import lombok.Getter;
import lombok.ToString;

/**
 * Класс, представляющий волка в зоопарке.
 * Волк является хищником, живущим в стае с определенной социальной структурой.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@ToString
public class Wolf extends Predator {
    @Getter
    private final String packRole;
    @Getter
    private final double howlVolume;
    private static final int DEFAULT_FOOD_CONSUMPTION = 8;

    /**
     * Конструктор для создания волка.
     *
     * @param inventoryNumber уникальный инвентарный номер
     * @param name имя волка
     * @param packRole роль волка в стае (вожак, бета, омега и т.д.)
     * @param howlVolume громкость воя в децибелах
     */
    public Wolf(int inventoryNumber, String name, String packRole, double howlVolume) {
        super(inventoryNumber, name, DEFAULT_FOOD_CONSUMPTION);
        this.packRole = packRole;
        this.howlVolume = howlVolume;
    }
}