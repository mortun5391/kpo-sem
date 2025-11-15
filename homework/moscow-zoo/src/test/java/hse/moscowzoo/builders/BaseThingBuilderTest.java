package hse.moscowzoo.builders;

import hse.moscowzoo.domain.entities.things.Thing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseThingBuilderTest {

    @BeforeEach
    void setUp() {
        BaseThingBuilder.resetCounter();
    }

    @Test
    void testWithInventoryNumber() {
        TestThingBuilder builder = new TestThingBuilder();
        builder.withInventoryNumber(123);

        TestThing thing = (TestThing) builder.withName("Test").build();
        assertEquals(123, thing.getInventoryNumber());
    }

    @Test
    void testWithName() {
        TestThingBuilder builder = new TestThingBuilder();
        builder.withName("Test Thing");

        TestThing thing = (TestThing) builder.build();
        assertEquals("Test Thing", thing.getName());
    }

    @Test
    void testAutoInventoryNumber() {
        TestThingBuilder builder1 = new TestThingBuilder();
        TestThing thing1 = (TestThing) builder1.withName("Thing1").build();

        TestThingBuilder builder2 = new TestThingBuilder();
        TestThing thing2 = (TestThing) builder2.withName("Thing2").build();

        assertEquals(2000, thing1.getInventoryNumber());
        assertEquals(2001, thing2.getInventoryNumber());
    }

    @Test
    void testValidationThrowsExceptionForNullName() {
        TestThingBuilder builder = new TestThingBuilder();

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testValidationThrowsExceptionForEmptyName() {
        TestThingBuilder builder = new TestThingBuilder();

        assertThrows(IllegalStateException.class, () -> {
            builder.withName("").build();
        });
    }

    private static class TestThingBuilder extends BaseThingBuilder<TestThingBuilder> {
        @Override
        public Thing build() {
            validateBase();
            return new TestThing(inventoryNumber, name);
        }
    }

    private static class TestThing extends Thing {
        public TestThing(int inventoryNumber, String name) {
            super(inventoryNumber, name);
        }
    }
}