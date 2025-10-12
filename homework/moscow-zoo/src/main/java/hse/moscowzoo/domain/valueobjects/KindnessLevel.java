package hse.moscowzoo.domain.valueobjects;

/**
 * Класс, представляющий уровень доброты животного.
 * Значение уровня должно быть в диапазоне от 1 до 10.
 * Уровень доброты определяет пригодность животного для контактного зоопарка.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class KindnessLevel {
    private final int value;

    /**
     * Приватный конструктор для создания уровня доброты.
     *
     * @param value значение уровня доброты (от 1 до 10)
     * @throws IllegalArgumentException если значение выходит за допустимые пределы
     */
    private KindnessLevel(int value) {
        if (value < 1 || value > 10) {
            throw new IllegalArgumentException("Уровень доброты должен быть от 1 до 10");
        }
        this.value = value;
    }

    /**
     * Фабричный метод для создания уровня доброты.
     *
     * @param value значение уровня доброты (от 1 до 10)
     * @return новый объект KindnessLevel
     * @throws IllegalArgumentException если значение выходит за допустимые пределы
     */
    public static KindnessLevel of(int value) {
        return new KindnessLevel(value);
    }

    /**
     * Создает уровень доброты со значением по умолчанию (5).
     *
     * @return уровень доброты со значением 5
     */
    public static KindnessLevel defaultLevel() {
        return new KindnessLevel(5);
    }

    /**
     * Возвращает числовое значение уровня доброты.
     *
     * @return значение уровня доброты от 1 до 10
     */
    public int getValue() {
        return value;
    }

    /**
     * Проверяет, подходит ли животное с данным уровнем доброты для контактного зоопарка.
     * Животное считается подходящим, если уровень доброты больше 5.
     *
     * @return true если уровень доброты больше 5, false в противном случае
     */
    public boolean isSuitableForContactZoo() {
        return value > 5;
    }
}