package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Wolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WolfBuilderImplTest {

    private WolfBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new WolfBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildWolfWithAllParameters() {
        Wolf wolf = (Wolf) builder
                .withName("Alpha")
                .withInventoryNumber(1)
                .withPackRole("1")
                .withHowlVolume(75)
                .build();

        assertEquals("Alpha", wolf.getName());
        assertEquals(1, wolf.getInventoryNumber());
        assertEquals("1", wolf.getPackRole());
        assertEquals(75, wolf.getHowlVolume());
    }
}