package hse.moscowzoo.services;

import hse.moscowzoo.builders.ThingBuilderFactory;
import hse.moscowzoo.builders.interfaces.CageBuilder;
import hse.moscowzoo.builders.interfaces.ComputerBuilder;
import hse.moscowzoo.builders.interfaces.FeedingBowlBuilder;
import hse.moscowzoo.builders.interfaces.TableBuilder;
import hse.moscowzoo.builders.interfaces.ThingBuilder;
import hse.moscowzoo.domain.valueobjects.ThingType;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.domain.entities.things.Cage;
import hse.moscowzoo.domain.entities.things.FeedingBowl;
import hse.moscowzoo.dto.ThingDto;
import hse.moscowzoo.dto.ThingRegistrationRequest;
import hse.moscowzoo.dto.ThingRegistrationResult;
import hse.moscowzoo.utils.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления предметами инвентаря в зоопарке.
 * Обеспечивает операции по добавлению, поиску и преобразованию данных о предметах.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class ThingManagementService {
    private final ThingRegistrationService thingRegistrationService;
    private final ZooInventoryService inventoryService;
    private final InputValidator inputValidator;

    /**
     * Добавляет новый предмет в систему инвентаря зоопарка.
     *
     * @param request запрос на регистрацию предмета
     * @return результат операции регистрации
     */
    public ThingRegistrationResult addThing(ThingRegistrationRequest request) {
        try {
            ThingType thingType = convertToThingType(request.getType());

            Thing thing = createThingWithBuilder(thingType, request).build();

            thingRegistrationService.registerThing(thing);

            return new ThingRegistrationResult(true,
                    "Thing '" + request.getName() + "' registered with number: " + thing.getInventoryNumber(),
                    thing.getInventoryNumber());

        } catch (Exception e) {
            return new ThingRegistrationResult(false, "Error: " + e.getMessage());
        }
    }

    /**
     * Создает билдер для предмета определенного типа и запрашивает дополнительные параметры.
     *
     * @param type тип предмета
     * @param request запрос на регистрацию
     * @return настроенный билдер предмета
     */
    private ThingBuilder<?> createThingWithBuilder(ThingType type, ThingRegistrationRequest request) {
        ThingBuilder<?> builder = ThingBuilderFactory.create(type)
                .withName(request.getName());

        switch (type) {
            case CAGE:
                CageBuilder cageBuilder = (CageBuilder) builder;
                double area = inputValidator.getDoubleInput("Enter cage area (m^2): ");
                String material = inputValidator.getStringInput("Enter cage material: ");
                return cageBuilder
                        .withArea(area)
                        .withMaterial(material);

            case FEEDING_BOWL:
                FeedingBowlBuilder feedingBowlBuilder = (FeedingBowlBuilder) builder;
                double capacity = inputValidator.getDoubleInput("Enter capacity (liters): ");
                boolean isAutomatic = inputValidator.getYesNoInput("Is automatic?");
                return feedingBowlBuilder
                        .withCapacity(capacity)
                        .withAutomatic(isAutomatic);

            case COMPUTER:
                return (ComputerBuilder) builder;

            case TABLE:
                return (TableBuilder) builder;

            default:
                throw new IllegalArgumentException("Unsupported thing type: " + type);
        }
    }

    /**
     * Возвращает список всех предметов в системе.
     *
     * @return список DTO объектов предметов
     */
    public List<ThingDto> getAllThings() {
        return inventoryService.getThings().stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Преобразует объект Thing в DTO для отображения.
     *
     * @param thing объект предмета
     * @return DTO объект предмета
     */
    ThingDto convertToDto(Thing thing) {
        String additionalInfo = "";

        if (thing instanceof Cage cage) {
            additionalInfo = String.format(" (Area: %.1f m^2, Material: %s)",
                    cage.getArea(), cage.getMaterial());
        } else if (thing instanceof FeedingBowl bowl) {
            String type = bowl.isAutomatic() ? "Automatic" : "Manual";
            additionalInfo = String.format(" (Capacity: %.1fL, Type: %s)",
                    bowl.getCapacity(), type);
        }

        return new ThingDto(
                thing.getName(),
                thing.getInventoryNumber(),
                thing.getClass().getSimpleName() + additionalInfo
        );
    }

    /**
     * Преобразует числовой код типа предмета в enum ThingType.
     *
     * @param type числовой код типа предмета
     * @return соответствующий enum ThingType
     * @throws IllegalArgumentException если передан неизвестный код типа
     */
    ThingType convertToThingType(int type) {
        return switch (type) {
            case 1 -> ThingType.COMPUTER;
            case 2 -> ThingType.TABLE;
            case 3 -> ThingType.CAGE;
            case 4 -> ThingType.FEEDING_BOWL;
            default -> throw new IllegalArgumentException("Unknown thing type: " + type);
        };
    }
}