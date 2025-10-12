package hse.moscowzoo.dto;

import lombok.Data;

/**
 * Data Transfer Object для запроса на регистрацию нового предмета.
 * Содержит данные, необходимые для создания предмета в системе инвентаря.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Data
public class ThingRegistrationRequest {
    /** Название предмета */
    private String name;

    /** Тип предмета в числовом формате: 1-computer, 2-table, 3-cage, 4-feeding bowl */
    private int type;
}