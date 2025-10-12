package hse.moscowzoo.ui;

import hse.moscowzoo.dto.AnimalRegistrationRequest;
import hse.moscowzoo.dto.ThingRegistrationRequest;
import hse.moscowzoo.services.AnimalManagementService;
import hse.moscowzoo.services.ContactZooService;
import hse.moscowzoo.services.FoodCalculationService;
import hse.moscowzoo.services.ThingManagementService;
import hse.moscowzoo.services.ZooInventoryService;
import hse.moscowzoo.utils.InputValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @Mock
    private ConsolePrinter printer;

    @Mock
    private InputValidator inputValidator;

    @Mock
    private AnimalManagementService animalManagementService;

    @Mock
    private ThingManagementService thingManagementService;

    @Mock
    private FoodCalculationService foodCalculationService;

    @Mock
    private ContactZooService contactZooService;

    @Mock
    private ZooInventoryService zooInventoryService;

    private ConsoleMenu consoleMenu;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        consoleMenu = new ConsoleMenu(printer, inputValidator, animalManagementService,
                thingManagementService, foodCalculationService,
                contactZooService, zooInventoryService);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testAddAnimalSuccess() {
        when(inputValidator.getStringInput("Animal's name: ")).thenReturn("Bugs");
        when(inputValidator.getIntInput("Choose type: ")).thenReturn(1);
        when(animalManagementService.isHerbivore(1)).thenReturn(true);
        when(animalManagementService.readKindnessLevelFromUser())
                .thenReturn(mock(hse.moscowzoo.domain.valueobjects.KindnessLevel.class));
        when(animalManagementService.addAnimal(any(AnimalRegistrationRequest.class)))
                .thenReturn(mock(hse.moscowzoo.dto.AnimalRegistrationResult.class));

        consoleMenu.addAnimal();

        verify(inputValidator).getStringInput("Animal's name: ");
        verify(inputValidator).getIntInput("Choose type: ");
        verify(animalManagementService).isHerbivore(1);
        verify(animalManagementService).readKindnessLevelFromUser();
        verify(animalManagementService).addAnimal(any(AnimalRegistrationRequest.class));
        verify(printer).printRegistrationResult(any());
    }

    @Test
    void testAddAnimalCarnivore() {
        when(inputValidator.getStringInput("Animal's name: ")).thenReturn("Tiger");
        when(inputValidator.getIntInput("Choose type: ")).thenReturn(5);
        when(animalManagementService.isHerbivore(5)).thenReturn(false);
        when(animalManagementService.addAnimal(any(AnimalRegistrationRequest.class)))
                .thenReturn(mock(hse.moscowzoo.dto.AnimalRegistrationResult.class));

        consoleMenu.addAnimal();

        verify(inputValidator).getStringInput("Animal's name: ");
        verify(inputValidator).getIntInput("Choose type: ");
        verify(animalManagementService).isHerbivore(5);
        verify(animalManagementService, never()).readKindnessLevelFromUser();
        verify(animalManagementService).addAnimal(any(AnimalRegistrationRequest.class));
    }

    @Test
    void testAddAnimalException() {
        when(inputValidator.getStringInput("Animal's name: ")).thenReturn("Bugs");
        when(inputValidator.getIntInput("Choose type: ")).thenReturn(1);
        when(animalManagementService.isHerbivore(1)).thenReturn(true);
        when(animalManagementService.readKindnessLevelFromUser()).thenThrow(new RuntimeException("Test error"));

        consoleMenu.addAnimal();

        String output = outputStream.toString();
        assertTrue(output.contains("Error when adding an animal: Test error"));
    }

    @Test
    void testShowAllAnimals() {
        when(animalManagementService.getAllAnimals()).thenReturn(List.of());

        consoleMenu.showAllAnimals();

        verify(animalManagementService).getAllAnimals();
        verify(printer).printAnimals(any());
    }

    @Test
    void testShowAllAnimalsException() {
        when(animalManagementService.getAllAnimals()).thenThrow(new RuntimeException("Database error"));

        consoleMenu.showAllAnimals();

        String output = outputStream.toString();
        assertTrue(output.contains("Error when showing animals: Database error"));
    }

    @Test
    void testCalculateTotalFood() {
        when(foodCalculationService.calculateTotalFoodConsumption()).thenReturn(450);
        when(foodCalculationService.getAnimalCount()).thenReturn(3);

        consoleMenu.calculateTotalFood();

        verify(foodCalculationService).calculateTotalFoodConsumption();
        verify(foodCalculationService).getAnimalCount();
        verify(printer).printFoodCalculation(450, 3);
    }

    @Test
    void testCalculateTotalFoodException() {
        when(foodCalculationService.calculateTotalFoodConsumption())
                .thenThrow(new RuntimeException("Calculation error"));

        consoleMenu.calculateTotalFood();

        String output = outputStream.toString();
        assertTrue(output.contains("Error calculating food: Calculation error"));
    }

    @Test
    void testShowContactZooAnimals() {
        when(contactZooService.getContactZooAnimals()).thenReturn(List.of());

        consoleMenu.showContactZooAnimals();

        verify(contactZooService).getContactZooAnimals();
        verify(printer).printContactZooAnimals(any());
    }

    @Test
    void testShowContactZooAnimalsException() {
        when(contactZooService.getContactZooAnimals()).thenThrow(new RuntimeException("Service error"));

        consoleMenu.showContactZooAnimals();

        String output = outputStream.toString();
        assertTrue(output.contains("Error getting contact zoo animals: Service error"));
    }

    @Test
    void testAddThing() {
        when(inputValidator.getStringInput("Thing's name: ")).thenReturn("Office PC");
        when(inputValidator.getIntInput("Choose type: ")).thenReturn(1);
        when(thingManagementService.addThing(any(ThingRegistrationRequest.class)))
                .thenReturn(mock(hse.moscowzoo.dto.ThingRegistrationResult.class));

        consoleMenu.addThing();

        verify(inputValidator).getStringInput("Thing's name: ");
        verify(inputValidator).getIntInput("Choose type: ");
        verify(thingManagementService).addThing(any(ThingRegistrationRequest.class));
        verify(printer).printThingRegistrationResult(any());
    }

    @Test
    void testAddThingException() {
        when(inputValidator.getStringInput("Thing's name: ")).thenThrow(new RuntimeException("Input error"));

        consoleMenu.addThing();

        String output = outputStream.toString();
        assertTrue(output.contains("Error when adding a thing: Input error"));
    }

    @Test
    void testShowAllThings() {
        when(thingManagementService.getAllThings()).thenReturn(List.of());

        consoleMenu.showAllThings();

        verify(thingManagementService).getAllThings();
        verify(printer).printThings(any());
    }

    @Test
    void testShowAllThingsException() {
        when(thingManagementService.getAllThings()).thenThrow(new RuntimeException("Service error"));

        consoleMenu.showAllThings();

        String output = outputStream.toString();
        assertTrue(output.contains("Error when showing things: Service error"));
    }

    @Test
    void testShowAllInventory() {
        when(zooInventoryService.getAllInventory()).thenReturn(List.of());

        consoleMenu.showAllInventory();

        verify(zooInventoryService).getAllInventory();
        verify(printer).printAllInventory(any());
    }

    @Test
    void testShowAllInventoryException() {
        when(zooInventoryService.getAllInventory()).thenThrow(new RuntimeException("Inventory error"));

        consoleMenu.showAllInventory();

        String output = outputStream.toString();
        assertTrue(output.contains("Error getting inventory: Inventory error"));
    }

    @Test
    void testShowMainMenuExit() {
        // Симулируем ввод "0" для выхода
        String input = "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(inputValidator.getIntInput("Choose item: ")).thenReturn(0);

        consoleMenu.showMainMenu();

        verify(printer).printMainMenu();
        verify(inputValidator).getIntInput("Choose item: ");
    }

    @Test
    void testShowMainMenuInvalidChoice() {
        String input = "99\n0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(inputValidator.getIntInput("Choose item: "))
                .thenReturn(99)
                .thenReturn(0);

        consoleMenu.showMainMenu();

        verify(printer, times(2)).printMainMenu();
        verify(inputValidator, times(2)).getIntInput("Choose item: ");

        String output = outputStream.toString();
        assertTrue(output.contains("Wrong menu item!"));
    }
}