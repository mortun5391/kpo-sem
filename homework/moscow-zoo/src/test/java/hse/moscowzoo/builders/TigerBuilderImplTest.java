package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Tiger;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TigerBuilderImplTest {

    private TigerBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new TigerBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildTigerWithAllParameters() {
        Tiger tiger = (Tiger) builder
                .withName("Shere Khan")
                .withInventoryNumber(1)
                .withStripeCount(120)
                .withStripePattern("001")
                .build();

        assertEquals("Shere Khan", tiger.getName());
        assertEquals(1, tiger.getInventoryNumber());
        assertEquals(120, tiger.getStripeCount());
        assertEquals("001", tiger.getStripePattern());
    }

    @Test
    void testTigerCannotRoar() {
        Tiger tiger = (Tiger) builder
                .withName("Quiet Tiger")
                .withKindness(KindnessLevel.of(4))
                .withStripeCount(100)
                .withStripePattern("002")
                .build();

        assertEquals("002", tiger.getStripePattern());
    }
}