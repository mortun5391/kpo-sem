package hse.moscowzoo.services;

import hse.moscowzoo.builders.AnimalBuilderFactory;
import hse.moscowzoo.builders.interfaces.AnimalBuilder;
import hse.moscowzoo.builders.interfaces.ElephantBuilder;
import hse.moscowzoo.builders.interfaces.FoxBuilder;
import hse.moscowzoo.builders.interfaces.GiraffeBuilder;
import hse.moscowzoo.builders.interfaces.LionBuilder;
import hse.moscowzoo.builders.interfaces.MonkeyBuilder;
import hse.moscowzoo.builders.interfaces.RabbitBuilder;
import hse.moscowzoo.builders.interfaces.TigerBuilder;
import hse.moscowzoo.builders.interfaces.WolfBuilder;
import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.entities.alive.Elephant;
import hse.moscowzoo.domain.entities.alive.Fox;
import hse.moscowzoo.domain.entities.alive.Giraffe;
import hse.moscowzoo.domain.entities.alive.Herbo;
import hse.moscowzoo.domain.entities.alive.Lion;
import hse.moscowzoo.domain.entities.alive.Monkey;
import hse.moscowzoo.domain.entities.alive.Rabbit;
import hse.moscowzoo.domain.entities.alive.Tiger;
import hse.moscowzoo.domain.entities.alive.Wolf;
import hse.moscowzoo.domain.valueobjects.AnimalType;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import hse.moscowzoo.dto.AnimalDto;
import hse.moscowzoo.dto.AnimalRegistrationRequest;
import hse.moscowzoo.dto.AnimalRegistrationResult;
import hse.moscowzoo.utils.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления животными в зоопарке.
 * Обеспечивает операции по добавлению, поиску и преобразованию данных о животных.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Service
@RequiredArgsConstructor
public class AnimalManagementService {
    private final AnimalRegistrationService registrationService;
    private final ZooInventoryService inventoryService;
    private final InputValidator inputValidator;

    /**
     * Добавляет новое животное в систему зоопарка.
     *
     * @param request запрос на регистрацию животного
     * @return результат операции регистрации
     */
    public AnimalRegistrationResult addAnimal(AnimalRegistrationRequest request) {
        try {
            AnimalType animalType = convertToAnimalType(request.getType());

            AnimalBuilder<?> builder = createAnimalWithBuilder(animalType, request);

            builder.withName(request.getName());

            if (animalType.requiresKindnessLevel() && request.getKindnessLevel() != null) {
                builder.withKindness(request.getKindnessLevel());
            }

            Animal animal = builder.build();
            boolean accepted = registrationService.registerAnimal(animal);

            String message = accepted ?
                    "Animal '" + request.getName() + "' registered with number: " + animal.getInventoryNumber() :
                    "Animal '" + request.getName() + "' registration failed";

            return new AnimalRegistrationResult(accepted, message, animal.getInventoryNumber());

        } catch (Exception e) {
            return new AnimalRegistrationResult(false, "Error: " + e.getMessage());
        }
    }

    /**
     * Возвращает список всех животных в системе.
     *
     * @return список DTO объектов животных
     */
    public List<AnimalDto> getAllAnimals() {
        return inventoryService.getAnimals().stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Находит животное по инвентарному номеру.
     *
     * @param inventoryNumber инвентарный номер животного
     * @return DTO объект животного или null, если животное не найдено
     */
    public AnimalDto getAnimalById(int inventoryNumber) {
        return inventoryService.getAnimals().stream()
                .filter(animal -> animal.getInventoryNumber() == inventoryNumber)
                .findFirst()
                .map(this::convertToDto)
                .orElse(null);
    }

    /**
     * Запрашивает у пользователя ввод уровня доброты животного.
     *
     * @return объект KindnessLevel с введенным значением
     */
    public KindnessLevel readKindnessLevelFromUser() {
        return inputValidator.readKindnessLevel();
    }

    /**
     * Создает билдер для животного определенного типа и запрашивает дополнительные параметры.
     *
     * @param type тип животного
     * @param request запрос на регистрацию
     * @return настроенный билдер животного
     */
    private AnimalBuilder<?> createAnimalWithBuilder(AnimalType type, AnimalRegistrationRequest request) {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(type);

        switch (type) {
            case RABBIT:
                RabbitBuilder rabbitBuilder = (RabbitBuilder) builder;
                double earLength = inputValidator.getDoubleInput("Enter ear length (cm): ");
                String furType = inputValidator.getStringInput("Enter fur type: ");
                rabbitBuilder.withEarLength(earLength).withFurType(furType);
                break;

            case MONKEY:
                MonkeyBuilder monkeyBuilder = (MonkeyBuilder) builder;
                double monkeyTailLength = inputValidator.getDoubleInput("Enter tail length (m): ");
                boolean canUseTools = inputValidator.getYesNoInput("Can use tools?");
                monkeyBuilder.withTailLength(monkeyTailLength).withCanUseTools(canUseTools);
                break;

            case ELEPHANT:
                ElephantBuilder elephantBuilder = (ElephantBuilder) builder;
                double trunkLength = inputValidator.getDoubleInput("Enter trunk length (m): ");
                double tuskLength = inputValidator.getDoubleInput("Enter tusk length (m): ");
                elephantBuilder.withTrunkLength(trunkLength).withTuskLength(tuskLength);
                break;

            case GIRAFFE:
                GiraffeBuilder giraffeBuilder = (GiraffeBuilder) builder;
                double neckLength = inputValidator.getDoubleInput("Enter neck length (m): ");
                String spotPattern = inputValidator.getStringInput("Enter spot pattern: ");
                giraffeBuilder.withNeckLength(neckLength).withSpotPattern(spotPattern);
                break;

            case TIGER:
                TigerBuilder tigerBuilder = (TigerBuilder) builder;
                int stripeCount = inputValidator.getIntInput("Enter stripe count: ");
                String stripePattern = inputValidator.getStringInput("Enter stripe pattern: ");
                tigerBuilder.withStripeCount(stripeCount).withStripePattern(stripePattern);
                break;

            case WOLF:
                WolfBuilder wolfBuilder = (WolfBuilder) builder;
                String packRole = inputValidator.getStringInput("Enter pack role: ");
                double howlVolume = inputValidator.getDoubleInput("Enter howl volume (dB): ");
                wolfBuilder.withPackRole(packRole).withHowlVolume(howlVolume);
                break;

            case LION:
                LionBuilder lionBuilder = (LionBuilder) builder;
                boolean hasMane = inputValidator.getYesNoInput("Has mane?");
                double maneLength = 0;
                if (hasMane) {
                    maneLength = inputValidator.getDoubleInput("Enter mane length (m): ");
                }
                lionBuilder.withHasMane(hasMane).withManeLength(maneLength);
                break;

            case FOX:
                FoxBuilder foxBuilder = (FoxBuilder) builder;
                String furColor = inputValidator.getStringInput("Enter fur color: ");
                double foxTailLength = inputValidator.getDoubleInput("Enter tail length (m): ");
                foxBuilder.withFurColor(furColor).withTailLength(foxTailLength);
                break;
        }

        return builder;
    }

    /**
     * Преобразует объект Animal в DTO для отображения.
     *
     * @param animal объект животного
     * @return DTO объект животного
     */
    private AnimalDto convertToDto(Animal animal) {
        String kindnessLevelStr = null;
        if (animal instanceof Herbo) {
            kindnessLevelStr = ((Herbo) animal).getKindnessLevel().getValue() + "/10";
        }

        String additionalInfo = getAnimalAdditionalInfo(animal);

        return new AnimalDto(
                animal.getName(),
                animal.getInventoryNumber(),
                animal.getClass().getSimpleName() + additionalInfo,
                animal.getHealthStatus().toString(),
                kindnessLevelStr
        );
    }

    /**
     * Генерирует дополнительную информацию о животном для отображения.
     *
     * @param animal объект животного
     * @return строка с дополнительной информацией
     */
    private String getAnimalAdditionalInfo(Animal animal) {
        if (animal instanceof Rabbit rabbit) {
            return String.format(" (Ears: %.1fcm, Fur: %s)",
                    rabbit.getEarLength(), rabbit.getFurType());
        } else if (animal instanceof Monkey monkey) {
            String tools = monkey.isCanUseTools() ? "tool-user" : "no-tools";
            return String.format(" (Tail: %.1fm, %s)",
                    monkey.getTailLength(), tools);
        } else if (animal instanceof Elephant elephant) {
            return String.format(" (Trunk: %.1fm, Tusks: %.1fm)",
                    elephant.getTrunkLength(), elephant.getTuskLength());
        } else if (animal instanceof Giraffe giraffe) {
            return String.format(" (Neck: %.1fm, Spots: %s)",
                    giraffe.getNeckLength(), giraffe.getSpotPattern());
        } else if (animal instanceof Tiger tiger) {
            return String.format(" (Stripes: %d, Pattern: %s)",
                    tiger.getStripeCount(), tiger.getStripePattern());
        } else if (animal instanceof Wolf wolf) {
            return String.format(" (Role: %s, Howl: %.1fdB)",
                    wolf.getPackRole(), wolf.getHowlVolume());
        } else if (animal instanceof Lion lion) {
            if (lion.isHasMane()) {
                return String.format(" (Mane: %.1fm)", lion.getManeLength());
            } else {
                return " (No mane)";
            }
        } else if (animal instanceof Fox fox) {
            return String.format(" (Color: %s, Tail: %.1fm)",
                    fox.getFurColor(), fox.getTailLength());
        }
        return "";
    }

    /**
     * Преобразует числовой код типа животного в enum AnimalType.
     *
     * @param type числовой код типа животного
     * @return соответствующий enum AnimalType
     * @throws IllegalArgumentException если передан неизвестный код типа
     */
    private AnimalType convertToAnimalType(int type) {
        return switch (type) {
            case 1 -> AnimalType.RABBIT;
            case 2 -> AnimalType.MONKEY;
            case 3 -> AnimalType.ELEPHANT;
            case 4 -> AnimalType.GIRAFFE;
            case 5 -> AnimalType.TIGER;
            case 6 -> AnimalType.WOLF;
            case 7 -> AnimalType.LION;
            case 8 -> AnimalType.FOX;
            default -> throw new IllegalArgumentException("Unknown animal type: " + type);
        };
    }

    /**
     * Проверяет, является ли животное травоядным по его типу.
     *
     * @param type числовой код типа животного
     * @return true если животное травоядное, false если хищное
     */
    public boolean isHerbivore(int type) {
        AnimalType animalType = convertToAnimalType(type);
        return animalType.requiresKindnessLevel();
    }
}