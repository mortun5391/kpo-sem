package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Elephant;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElephantBuilderImplTest {

    private ElephantBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new ElephantBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildElephantWithAllParameters() {
        Elephant elephant = (Elephant) builder
                .withName("Dumbo")
                .withInventoryNumber(1)
                .withKindness(KindnessLevel.of(8))
                .withTrunkLength(2.5)
                .withTuskLength(1.8)
                .build();

        assertEquals("Dumbo", elephant.getName());
        assertEquals(1, elephant.getInventoryNumber());
        assertEquals(8, elephant.getKindnessLevel().getValue());
        assertEquals(2.5, elephant.getTrunkLength());
        assertEquals(1.8, elephant.getTuskLength());
    }

    @Test
    void testElephantCanBeInContactZoo() {
        Elephant elephant = (Elephant) builder
                .withName("Test Elephant")
                .withKindness(KindnessLevel.of(7))
                .withTrunkLength(2.0)
                .withTuskLength(1.5)
                .build();

        assertTrue(elephant.canBeInContactZoo());
    }
}