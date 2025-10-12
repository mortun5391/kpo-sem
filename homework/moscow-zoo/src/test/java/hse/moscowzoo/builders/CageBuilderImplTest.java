package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.things.Cage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CageBuilderImplTest {

    private CageBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new CageBuilderImpl();
        BaseThingBuilder.resetCounter();
    }

    @Test
    void testBuildCageWithAllParameters() {
        Cage cage = (Cage) builder
                .withName("Large Cage")
                .withInventoryNumber(1)
                .withArea(25.5)
                .withMaterial("Steel")
                .build();

        assertEquals("Large Cage", cage.getName());
        assertEquals(1, cage.getInventoryNumber());
        assertEquals(25.5, cage.getArea());
        assertEquals("Steel", cage.getMaterial());
    }

    @Test
    void testBuildCageWithAutoInventoryNumber() {
        Cage cage = (Cage) builder
                .withName("Medium Cage")
                .withArea(15.0)
                .withMaterial("Wood")
                .build();

        assertEquals("Medium Cage", cage.getName());
        assertEquals(2000, cage.getInventoryNumber());
        assertEquals(15.0, cage.getArea());
        assertEquals("Wood", cage.getMaterial());
    }

    @Test
    void testBuildCageThrowsExceptionWithoutArea() {
        builder.withName("Test Cage").withMaterial("Steel");

        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }

    @Test
    void testBuildCageThrowsExceptionWithoutMaterial() {
        builder.withName("Test Cage").withArea(10.0);

        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }

    @Test
    void testBuildCageThrowsExceptionWithEmptyMaterial() {
        builder.withName("Test Cage").withArea(10.0).withMaterial("");

        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }
}