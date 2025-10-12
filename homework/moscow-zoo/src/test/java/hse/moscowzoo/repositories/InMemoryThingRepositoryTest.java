package hse.moscowzoo.repositories;

import hse.moscowzoo.domain.entities.things.Computer;
import hse.moscowzoo.domain.entities.things.Cage;
import hse.moscowzoo.domain.entities.things.FeedingBowl;
import hse.moscowzoo.domain.entities.things.Table;
import hse.moscowzoo.domain.entities.things.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryThingRepositoryTest {

    private InMemoryThingRepository repository;
    private Computer computer;
    private Cage cage;
    private FeedingBowl bowl;
    private Table table;

    @BeforeEach
    void setUp() {
        repository = new InMemoryThingRepository();

        computer = new Computer(1, "Office PC");
        cage = new Cage(2, "Bird Cage", 15.5, "Steel");
        bowl = new FeedingBowl(3, "Auto Feeder", 2.5, true);
        table = new Table(4, "Dining Table");
    }

    @Test
    void testSaveThing() {
        repository.save(computer);

        List<Thing> things = repository.findAll();
        assertEquals(1, things.size());
        assertEquals(computer, things.get(0));
    }

    @Test
    void testSaveMultipleThings() {
        repository.save(computer);
        repository.save(cage);
        repository.save(bowl);
        repository.save(table);

        List<Thing> things = repository.findAll();
        assertEquals(4, things.size());
        assertTrue(things.contains(computer));
        assertTrue(things.contains(cage));
        assertTrue(things.contains(bowl));
        assertTrue(things.contains(table));
    }

    @Test
    void testFindByIdExisting() {
        repository.save(computer);
        repository.save(cage);

        Optional<Thing> result = repository.findById(2);

        assertTrue(result.isPresent());
        assertEquals(cage, result.get());
    }

    @Test
    void testFindByIdNonExisting() {
        repository.save(computer);

        Optional<Thing> result = repository.findById(999);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByIdEmptyRepository() {
        Optional<Thing> result = repository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllEmpty() {
        List<Thing> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAllReturnsCopy() {
        repository.save(computer);

        List<Thing> result1 = repository.findAll();
        List<Thing> result2 = repository.findAll();

        assertNotSame(result1, result2);
        assertEquals(result1, result2);

        result1.clear();
        List<Thing> result3 = repository.findAll();
        assertEquals(1, result3.size());
    }

    @Test
    void testRepositoryIsolation() {
        InMemoryThingRepository repo1 = new InMemoryThingRepository();
        InMemoryThingRepository repo2 = new InMemoryThingRepository();

        repo1.save(computer);
        repo2.save(cage);

        assertEquals(1, repo1.findAll().size());
        assertEquals(1, repo2.findAll().size());
        assertEquals(computer, repo1.findAll().get(0));
        assertEquals(cage, repo2.findAll().get(0));
    }

    @Test
    void testSaveSameThingTwice() {
        repository.save(computer);
        repository.save(computer);

        List<Thing> things = repository.findAll();
        assertEquals(2, things.size());
        assertEquals(computer, things.get(0));
        assertEquals(computer, things.get(1));
    }

    @Test
    void testFindByIdWithDifferentThingTypes() {
        repository.save(computer);
        repository.save(cage);
        repository.save(bowl);
        repository.save(table);

        Optional<Thing> result1 = repository.findById(1);
        Optional<Thing> result2 = repository.findById(2);
        Optional<Thing> result3 = repository.findById(3);
        Optional<Thing> result4 = repository.findById(4);

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertTrue(result3.isPresent());
        assertTrue(result4.isPresent());
        assertEquals(computer, result1.get());
        assertEquals(cage, result2.get());
        assertEquals(bowl, result3.get());
        assertEquals(table, result4.get());
    }

    @Test
    void testMixedOperations() {
        repository.save(computer);
        repository.save(cage);

        assertTrue(repository.findById(1).isPresent());
        assertTrue(repository.findById(2).isPresent());
        assertFalse(repository.findById(3).isPresent());

        List<Thing> allThings = repository.findAll();
        assertEquals(2, allThings.size());

        repository.save(bowl);

        List<Thing> updatedThings = repository.findAll();
        assertEquals(3, updatedThings.size());
        assertTrue(updatedThings.contains(bowl));
    }

    @Test
    void testThingInventoryNumberUniqueness() {
        Computer computer1 = new Computer(1, "PC1");
        Computer computer2 = new Computer(1, "PC2");

        repository.save(computer1);
        repository.save(computer2);

        List<Thing> things = repository.findAll();
        assertEquals(2, things.size());

        Optional<Thing> result = repository.findById(1);
        assertTrue(result.isPresent());
    }
}