package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.things.Computer;
import hse.moscowzoo.domain.entities.things.Cage;
import hse.moscowzoo.domain.entities.things.FeedingBowl;
import hse.moscowzoo.domain.entities.things.Table;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.repositories.ThingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ThingRegistrationServiceTest {

    @Mock
    private ThingsRepository thingsRepository;

    @InjectMocks
    private ThingRegistrationService thingRegistrationService;

    @Test
    void testRegisterComputer() {
        Computer computer = new Computer(1, "Office PC");

        boolean result = thingRegistrationService.registerThing(computer);

        assertTrue(result);
        verify(thingsRepository).save(computer);
    }

    @Test
    void testRegisterCage() {
        Cage cage = new Cage(2, "Bird Cage", 15.5, "Steel");

        boolean result = thingRegistrationService.registerThing(cage);

        assertTrue(result);
        verify(thingsRepository).save(cage);
    }

    @Test
    void testRegisterFeedingBowl() {
        FeedingBowl bowl = new FeedingBowl(3, "Auto Feeder", 2.5, true);

        boolean result = thingRegistrationService.registerThing(bowl);

        assertTrue(result);
        verify(thingsRepository).save(bowl);
    }

    @Test
    void testRegisterTable() {
        Table table = new Table(4, "Dining Table");

        boolean result = thingRegistrationService.registerThing(table);

        assertTrue(result);
        verify(thingsRepository).save(table);
    }

    @Test
    void testRegisterMultipleThings() {
        Computer computer = new Computer(1, "PC1");
        Cage cage = new Cage(2, "Cage1", 10.0, "Wood");
        FeedingBowl bowl = new FeedingBowl(3, "Bowl1", 1.5, false);

        boolean result1 = thingRegistrationService.registerThing(computer);
        boolean result2 = thingRegistrationService.registerThing(cage);
        boolean result3 = thingRegistrationService.registerThing(bowl);

        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        verify(thingsRepository, times(3)).save(any(Thing.class));
    }

    @Test
    void testRegisterThingAlwaysReturnsTrue() {
        Thing thing = new Computer(1, "Test PC");
        boolean result = thingRegistrationService.registerThing(thing);
        assertTrue(result);
    }
}