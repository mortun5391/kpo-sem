package hse.moscowzoo.dto;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.Data;

/**
 * Data Transfer Object для запроса на регистрацию нового животного.
 * Содержит данные, необходимые для создания животного в системе.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Data
public class AnimalRegistrationRequest {
    /** Имя животного */
    private String name;

    /** Тип животного в числовом формате: 1-rabbit, 2-monkey, 3-elephant, 4-giraffe, 5-tiger, 6-wolf, 7-lion, 8-fox */
    private int type;

    /** Уровень доброты животного (требуется только для травоядных) */
    private KindnessLevel kindnessLevel;
}