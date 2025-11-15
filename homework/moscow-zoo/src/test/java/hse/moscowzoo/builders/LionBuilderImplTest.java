package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Lion;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LionBuilderImplTest {

    private LionBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new LionBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildLionWithAllParameters() {
        Lion lion = (Lion) builder
                .withName("Simba")
                .withInventoryNumber(1)
                .withKindness(KindnessLevel.of(5))
                .withManeLength(0.6)
                .withHasMane(true)
                .build();

        assertEquals("Simba", lion.getName());
        assertEquals(1, lion.getInventoryNumber());
        assertEquals(0.6, lion.getManeLength());
        assertTrue(lion.isHasMane());
    }

    @Test
    void testLionIsNotAlpha() {
        Lion lion = (Lion) builder
                .withName("Young Lion")
                .withKindness(KindnessLevel.of(6))
                .withManeLength(0.3)
                .withHasMane(false)
                .build();

        assertFalse(lion.isHasMane());
    }
}