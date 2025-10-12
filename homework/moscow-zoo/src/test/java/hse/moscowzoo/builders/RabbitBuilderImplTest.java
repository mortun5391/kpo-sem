package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Rabbit;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RabbitBuilderImplTest {

    private RabbitBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new RabbitBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildRabbitWithAllParameters() {
        Rabbit rabbit = (Rabbit) builder
                .withName("Bugs")
                .withInventoryNumber(1)
                .withKindness(KindnessLevel.of(7))
                .withEarLength(0.3)
                .build();

        assertEquals("Bugs", rabbit.getName());
        assertEquals(1, rabbit.getInventoryNumber());
        assertEquals(7, rabbit.getKindnessLevel().getValue());
        assertEquals(0.3, rabbit.getEarLength());
   }

    @Test
    void testBuildRabbitWithAutoInventoryNumber() {
        Rabbit rabbit = (Rabbit) builder
                .withName("White Rabbit")
                .withKindness(KindnessLevel.of(5))
                .withEarLength(0.25)
                .withFurType("001")
                .build();

        assertEquals("White Rabbit", rabbit.getName());
        assertEquals(1000, rabbit.getInventoryNumber());
        assertEquals(0.25, rabbit.getEarLength());
        assertEquals("001", rabbit.getFurType());
    }

    @Test
    void testRabbitCanBeInContactZoo() {
        Rabbit rabbit = (Rabbit) builder
                .withName("Test Rabbit")
                .withKindness(KindnessLevel.of(8))
                .withEarLength(0.3)
                .withFurType("002")
                .build();

        assertTrue(rabbit.canBeInContactZoo());
    }

    @Test
    void testRabbitCannotBeInContactZoo() {
        Rabbit rabbit = (Rabbit) builder
                .withName("Test Rabbit")
                .withKindness(KindnessLevel.of(3))
                .withEarLength(0.3)
                .withFurType("003")
                .build();

        assertFalse(rabbit.canBeInContactZoo());
    }
}