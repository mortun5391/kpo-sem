package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.things.Computer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComputerBuilderImplTest {

    private ComputerBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new ComputerBuilderImpl();
        BaseThingBuilder.resetCounter();
    }

    @Test
    void testBuildComputerWithInventoryNumber() {
        Computer computer = (Computer) builder
                .withName("Office PC")
                .withInventoryNumber(5)
                .build();

        assertEquals("Office PC", computer.getName());
        assertEquals(5, computer.getInventoryNumber());
    }

    @Test
    void testBuildComputerWithAutoInventoryNumber() {
        Computer computer = (Computer) builder
                .withName("Gaming PC")
                .build();

        assertEquals("Gaming PC", computer.getName());
        assertEquals(2000, computer.getInventoryNumber());
    }

    @Test
    void testBuildComputerThrowsExceptionWithoutName() {
        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }
}