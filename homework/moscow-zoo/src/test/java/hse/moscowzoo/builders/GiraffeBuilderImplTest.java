package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Giraffe;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiraffeBuilderImplTest {

    private GiraffeBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new GiraffeBuilderImpl();
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testBuildGiraffeWithAllParameters() {
        Giraffe giraffe = (Giraffe) builder
                .withName("Spots")
                .withInventoryNumber(1)
                .withKindness(KindnessLevel.of(6))
                .withNeckLength(3.5)
                .withSpotPattern("001")
                .build();

        assertEquals("Spots", giraffe.getName());
        assertEquals(1, giraffe.getInventoryNumber());
        assertEquals(6, giraffe.getKindnessLevel().getValue());
        assertEquals(3.5, giraffe.getNeckLength());
        assertEquals("001", giraffe.getSpotPattern());
    }
}