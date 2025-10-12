package hse.moscowzoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object для результата регистрации предмета.
 * Содержит информацию об успешности операции и дополнительные данные.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Data
@AllArgsConstructor
public class ThingRegistrationResult {
    /** Флаг успешности операции регистрации */
    private boolean success;

    /** Сообщение о результате операции */
    private String message;

    /** Инвентарный номер зарегистрированного предмета (только при успешной регистрации) */
    private Integer thingInventoryNumber;

    /**
     * Конструктор для создания результата с сообщением без инвентарного номера.
     * Используется при неудачной регистрации.
     *
     * @param success флаг успешности операции
     * @param message сообщение о результате операции
     */
    public ThingRegistrationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.thingInventoryNumber = null;
    }
}