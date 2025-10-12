package hse.moscowzoo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object для представления информации о предмете.
 * Используется для передачи данных о предметах инвентаря между слоями приложения.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThingDto {
    /** Название предмета */
    private String name;

    /** Уникальный инвентарный номер предмета */
    private int inventoryNumber;

    /** Тип предмета с дополнительной информацией */
    private String thingType;
}