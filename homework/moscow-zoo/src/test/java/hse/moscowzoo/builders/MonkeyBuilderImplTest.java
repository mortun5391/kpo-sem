package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Monkey;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonkeyBuilderImplTest {

    private MonkeyBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new MonkeyBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildMonkeyWithAllParameters() {
        Monkey monkey = (Monkey) builder
                .withName("George")
                .withInventoryNumber(1)
                .withKindness(KindnessLevel.of(9))
                .withTailLength(0.8)
                .withCanUseTools(true)
                .build();

        assertEquals("George", monkey.getName());
        assertEquals(1, monkey.getInventoryNumber());
        assertEquals(9, monkey.getKindnessLevel().getValue());
        assertEquals(0.8, monkey.getTailLength());
        assertTrue(monkey.isCanUseTools());
    }

    @Test
    void testMonkeyIntelligenceLevelBounds() {
        Monkey monkey = (Monkey) builder
                .withName("Smart Monkey")
                .withKindness(KindnessLevel.of(7))
                .withTailLength(0.7)
                .withCanUseTools(false)
                .build();

        assertFalse(monkey.isCanUseTools());
    }
}