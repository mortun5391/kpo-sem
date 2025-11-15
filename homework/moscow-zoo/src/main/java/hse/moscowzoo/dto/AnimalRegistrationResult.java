package hse.moscowzoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object для результата регистрации животного.
 * Содержит информацию об успешности операции и дополнительные данные.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Data
@AllArgsConstructor
public class AnimalRegistrationResult {
    /** Флаг успешности операции регистрации */
    private boolean success;

    /** Сообщение о результате операции */
    private String message;

    /** Инвентарный номер зарегистрированного животного (только при успешной регистрации) */
    private Integer animalInventoryNumber;

    /**
     * Конструктор для создания результата с сообщением без инвентарного номера.
     * Используется при неудачной регистрации.
     *
     * @param success флаг успешности операции
     * @param message сообщение о результате операции
     */
    public AnimalRegistrationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.animalInventoryNumber = null;
    }
}