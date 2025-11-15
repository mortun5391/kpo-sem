package hse.moscowzoo.domain.valueobjects;

/**
 * Перечисление типов животных в зоопарке.
 * Определяет, требует ли тип животного указания уровня доброты.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public enum AnimalType {
    /** Кролик - травоядное животное */
    RABBIT(true),
    /** Обезьяна - травоядное животное */
    MONKEY(true),
    /** Слон - травоядное животное */
    ELEPHANT(true),
    /** Жираф - травоядное животное */
    GIRAFFE(true),
    /** Тигр - хищное животное */
    TIGER(false),
    /** Волк - хищное животное */
    WOLF(false),
    /** Лев - хищное животное */
    LION(false),
    /** Лиса - хищное животное */
    FOX(false);

    private final boolean requiresKindnessLevel;

    /**
     * Конструктор для типа животного.
     *
     * @param requiresKindnessLevel требуется ли для данного типа уровень доброты
     */
    AnimalType(boolean requiresKindnessLevel) {
        this.requiresKindnessLevel = requiresKindnessLevel;
    }

    /**
     * Проверяет, требуется ли для данного типа животного указание уровня доброты.
     * Травоядные животные требуют уровень доброты, хищные - нет.
     *
     * @return true если требуется уровень доброты, false в противном случае
     */
    public boolean requiresKindnessLevel() {
        return requiresKindnessLevel;
    }
}