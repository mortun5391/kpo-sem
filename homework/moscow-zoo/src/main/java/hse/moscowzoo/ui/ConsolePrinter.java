package hse.moscowzoo.ui;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.domain.interfaces.IInventory;
import hse.moscowzoo.dto.AnimalDto;
import hse.moscowzoo.dto.AnimalRegistrationResult;
import hse.moscowzoo.dto.ThingDto;
import hse.moscowzoo.dto.ThingRegistrationResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Компонент для вывода информации в консоль.
 * Обеспечивает форматированный вывод меню, данных и результатов операций.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Component
public class ConsolePrinter {

    /**
     * Выводит главное меню управления зоопарком.
     */
    public void printMainMenu() {
        System.out.println("\n=== Moscow Zoo Management ===");
        System.out.println("1. Add animal");
        System.out.println("2. Show all animals");
        System.out.println("3. Calculate food");
        System.out.println("4. Contact zoo info");
        System.out.println("5. Add thing");
        System.out.println("6. Show all things");
        System.out.println("7. Show all inventory");
        System.out.println("0. Exit");
    }

    /**
     * Выводит меню выбора типа животного.
     */
    public void printAnimalTypesMenu() {
        System.out.println("\nSelect animal type:");
        System.out.println("1. Rabbit");
        System.out.println("2. Monkey");
        System.out.println("3. Elephant");
        System.out.println("4. Giraffe");
        System.out.println("5. Tiger");
        System.out.println("6. Wolf");
        System.out.println("7. Lion");
        System.out.println("8. Fox");
    }

    /**
     * Выводит список всех животных.
     *
     * @param animals список DTO объектов животных
     */
    public void printAnimals(List<AnimalDto> animals) {
        if (animals.isEmpty()) {
            System.out.println("\nNo animals in zoo.");
            return;
        }

        System.out.println("\n=== Animals in Zoo ===");
        for (AnimalDto animal : animals) {
            printAnimal(animal);
        }
    }

    /**
     * Выводит информацию об одном животном.
     *
     * @param animal DTO объект животного
     */
    public void printAnimal(AnimalDto animal) {
        System.out.printf("Name: %s, Inventory#: %d, Type: %s, Health: %s",
                animal.getName(), animal.getInventoryNumber(),
                animal.getAnimalType(), animal.getHealthStatus());

        if (animal.getKindnessLevel() != null) {
            System.out.printf(", Kindness: %s", animal.getKindnessLevel());
        }
        System.out.println();
    }

    /**
     * Выводит результат регистрации животного.
     *
     * @param result результат регистрации животного
     */
    public void printRegistrationResult(AnimalRegistrationResult result) {
        if (result.isSuccess()) {
            System.out.println("SUCCESS: " + result.getMessage());
        } else {
            System.out.println("FAILED: " + result.getMessage());
        }
    }

    /**
     * Выводит расчет потребления пищи.
     *
     * @param totalFood общее потребление пищи в кг/день
     * @param animalCount количество животных
     */
    public void printFoodCalculation(int totalFood, int animalCount) {
        System.out.println("\n=== Food Calculation ===");
        System.out.println("Total animals: " + animalCount);
        System.out.println("Total food consumption: " + totalFood + " kg per day");
    }

    /**
     * Выводит список животных для контактного зоопарка.
     *
     * @param animals список животных для контактного зоопарка
     */
    public void printContactZooAnimals(List<Animal> animals) {
        if (animals.isEmpty()) {
            System.out.println("\nNo animals suitable for contact zoo.");
            return;
        }

        System.out.println("\n=== Contact Zoo Animals ===");
        for (Animal animal : animals) {
            System.out.printf("Name: %s, Inventory#: %d, Type: %s%n",
                    animal.getName(), animal.getInventoryNumber(),
                    animal.getClass().getSimpleName());
        }
    }

    /**
     * Выводит полный инвентарь зоопарка.
     *
     * @param inventory список всех объектов инвентаря
     */
    public void printAllInventory(List<IInventory> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("\nNo inventory items.");
            return;
        }

        System.out.println("\n=== All Inventory ===");
        for (IInventory item : inventory) {
            if (item instanceof Animal animal) {
                System.out.printf("ANIMAL: Name: %s, Inventory#: %d, Type: %s%n",
                        animal.getName(), animal.getInventoryNumber(), animal.getClass().getSimpleName());
            } else if (item instanceof Thing thing) {
                System.out.printf("THING: Name: %s, Inventory#: %d, Type: %s%n",
                        thing.getName(), thing.getInventoryNumber(), thing.getClass().getSimpleName());
            }
        }
    }

    /**
     * Выводит меню выбора типа предмета.
     */
    public void printThingTypesMenu() {
        System.out.println("\nSelect thing type:");
        System.out.println("1. Computer");
        System.out.println("2. Table");
        System.out.println("3. Cage");
        System.out.println("4. Feeding Bowl");
    }

    /**
     * Выводит список всех предметов.
     *
     * @param things список DTO объектов предметов
     */
    public void printThings(List<ThingDto> things) {
        if (things.isEmpty()) {
            System.out.println("\nNo things in inventory.");
            return;
        }

        System.out.println("\n=== Things in Inventory ===");
        for (ThingDto thing : things) {
            System.out.printf("Name: %s, Inventory#: %d, Type: %s%n",
                    thing.getName(), thing.getInventoryNumber(), thing.getThingType());
        }
    }

    /**
     * Выводит результат регистрации предмета.
     *
     * @param result результат регистрации предмета
     */
    public void printThingRegistrationResult(ThingRegistrationResult result) {
        if (result.isSuccess()) {
            System.out.println("SUCCESS: " + result.getMessage());
        } else {
            System.out.println("FAILED: " + result.getMessage());
        }
    }

    /**
     * Выводит сообщение об ошибке.
     *
     * @param message текст сообщения об ошибке
     */
    public void printError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Выводит информационное сообщение.
     *
     * @param message текст информационного сообщения
     */
    public void printInfo(String message) {
        System.out.println("Info: " + message);
    }
}