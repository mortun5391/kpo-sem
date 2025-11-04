package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.things.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TableBuilderImplTest {

    private TableBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new TableBuilderImpl();
        BaseThingBuilder.resetCounter();
    }

    @Test
    void testBuildTableWithInventoryNumber() {
        Table table = (Table) builder
                .withName("Dining Table")
                .withInventoryNumber(7)
                .build();

        assertEquals("Dining Table", table.getName());
        assertEquals(7, table.getInventoryNumber());
    }

    @Test
    void testBuildTableWithAutoInventoryNumber() {
        Table table = (Table) builder
                .withName("Coffee Table")
                .build();

        assertEquals("Coffee Table", table.getName());
        assertEquals(2000, table.getInventoryNumber());
    }
}