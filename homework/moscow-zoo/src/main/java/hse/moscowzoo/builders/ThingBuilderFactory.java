package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.CageBuilder;
import hse.moscowzoo.builders.interfaces.ComputerBuilder;
import hse.moscowzoo.builders.interfaces.FeedingBowlBuilder;
import hse.moscowzoo.builders.interfaces.TableBuilder;
import hse.moscowzoo.builders.interfaces.ThingBuilder;
import hse.moscowzoo.domain.valueobjects.ThingType;

/**
 * Фабрика для создания строителей вещей.
 * Предоставляет статические методы для создания конкретных строителей различных типов вещей.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class ThingBuilderFactory {

    /**
     * Создает строитель для клетки.
     *
     * @return экземпляр CageBuilder
     */
    public static CageBuilder cage() {
        return new CageBuilderImpl();
    }

    /**
     * Создает строитель для компьютера.
     *
     * @return экземпляр ComputerBuilder
     */
    public static ComputerBuilder computer() {
        return new ComputerBuilderImpl();
    }

    /**
     * Создает строитель для миски для кормления.
     *
     * @return экземпляр FeedingBowlBuilder
     */
    public static FeedingBowlBuilder feedingBowl() {
        return new FeedingBowlBuilderImpl();
    }

    /**
     * Создает строитель для стола.
     *
     * @return экземпляр TableBuilder
     */
    public static TableBuilder table() {
        return new TableBuilderImpl();
    }

    /**
     * Создает строитель на основе типа вещи.
     *
     * @param type тип вещи
     * @return соответствующий строитель вещей
     * @throws IllegalArgumentException если передан неизвестный тип вещи
     */
    public static ThingBuilder create(ThingType type) {
        return switch (type) {
            case COMPUTER -> computer();
            case TABLE -> table();
            case CAGE -> cage();
            case FEEDING_BOWL -> feedingBowl();
        };
    }
}