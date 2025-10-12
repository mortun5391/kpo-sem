package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.valueobjects.HealthStatus;
import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseAnimalBuilderTest {

    @BeforeEach
    void setUp() {
        BaseAnimalBuilder.resetCounter();
    }

    @Test
    void testWithInventoryNumber() {
        TestAnimalBuilder builder = new TestAnimalBuilder();
        builder.withInventoryNumber(123);

        TestAnimal animal = (TestAnimal) builder.withName("Test").withKindness(KindnessLevel.of(5)).build();
        assertEquals(123, animal.getInventoryNumber());
    }

    @Test
    void testWithName() {
        TestAnimalBuilder builder = new TestAnimalBuilder();
        builder.withName("Test Animal");

        TestAnimal animal = (TestAnimal) builder.withKindness(KindnessLevel.of(5)).build();
        assertEquals("Test Animal", animal.getName());
    }

    @Test
    void testWithKindness() {
        TestAnimalBuilder builder = new TestAnimalBuilder();
        builder.withKindness(KindnessLevel.of(8));

        TestAnimal animal = (TestAnimal) builder.withName("Test").build();
        assertEquals(8, animal.getKindnessLevel().getValue());
    }

    @Test
    void testAutoInventoryNumber() {
        TestAnimalBuilder builder1 = new TestAnimalBuilder();
        TestAnimal animal1 = (TestAnimal) builder1.withName("Animal1").withKindness(KindnessLevel.of(5)).build();

        TestAnimalBuilder builder2 = new TestAnimalBuilder();
        TestAnimal animal2 = (TestAnimal) builder2.withName("Animal2").withKindness(KindnessLevel.of(5)).build();

        assertEquals(1000, animal1.getInventoryNumber());
        assertEquals(1001, animal2.getInventoryNumber());
    }

    @Test
    void testValidationThrowsExceptionForNullName() {
        TestAnimalBuilder builder = new TestAnimalBuilder();

        assertThrows(IllegalStateException.class, () -> {
            builder.withKindness(KindnessLevel.of(5)).build();
        });
    }

    @Test
    void testDefaultHealthStatus() {
        TestAnimalBuilder builder = new TestAnimalBuilder();
        TestAnimal animal = (TestAnimal) builder.withName("Test").withKindness(KindnessLevel.of(5)).build();

        assertEquals(HealthStatus.HEALTHY, animal.getHealthStatus());
    }

    private static class TestAnimalBuilder extends BaseAnimalBuilder<TestAnimalBuilder> {
        @Override
        public Animal build() {
            validateBase();
            return new TestAnimal(inventoryNumber, name, kindnessLevel);
        }
    }

    private static class TestAnimal extends Animal {
        private final KindnessLevel kindnessLevel;

        public TestAnimal(int inventoryNumber, String name, KindnessLevel kindnessLevel) {
            super(inventoryNumber, name, 100);
            this.kindnessLevel = kindnessLevel;
        }

        public KindnessLevel getKindnessLevel() {
            return kindnessLevel;
        }

        @Override
        public boolean canBeInContactZoo() {
            return kindnessLevel.isSuitableForContactZoo();
        }
    }
}