package hse.moscowzoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object для представления информации о животном.
 * Используется для передачи данных между слоями приложения.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDto {
    /** Имя животного */
    private String name;

    /** Уникальный инвентарный номер животного */
    private int inventoryNumber;

    /** Тип животного с дополнительной информацией */
    private String animalType;

    /** Статус здоровья животного */
    private String healthStatus;

    /** Уровень доброты животного в формате "X/10" (только для травоядных) */
    private String kindnessLevel;
}