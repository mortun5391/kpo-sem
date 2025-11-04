package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.AnimalBuilder;
import hse.moscowzoo.builders.interfaces.ElephantBuilder;
import hse.moscowzoo.builders.interfaces.FoxBuilder;
import hse.moscowzoo.builders.interfaces.GiraffeBuilder;
import hse.moscowzoo.builders.interfaces.LionBuilder;
import hse.moscowzoo.builders.interfaces.MonkeyBuilder;
import hse.moscowzoo.builders.interfaces.RabbitBuilder;
import hse.moscowzoo.builders.interfaces.TigerBuilder;
import hse.moscowzoo.builders.interfaces.WolfBuilder;
import hse.moscowzoo.domain.valueobjects.AnimalType;

/**
 * Фабрика для создания строителей животных.
 * Предоставляет статические методы для создания конкретных строителей различных типов животных.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class AnimalBuilderFactory {

    /**
     * Создает строитель для кролика.
     *
     * @return экземпляр RabbitBuilder
     */
    public static RabbitBuilder rabbit() {
        return new RabbitBuilderImpl();
    }

    /**
     * Создает строитель для обезьяны.
     *
     * @return экземпляр MonkeyBuilder
     */
    public static MonkeyBuilder monkey() {
        return new MonkeyBuilderImpl();
    }

    /**
     * Создает строитель для слона.
     *
     * @return экземпляр ElephantBuilder
     */
    public static ElephantBuilder elephant() {
        return new ElephantBuilderImpl();
    }

    /**
     * Создает строитель для жирафа.
     *
     * @return экземпляр GiraffeBuilder
     */
    public static GiraffeBuilder giraffe() {
        return new GiraffeBuilderImpl();
    }

    /**
     * Создает строитель для тигра.
     *
     * @return экземпляр TigerBuilder
     */
    public static TigerBuilder tiger() {
        return new TigerBuilderImpl();
    }

    /**
     * Создает строитель для волка.
     *
     * @return экземпляр WolfBuilder
     */
    public static WolfBuilder wolf() {
        return new WolfBuilderImpl();
    }

    /**
     * Создает строитель для льва.
     *
     * @return экземпляр LionBuilder
     */
    public static LionBuilder lion() {
        return new LionBuilderImpl();
    }

    /**
     * Создает строитель для лисы.
     *
     * @return экземпляр FoxBuilder
     */
    public static FoxBuilder fox() {
        return new FoxBuilderImpl();
    }

    /**
     * Создает строитель на основе типа животного.
     *
     * @param type тип животного
     * @return соответствующий строитель животных
     * @throws IllegalArgumentException если передан неизвестный тип животного
     */
    public static AnimalBuilder create(AnimalType type) {
        return switch (type) {
            case RABBIT -> rabbit();
            case MONKEY -> monkey();
            case ELEPHANT -> elephant();
            case GIRAFFE -> giraffe();
            case TIGER -> tiger();
            case WOLF -> wolf();
            case LION -> lion();
            case FOX -> fox();
        };
    }
}