package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.entities.things.Computer;
import hse.moscowzoo.domain.entities.things.Cage;
import hse.moscowzoo.domain.entities.things.Thing;
import hse.moscowzoo.domain.interfaces.IInventory;
import hse.moscowzoo.repositories.AnimalRepository;
import hse.moscowzoo.repositories.ThingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZooInventoryServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private ThingsRepository thingsRepository;

    @InjectMocks
    private ZooInventoryService zooInventoryService;

    @Test
    void testGetAllInventoryWithAnimalsAndThings() {
        Animal animal = mock(Animal.class);
        Computer computer = new Computer(1, "Office PC");
        Cage cage = new Cage(2, "Bird Cage", 10.0, "Steel");

        when(animalRepository.findAll()).thenReturn(List.of(animal));
        when(thingsRepository.findAll()).thenReturn(List.of(computer, cage));

        List<IInventory> result = zooInventoryService.getAllInventory();

        assertEquals(3, result.size());
        assertTrue(result.contains(animal));
        assertTrue(result.contains(computer));
        assertTrue(result.contains(cage));
        verify(animalRepository).findAll();
        verify(thingsRepository).findAll();
    }

    @Test
    void testGetAllInventoryOnlyAnimals() {
        Animal animal1 = mock(Animal.class);
        Animal animal2 = mock(Animal.class);

        when(animalRepository.findAll()).thenReturn(List.of(animal1, animal2));
        when(thingsRepository.findAll()).thenReturn(List.of());

        List<IInventory> result = zooInventoryService.getAllInventory();

        assertEquals(2, result.size());
        assertTrue(result.contains(animal1));
        assertTrue(result.contains(animal2));
        verify(animalRepository).findAll();
        verify(thingsRepository).findAll();
    }

    @Test
    void testGetAllInventoryOnlyThings() {
        Computer computer = new Computer(1, "PC");
        Cage cage = new Cage(2, "Cage", 15.0, "Wood");

        when(animalRepository.findAll()).thenReturn(List.of());
        when(thingsRepository.findAll()).thenReturn(List.of(computer, cage));

        List<IInventory> result = zooInventoryService.getAllInventory();

        assertEquals(2, result.size());
        assertTrue(result.contains(computer));
        assertTrue(result.contains(cage));
        verify(animalRepository).findAll();
        verify(thingsRepository).findAll();
    }

    @Test
    void testGetAllInventoryEmpty() {
        when(animalRepository.findAll()).thenReturn(List.of());
        when(thingsRepository.findAll()).thenReturn(List.of());

        List<IInventory> result = zooInventoryService.getAllInventory();

        assertTrue(result.isEmpty());
        verify(animalRepository).findAll();
        verify(thingsRepository).findAll();
    }

    @Test
    void testGetAnimals() {
        Animal animal1 = mock(Animal.class);
        Animal animal2 = mock(Animal.class);
        when(animalRepository.findAll()).thenReturn(List.of(animal1, animal2));

        List<Animal> result = zooInventoryService.getAnimals();

        assertEquals(2, result.size());
        assertTrue(result.contains(animal1));
        assertTrue(result.contains(animal2));
        verify(animalRepository).findAll();
    }

    @Test
    void testGetAnimalsEmpty() {
        when(animalRepository.findAll()).thenReturn(List.of());

        List<Animal> result = zooInventoryService.getAnimals();

        assertTrue(result.isEmpty());
        verify(animalRepository).findAll();
    }

    @Test
    void testGetThings() {
        Computer computer = new Computer(1, "PC");
        Cage cage = new Cage(2, "Cage", 10.0, "Steel");
        when(thingsRepository.findAll()).thenReturn(List.of(computer, cage));

        List<Thing> result = zooInventoryService.getThings();

        assertEquals(2, result.size());
        assertTrue(result.contains(computer));
        assertTrue(result.contains(cage));
        verify(thingsRepository).findAll();
    }

    @Test
    void testGetThingsEmpty() {
        when(thingsRepository.findAll()).thenReturn(List.of());

        List<Thing> result = zooInventoryService.getThings();

        assertTrue(result.isEmpty());
        verify(thingsRepository).findAll();
    }

    @Test
    void testInventoryOrder() {
        Animal animal = mock(Animal.class);
        Computer computer = new Computer(1, "PC");

        when(animalRepository.findAll()).thenReturn(List.of(animal));
        when(thingsRepository.findAll()).thenReturn(List.of(computer));

        List<IInventory> result = zooInventoryService.getAllInventory();

        assertEquals(2, result.size());
        assertEquals(animal, result.get(0));
        assertEquals(computer, result.get(1));
    }
}