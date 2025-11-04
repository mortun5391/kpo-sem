package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.entities.alive.Rabbit;
import hse.moscowzoo.domain.entities.alive.Tiger;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import hse.moscowzoo.dto.AnimalDto;
import hse.moscowzoo.dto.AnimalRegistrationRequest;
import hse.moscowzoo.dto.AnimalRegistrationResult;
import hse.moscowzoo.utils.InputValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalManagementServiceTest {

    @Mock
    private AnimalRegistrationService registrationService;

    @Mock
    private ZooInventoryService inventoryService;

    @Mock
    private InputValidator inputValidator;

    @InjectMocks
    private AnimalManagementService animalManagementService;

    @Test
    void testAddAnimalSuccess() {
        AnimalRegistrationRequest request = new AnimalRegistrationRequest();
        request.setType(1); // RABBIT
        request.setName("Bugs");
        request.setKindnessLevel(KindnessLevel.of(7));

        when(inputValidator.getDoubleInput("Enter ear length (cm): ")).thenReturn(25.0);
        when(inputValidator.getStringInput("Enter fur type: ")).thenReturn("Fluffy");
        when(registrationService.registerAnimal(any(Animal.class))).thenReturn(true);

        AnimalRegistrationResult result = animalManagementService.addAnimal(request);

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("Bugs"));
        assertTrue(result.getMessage().contains("registered"));
    }

    @Test
    void testAddAnimalRegistrationFailed() {
        AnimalRegistrationRequest request = new AnimalRegistrationRequest();
        request.setType(1);
        request.setName("Bugs");
        request.setKindnessLevel(KindnessLevel.of(7)); // Добавляем kindnessLevel

        when(inputValidator.getDoubleInput("Enter ear length (cm): ")).thenReturn(25.0);
        when(inputValidator.getStringInput("Enter fur type: ")).thenReturn("Fluffy");
        when(registrationService.registerAnimal(any(Animal.class))).thenReturn(false);

        AnimalRegistrationResult result = animalManagementService.addAnimal(request);

        assertFalse(result.isSuccess());
    }

    @Test
    void testGetAllAnimals() {
        Rabbit rabbit = new Rabbit(1, "Bugs", KindnessLevel.of(7), 25.0, "Fluffy");
        Tiger tiger = new Tiger(2, "Shere Khan", 120, "Striped");

        List<Animal> animals = List.of(rabbit, tiger);
        when(inventoryService.getAnimals()).thenReturn(animals);

        List<AnimalDto> result = animalManagementService.getAllAnimals();

        assertEquals(2, result.size());
        assertEquals("Bugs", result.get(0).getName());
        assertEquals("Shere Khan", result.get(1).getName());
        assertEquals(1, result.get(0).getInventoryNumber());
        assertEquals(2, result.get(1).getInventoryNumber());
    }

    @Test
    void testGetAnimalByIdFound() {
        Rabbit rabbit = new Rabbit(1, "Test Animal", KindnessLevel.of(5), 20.0, "Short");
        when(inventoryService.getAnimals()).thenReturn(List.of(rabbit));

        AnimalDto result = animalManagementService.getAnimalById(1);

        assertNotNull(result);
        assertEquals("Test Animal", result.getName());
        assertEquals(1, result.getInventoryNumber());
    }

    @Test
    void testGetAnimalByIdNotFound() {
        when(inventoryService.getAnimals()).thenReturn(List.of());

        AnimalDto result = animalManagementService.getAnimalById(999);

        assertNull(result);
    }

    @Test
    void testReadKindnessLevelFromUser() {
        KindnessLevel expected = KindnessLevel.of(8);
        when(inputValidator.readKindnessLevel()).thenReturn(expected);

        KindnessLevel result = animalManagementService.readKindnessLevelFromUser();

        assertEquals(expected, result);
    }

    @Test
    void testIsHerbivore() {
        assertTrue(animalManagementService.isHerbivore(1)); // RABBIT
        assertTrue(animalManagementService.isHerbivore(3)); // ELEPHANT
        assertTrue(animalManagementService.isHerbivore(4)); // GIRAFFE
        assertFalse(animalManagementService.isHerbivore(5)); // TIGER
        assertFalse(animalManagementService.isHerbivore(6)); // WOLF
        assertFalse(animalManagementService.isHerbivore(7)); // LION
        assertFalse(animalManagementService.isHerbivore(8)); // FOX
    }

    @Test
    void testAddAnimalWithInvalidType() {
        AnimalRegistrationRequest request = new AnimalRegistrationRequest();
        request.setType(99); // Invalid type
        request.setName("Test Animal");

        AnimalRegistrationResult result = animalManagementService.addAnimal(request);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Unknown animal type"));
    }

    @Test
    void testAnimalDtoCreation() {
        Rabbit rabbit = new Rabbit(1, "Bugs", KindnessLevel.of(7), 25.0, "Fluffy");
        when(inventoryService.getAnimals()).thenReturn(List.of(rabbit));

        List<AnimalDto> result = animalManagementService.getAllAnimals();
        AnimalDto dto = result.getFirst();

        assertNotNull(dto);
        assertEquals("Bugs", dto.getName());
        assertEquals(1, dto.getInventoryNumber());
        assertNotNull(dto.getAnimalType());
        assertNotNull(dto.getHealthStatus());
        assertNotNull(dto.getKindnessLevel());
    }

    @Test
    void testAddAnimalRegistrationFailedAlternative() {
        AnimalRegistrationRequest request = new AnimalRegistrationRequest();
        request.setType(1);
        request.setName("Bugs");
        request.setKindnessLevel(KindnessLevel.of(7));

        doReturn(25.0).when(inputValidator).getDoubleInput("Enter ear length (cm): ");
        doReturn("Fluffy").when(inputValidator).getStringInput("Enter fur type: ");
        doReturn(false).when(registrationService).registerAnimal(any(Animal.class));

        AnimalRegistrationResult result = animalManagementService.addAnimal(request);

        assertFalse(result.isSuccess());
    }
}