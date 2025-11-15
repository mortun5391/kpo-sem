package hse.moscowzoo.ui;

import hse.moscowzoo.dto.AnimalRegistrationRequest;
import hse.moscowzoo.dto.ThingRegistrationRequest;
import hse.moscowzoo.services.AnimalManagementService;
import hse.moscowzoo.services.ContactZooService;
import hse.moscowzoo.services.FoodCalculationService;
import hse.moscowzoo.services.ThingManagementService;
import hse.moscowzoo.services.ZooInventoryService;
import hse.moscowzoo.utils.InputValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Консольное меню для управления зоопарком.
 * Предоставляет пользовательский интерфейс для взаимодействия с системой зоопарка.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Component
@RequiredArgsConstructor
public class ConsoleMenu {
    private final ConsolePrinter printer;
    private final InputValidator inputValidator;
    private final AnimalManagementService animalManagementService;
    private final ThingManagementService thingManagementService;
    private final FoodCalculationService foodCalculationService;
    private final ContactZooService contactZooService;
    private final ZooInventoryService zooInventoryService;

    /**
     * Отображает главное меню и обрабатывает выбор пользователя.
     * Работает в цикле до выбора пункта "Выход".
     */
    public void showMainMenu() {
        while (true) {
            printer.printMainMenu();
            int choice = inputValidator.getIntInput("Choose item: ");

            switch (choice) {
                case 1 -> addAnimal();
                case 2 -> showAllAnimals();
                case 3 -> calculateTotalFood();
                case 4 -> showContactZooAnimals();
                case 5 -> addThing();
                case 6 -> showAllThings();
                case 7 -> showAllInventory();
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Wrong menu item!");
            }
        }
    }

    /**
     * Добавляет новое животное в систему.
     * Запрашивает у пользователя данные о животном и отправляет запрос на регистрацию.
     */
    void addAnimal() {
        System.out.println("\n--- Adding animal ---");

        try {
            var request = new AnimalRegistrationRequest();
            request.setName(inputValidator.getStringInput("Animal's name: "));

            printer.printAnimalTypesMenu();
            request.setType(inputValidator.getIntInput("Choose type: "));

            if (animalManagementService.isHerbivore(request.getType())) {
                request.setKindnessLevel(animalManagementService.readKindnessLevelFromUser());
            }

            var result = animalManagementService.addAnimal(request);
            printer.printRegistrationResult(result);

        } catch (Exception e) {
            System.out.println("Error when adding an animal: " + e.getMessage());
        }
    }

    /**
     * Отображает список всех животных в зоопарке.
     */
    void showAllAnimals() {
        try {
            var animals = animalManagementService.getAllAnimals();
            printer.printAnimals(animals);
        } catch (Exception e) {
            System.out.println("Error when showing animals: " + e.getMessage());
        }
    }

    /**
     * Рассчитывает и отображает общее потребление пищи животными.
     */
    void calculateTotalFood() {
        try {
            int totalFood = foodCalculationService.calculateTotalFoodConsumption();
            int animalCount = foodCalculationService.getAnimalCount();
            printer.printFoodCalculation(totalFood, animalCount);
        } catch (Exception e) {
            System.out.println("Error calculating food: " + e.getMessage());
        }
    }

    /**
     * Отображает животных, подходящих для контактного зоопарка.
     */
    void showContactZooAnimals() {
        try {
            var contactAnimals = contactZooService.getContactZooAnimals();
            printer.printContactZooAnimals(contactAnimals);
        } catch (Exception e) {
            System.out.println("Error getting contact zoo animals: " + e.getMessage());
        }
    }

    /**
     * Добавляет новый предмет в систему инвентаря.
     * Запрашивает у пользователя данные о предмете и отправляет запрос на регистрацию.
     */
    void addThing() {
        System.out.println("\n--- Adding thing ---");

        try {
            var request = new ThingRegistrationRequest();
            request.setName(inputValidator.getStringInput("Thing's name: "));

            printer.printThingTypesMenu();
            request.setType(inputValidator.getIntInput("Choose type: "));

            var result = thingManagementService.addThing(request);
            printer.printThingRegistrationResult(result);

        } catch (Exception e) {
            System.out.println("Error when adding a thing: " + e.getMessage());
        }
    }

    /**
     * Отображает список всех предметов в инвентаре.
     */
    void showAllThings() {
        try {
            var things = thingManagementService.getAllThings();
            printer.printThings(things);
        } catch (Exception e) {
            System.out.println("Error when showing things: " + e.getMessage());
        }
    }

    /**
     * Отображает полный инвентарь зоопарка (животные и предметы).
     */
    void showAllInventory() {
        try {
            var allInventory = zooInventoryService.getAllInventory();
            printer.printAllInventory(allInventory);
        } catch (Exception e) {
            System.out.println("Error getting inventory: " + e.getMessage());
        }
    }
}