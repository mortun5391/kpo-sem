package hse.moscowzoo.repositories;

import hse.moscowzoo.domain.entities.alive.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InMemoryAnimalRepositoryTest {

    private InMemoryAnimalRepository repository;
    private Animal animal1;
    private Animal animal2;
    private Animal animal3;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAnimalRepository();

        animal1 = mock(Animal.class);
        when(animal1.getInventoryNumber()).thenReturn(1);
        when(animal1.getDailyFoodConsumption()).thenReturn(100);
        when(animal1.canBeInContactZoo()).thenReturn(true);

        animal2 = mock(Animal.class);
        when(animal2.getInventoryNumber()).thenReturn(2);
        when(animal2.getDailyFoodConsumption()).thenReturn(200);
        when(animal2.canBeInContactZoo()).thenReturn(false);

        animal3 = mock(Animal.class);
        when(animal3.getInventoryNumber()).thenReturn(3);
        when(animal3.getDailyFoodConsumption()).thenReturn(150);
        when(animal3.canBeInContactZoo()).thenReturn(true);
    }

    @Test
    void testSaveAnimal() {
        repository.save(animal1);

        List<Animal> animals = repository.findAll();
        assertEquals(1, animals.size());
        assertEquals(animal1, animals.getFirst());
    }

    @Test
    void testSaveMultipleAnimals() {
        repository.save(animal1);
        repository.save(animal2);
        repository.save(animal3);

        List<Animal> animals = repository.findAll();
        assertEquals(3, animals.size());
        assertTrue(animals.contains(animal1));
        assertTrue(animals.contains(animal2));
        assertTrue(animals.contains(animal3));
    }

    @Test
    void testFindByIdExisting() {
        repository.save(animal1);
        repository.save(animal2);

        Optional<Animal> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(animal1, result.get());
    }

    @Test
    void testFindByIdNonExisting() {
        repository.save(animal1);

        Optional<Animal> result = repository.findById(999);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByIdEmptyRepository() {
        Optional<Animal> result = repository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllEmpty() {
        List<Animal> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllReturnsCopy() {
        repository.save(animal1);

        List<Animal> result1 = repository.findAll();
        List<Animal> result2 = repository.findAll();

        assertNotSame(result1, result2);
        assertEquals(result1, result2);

        result1.clear();
        List<Animal> result3 = repository.findAll();
        assertEquals(1, result3.size());
    }

    @Test
    void testFindContactZooEligible() {
        repository.save(animal1);
        repository.save(animal2);
        repository.save(animal3);

        List<Animal> result = repository.findContactZooEligible();

        assertEquals(2, result.size());
        assertTrue(result.contains(animal1));
        assertTrue(result.contains(animal3));
        assertFalse(result.contains(animal2));

        verify(animal1, atLeastOnce()).canBeInContactZoo();
        verify(animal2, atLeastOnce()).canBeInContactZoo();
        verify(animal3, atLeastOnce()).canBeInContactZoo();
    }

    @Test
    void testFindContactZooEligibleEmpty() {
        List<Animal> result = repository.findContactZooEligible();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindContactZooEligibleNoneEligible() {
        when(animal1.canBeInContactZoo()).thenReturn(false);
        when(animal2.canBeInContactZoo()).thenReturn(false);

        repository.save(animal1);
        repository.save(animal2);

        List<Animal> result = repository.findContactZooEligible();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTotalFoodConsumption() {
        repository.save(animal1);
        repository.save(animal2);
        repository.save(animal3);

        int result = repository.getTotalFoodConsumption();

        assertEquals(450, result);

        verify(animal1, atLeastOnce()).getDailyFoodConsumption();
        verify(animal2, atLeastOnce()).getDailyFoodConsumption();
        verify(animal3, atLeastOnce()).getDailyFoodConsumption();
    }

    @Test
    void testGetTotalFoodConsumptionEmpty() {
        int result = repository.getTotalFoodConsumption();

        assertEquals(0, result);
    }

    @Test
    void testGetTotalFoodConsumptionSingleAnimal() {
        repository.save(animal1);

        int result = repository.getTotalFoodConsumption();

        assertEquals(100, result);
    }

    @Test
    void testRepositoryIsolation() {
        InMemoryAnimalRepository repo1 = new InMemoryAnimalRepository();
        InMemoryAnimalRepository repo2 = new InMemoryAnimalRepository();

        repo1.save(animal1);
        repo2.save(animal2);

        assertEquals(1, repo1.findAll().size());
        assertEquals(1, repo2.findAll().size());
        assertEquals(animal1, repo1.findAll().getFirst());
        assertEquals(animal2, repo2.findAll().getFirst());
    }

    @Test
    void testSaveSameAnimalTwice() {
        repository.save(animal1);
        repository.save(animal1);

        List<Animal> animals = repository.findAll();
        assertEquals(2, animals.size());
        assertEquals(animal1, animals.get(0));
        assertEquals(animal1, animals.get(1));
    }
}