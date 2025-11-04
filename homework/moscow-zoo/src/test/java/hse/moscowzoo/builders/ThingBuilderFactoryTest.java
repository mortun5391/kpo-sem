package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.CageBuilder;
import hse.moscowzoo.builders.interfaces.ComputerBuilder;
import hse.moscowzoo.builders.interfaces.FeedingBowlBuilder;
import hse.moscowzoo.builders.interfaces.TableBuilder;
import hse.moscowzoo.builders.interfaces.ThingBuilder;
import hse.moscowzoo.domain.valueobjects.ThingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ThingBuilderFactoryTest {

    @Test
    void testCreateCageBuilder() {
        ThingBuilder<?> builder = ThingBuilderFactory.create(ThingType.CAGE);
        assertInstanceOf(CageBuilder.class, builder);
    }

    @Test
    void testCreateComputerBuilder() {
        ThingBuilder<?> builder = ThingBuilderFactory.create(ThingType.COMPUTER);
        assertInstanceOf(ComputerBuilder.class, builder);
    }

    @Test
    void testCreateFeedingBowlBuilder() {
        ThingBuilder<?> builder = ThingBuilderFactory.create(ThingType.FEEDING_BOWL);
        assertInstanceOf(FeedingBowlBuilder.class, builder);
    }

    @Test
    void testCreateTableBuilder() {
        ThingBuilder<?> builder = ThingBuilderFactory.create(ThingType.TABLE);
        assertInstanceOf(TableBuilder.class, builder);
    }

    @Test
    void testCageBuilderMethod() {
        CageBuilder builder = ThingBuilderFactory.cage();
        assertNotNull(builder);
    }

    @Test
    void testComputerBuilderMethod() {
        ComputerBuilder builder = ThingBuilderFactory.computer();
        assertNotNull(builder);
    }

    @Test
    void testFeedingBowlBuilderMethod() {
        FeedingBowlBuilder builder = ThingBuilderFactory.feedingBowl();
        assertNotNull(builder);
    }

    @Test
    void testTableBuilderMethod() {
        TableBuilder builder = ThingBuilderFactory.table();
        assertNotNull(builder);
    }
}