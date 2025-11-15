package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Fox;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FoxBuilderImplTest {

    private FoxBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new FoxBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildFoxWithAllParameters() {
        Fox fox = (Fox) builder
                .withName("Foxy")
                .withInventoryNumber(1)
                .withKindness(KindnessLevel.of(6))
                .withTailLength(0.9)
                .withFurColor("red")
                .build();

        assertEquals("Foxy", fox.getName());
        assertEquals(1, fox.getInventoryNumber());
        assertEquals(0.9, fox.getTailLength());
        assertEquals("red", fox.getFurColor());
    }

    @Test
    void testFoxCunningLevelBounds() {
        Fox fox = (Fox) builder
                .withName("Sly Fox")
                .withKindness(KindnessLevel.of(7))
                .withTailLength(0.8)
                .withFurColor("white")
                .build();

        assertEquals("white", fox.getFurColor());
    }
}