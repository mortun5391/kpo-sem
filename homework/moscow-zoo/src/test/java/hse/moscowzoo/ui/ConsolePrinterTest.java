package hse.moscowzoo.ui;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.entities.things.Computer;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.domain.valueobjects.HealthStatus;
import hse.moscowzoo.dto.AnimalDto;
import hse.moscowzoo.dto.AnimalRegistrationResult;
import hse.moscowzoo.dto.ThingDto;
import hse.moscowzoo.dto.ThingRegistrationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsolePrinterTest {

    private ConsolePrinter consolePrinter;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        consolePrinter = new ConsolePrinter();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String getOutput() {
        return outputStream.toString();
    }

    @Test
    void testPrintMainMenu() {
        consolePrinter.printMainMenu();

        String output = getOutput();
        assertTrue(output.contains("Moscow Zoo Management"));
        assertTrue(output.contains("1. Add animal"));
        assertTrue(output.contains("2. Show all animals"));
        assertTrue(output.contains("3. Calculate food"));
        assertTrue(output.contains("4. Contact zoo info"));
        assertTrue(output.contains("5. Add thing"));
        assertTrue(output.contains("6. Show all things"));
        assertTrue(output.contains("7. Show all inventory"));
        assertTrue(output.contains("0. Exit"));
    }

    @Test
    void testPrintAnimalTypesMenu() {
        consolePrinter.printAnimalTypesMenu();

        String output = getOutput();
        assertTrue(output.contains("Select animal type:"));
        assertTrue(output.contains("1. Rabbit"));
        assertTrue(output.contains("2. Monkey"));
        assertTrue(output.contains("3. Elephant"));
        assertTrue(output.contains("4. Giraffe"));
        assertTrue(output.contains("5. Tiger"));
        assertTrue(output.contains("6. Wolf"));
        assertTrue(output.contains("7. Lion"));
        assertTrue(output.contains("8. Fox"));
    }

    @Test
    void testPrintAnimalsEmpty() {
        consolePrinter.printAnimals(List.of());

        String output = getOutput();
        assertTrue(output.contains("No animals in zoo."));
    }

    @Test
    void testPrintAnimalsWithData() {
        AnimalDto animal1 = new AnimalDto("Bugs", 1, "Rabbit (Ears: 25.0cm, Fur: Fluffy)",
                HealthStatus.HEALTHY.toString(), "7/10");
        AnimalDto animal2 = new AnimalDto("Shere Khan", 2, "Tiger (Stripes: 120, Pattern: Striped)",
                HealthStatus.HEALTHY.toString(), null);

        consolePrinter.printAnimals(List.of(animal1, animal2));

        String output = getOutput();
        assertTrue(output.contains("Animals in Zoo"));
        assertTrue(output.contains("Name: Bugs, Inventory#: 1, " +
                "Type: Rabbit (Ears: 25.0cm, Fur: Fluffy), Health: HEALTHY, Kindness: 7/10"));
        assertTrue(output.contains("Name: Shere Khan, Inventory#: 2, " +
                "Type: Tiger (Stripes: 120, Pattern: Striped), Health: HEALTHY"));
    }

    @Test
    void testPrintAnimal() {
        AnimalDto animal = new AnimalDto("Test Animal", 1, "Rabbit", HealthStatus.HEALTHY.toString(), "5/10");

        consolePrinter.printAnimal(animal);

        String output = getOutput();
        assertTrue(output.contains("Name: Test Animal, Inventory#: 1, Type: Rabbit, Health: HEALTHY, Kindness: 5/10"));
    }

    @Test
    void testPrintAnimalWithoutKindness() {
        AnimalDto animal = new AnimalDto("Test Animal", 1, "Tiger", HealthStatus.HEALTHY.toString(), null);

        consolePrinter.printAnimal(animal);

        String output = getOutput();
        assertTrue(output.contains("Name: Test Animal, Inventory#: 1, Type: Tiger, Health: HEALTHY"));
        assertFalse(output.contains("Kindness:"));
    }

    @Test
    void testPrintRegistrationResultSuccess() {
        AnimalRegistrationResult result = new AnimalRegistrationResult(true, "Animal registered successfully", 1);

        consolePrinter.printRegistrationResult(result);

        String output = getOutput();
        assertTrue(output.contains("SUCCESS: Animal registered successfully"));
    }

    @Test
    void testPrintRegistrationResultFailed() {
        AnimalRegistrationResult result = new AnimalRegistrationResult(false, "Registration failed");

        consolePrinter.printRegistrationResult(result);

        String output = getOutput();
        assertTrue(output.contains("FAILED: Registration failed"));
    }

    @Test
    void testPrintFoodCalculation() {
        consolePrinter.printFoodCalculation(450, 3);

        String output = getOutput();
        assertTrue(output.contains("Food Calculation"));
        assertTrue(output.contains("Total animals: 3"));
        assertTrue(output.contains("Total food consumption: 450 kg per day"));
    }

    @Test
    void testPrintContactZooAnimalsEmpty() {
        consolePrinter.printContactZooAnimals(List.of());

        String output = getOutput();
        assertTrue(output.contains("No animals suitable for contact zoo."));
    }

    @Test
    void testPrintContactZooAnimalsWithData() {
        Animal animal1 = mock(Animal.class);
        when(animal1.getName()).thenReturn("Bugs");
        when(animal1.getInventoryNumber()).thenReturn(1);

        Animal animal2 = mock(Animal.class);
        when(animal2.getName()).thenReturn("George");
        when(animal2.getInventoryNumber()).thenReturn(2);

        consolePrinter.printContactZooAnimals(List.of(animal1, animal2));

        String output = getOutput();
        assertTrue(output.contains("Contact Zoo Animals"));
        assertTrue(output.contains("Name: Bugs, Inventory#: 1"));
        assertTrue(output.contains("Name: George, Inventory#: 2"));
    }

    @Test
    void testPrintAllInventoryEmpty() {
        consolePrinter.printAllInventory(List.of());

        String output = getOutput();
        assertTrue(output.contains("No inventory items."));
    }

    @Test
    void testPrintAllInventoryWithMixedItems() {
        Animal animal = mock(Animal.class);
        when(animal.getName()).thenReturn("Bugs");
        when(animal.getInventoryNumber()).thenReturn(1);

        Thing thing = new Computer(2, "Office PC");

        consolePrinter.printAllInventory(List.of(animal, thing));

        String output = getOutput();
        assertTrue(output.contains("All Inventory"));
        assertTrue(output.contains("ANIMAL: Name: Bugs, Inventory#: 1"));
        assertTrue(output.contains("THING: Name: Office PC, Inventory#: 2, Type: Computer"));
    }

    @Test
    void testPrintThingTypesMenu() {
        consolePrinter.printThingTypesMenu();

        String output = getOutput();
        assertTrue(output.contains("Select thing type:"));
        assertTrue(output.contains("1. Computer"));
        assertTrue(output.contains("2. Table"));
        assertTrue(output.contains("3. Cage"));
        assertTrue(output.contains("4. Feeding Bowl"));
    }

    @Test
    void testPrintThingsEmpty() {
        consolePrinter.printThings(List.of());

        String output = getOutput();
        assertTrue(output.contains("No things in inventory."));
    }

    @Test
    void testPrintThingsWithData() {
        ThingDto thing1 = new ThingDto("Office PC", 1, "Computer");
        ThingDto thing2 = new ThingDto("Bird Cage", 2, "Cage (Area: 15.5 m^2, Material: Steel)");

        consolePrinter.printThings(List.of(thing1, thing2));

        String output = getOutput();
        assertTrue(output.contains("Things in Inventory"));
        assertTrue(output.contains("Name: Office PC, Inventory#: 1, Type: Computer"));
        assertTrue(output.contains("Name: Bird Cage, Inventory#: 2, Type: Cage (Area: 15.5 m^2, Material: Steel)"));
    }

    @Test
    void testPrintThingRegistrationResultSuccess() {
        ThingRegistrationResult result = new ThingRegistrationResult(true, "Thing registered successfully", 1);

        consolePrinter.printThingRegistrationResult(result);

        String output = getOutput();
        assertTrue(output.contains("SUCCESS: Thing registered successfully"));
    }

    @Test
    void testPrintThingRegistrationResultFailed() {
        ThingRegistrationResult result = new ThingRegistrationResult(false, "Registration failed");

        consolePrinter.printThingRegistrationResult(result);

        String output = getOutput();
        assertTrue(output.contains("FAILED: Registration failed"));
    }

    @Test
    void testPrintError() {
        consolePrinter.printError("Something went wrong");

        String output = getOutput();
        assertTrue(output.contains("Error: Something went wrong"));
    }

    @Test
    void testPrintInfo() {
        consolePrinter.printInfo("Operation completed");

        String output = getOutput();
        assertTrue(output.contains("Info: Operation completed"));
    }
}