package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.things.Cage;
import hse.moscowzoo.domain.entities.things.Computer;
import hse.moscowzoo.domain.entities.things.FeedingBowl;
import hse.moscowzoo.domain.entities.things.Table;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.domain.valueobjects.ThingType;
import hse.moscowzoo.dto.ThingDto;
import hse.moscowzoo.dto.ThingRegistrationRequest;
import hse.moscowzoo.dto.ThingRegistrationResult;
import hse.moscowzoo.utils.InputValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThingManagementServiceTest {

    @Mock
    private ThingRegistrationService thingRegistrationService;

    @Mock
    private ZooInventoryService inventoryService;

    @Mock
    private InputValidator inputValidator;

    @InjectMocks
    private ThingManagementService thingManagementService;

    @Test
    void testAddComputerSuccess() {
        ThingRegistrationRequest request = new ThingRegistrationRequest();
        request.setType(1);
        request.setName("Office PC");

        when(thingRegistrationService.registerThing(any(Thing.class))).thenReturn(true);

        ThingRegistrationResult result = thingManagementService.addThing(request);

        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().contains("Office PC"));
    }

    @Test
    void testAddCageSuccess() {
        ThingRegistrationRequest request = new ThingRegistrationRequest();
        request.setType(3);
        request.setName("Bird Cage");

        when(inputValidator.getDoubleInput("Enter cage area (m^2): ")).thenReturn(15.5);
        when(inputValidator.getStringInput("Enter cage material: ")).thenReturn("Steel");
        when(thingRegistrationService.registerThing(any(Cage.class))).thenReturn(true);

        ThingRegistrationResult result = thingManagementService.addThing(request);

        assertTrue(result.isSuccess());
        verify(inputValidator).getDoubleInput("Enter cage area (m^2): ");
        verify(inputValidator).getStringInput("Enter cage material: ");
    }

    @Test
    void testAddFeedingBowlSuccess() {
        ThingRegistrationRequest request = new ThingRegistrationRequest();
        request.setType(4);
        request.setName("Auto Feeder");

        when(inputValidator.getDoubleInput("Enter capacity (liters): ")).thenReturn(2.5);
        when(inputValidator.getYesNoInput("Is automatic?")).thenReturn(true);
        when(thingRegistrationService.registerThing(any(FeedingBowl.class))).thenReturn(true);

        ThingRegistrationResult result = thingManagementService.addThing(request);

        assertTrue(result.isSuccess());
        verify(inputValidator).getDoubleInput("Enter capacity (liters): ");
        verify(inputValidator).getYesNoInput("Is automatic?");
    }

    @Test
    void testAddThingWithInvalidType() {
        ThingRegistrationRequest request = new ThingRegistrationRequest();
        request.setType(99);
        request.setName("Test Thing");

        ThingRegistrationResult result = thingManagementService.addThing(request);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Unknown thing type"));
    }

    @Test
    void testGetAllThings() {
        Computer computer = new Computer(1, "Office PC");
        Cage cage = new Cage(2, "Bird Cage", 10.0, "Steel");
        List<Thing> things = List.of(computer, cage);

        when(inventoryService.getThings()).thenReturn(things);

        List<ThingDto> result = thingManagementService.getAllThings();

        assertEquals(2, result.size());
        assertEquals("Office PC", result.get(0).getName());
        assertEquals("Bird Cage", result.get(1).getName());
        assertEquals("Computer", result.get(0).getThingType());
    }

    @Test
    void testConvertToDtoForCage() {
        Cage cage = new Cage(1, "Test Cage", 15.5, "Wood");
        ThingDto dto = thingManagementService.convertToDto(cage);

        assertEquals("Test Cage", dto.getName());
        assertEquals(1, dto.getInventoryNumber());
        assertEquals("Cage (Area: 15,5 m^2, Material: Wood)", dto.getThingType());
    }

    @Test
    void testConvertToDtoForFeedingBowl() {
        FeedingBowl bowl = new FeedingBowl(2, "Auto Feeder", 2.5, true);
        ThingDto dto = thingManagementService.convertToDto(bowl);

        assertEquals("Auto Feeder", dto.getName());
        assertEquals(2, dto.getInventoryNumber());
        assertEquals("FeedingBowl (Capacity: 2,5L, Type: Automatic)", dto.getThingType());
    }

    @Test
    void testConvertToDtoForComputer() {
        Computer computer = new Computer(3, "Gaming PC");
        ThingDto dto = thingManagementService.convertToDto(computer);

        assertEquals("Gaming PC", dto.getName());
        assertEquals(3, dto.getInventoryNumber());
        assertEquals("Computer", dto.getThingType());
    }

    @Test
    void testConvertToDtoForTable() {
        Table table = new Table(4, "Dining Table");
        ThingDto dto = thingManagementService.convertToDto(table);

        assertEquals("Dining Table", dto.getName());
        assertEquals(4, dto.getInventoryNumber());
        assertEquals("Table", dto.getThingType());
    }

    @Test
    void testConvertToThingType() {
        assertEquals(ThingType.COMPUTER, thingManagementService.convertToThingType(1));
        assertEquals(ThingType.TABLE, thingManagementService.convertToThingType(2));
        assertEquals(ThingType.CAGE, thingManagementService.convertToThingType(3));
        assertEquals(ThingType.FEEDING_BOWL, thingManagementService.convertToThingType(4));
    }

    @Test
    void testConvertToThingTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            thingManagementService.convertToThingType(99);
        });
    }
}