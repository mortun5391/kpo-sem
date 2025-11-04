package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.things.FeedingBowl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FeedingBowlBuilderImplTest {

    private FeedingBowlBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new FeedingBowlBuilderImpl();
        BaseThingBuilder.resetCounter();
    }

    @Test
    void testBuildFeedingBowlWithAllParameters() {
        FeedingBowl bowl = (FeedingBowl) builder
                .withName("Automatic Feeder")
                .withInventoryNumber(3)
                .withCapacity(2.5)
                .withAutomatic(true)
                .build();

        assertEquals("Automatic Feeder", bowl.getName());
        assertEquals(3, bowl.getInventoryNumber());
        assertEquals(2.5, bowl.getCapacity());
        assertTrue(bowl.isAutomatic());
    }

    @Test
    void testBuildManualFeedingBowl() {
        FeedingBowl bowl = (FeedingBowl) builder
                .withName("Manual Bowl")
                .withCapacity(1.0)
                .withAutomatic(false)
                .build();

        assertEquals("Manual Bowl", bowl.getName());
        assertEquals(1.0, bowl.getCapacity());
        assertFalse(bowl.isAutomatic());
    }

    @Test
    void testBuildFeedingBowlThrowsExceptionWithoutCapacity() {
        builder.withName("Test Bowl").withAutomatic(true);

        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }

    @Test
    void testBuildFeedingBowlThrowsExceptionWithoutAutomaticFlag() {
        builder.withName("Test Bowl").withCapacity(1.5);

        assertThrows(IllegalStateException.class, () -> {
            builder.build();
        });
    }
}