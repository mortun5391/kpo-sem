package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.AnimalBuilder;
import hse.moscowzoo.builders.interfaces.ElephantBuilder;
import hse.moscowzoo.builders.interfaces.FoxBuilder;
import hse.moscowzoo.builders.interfaces.GiraffeBuilder;
import hse.moscowzoo.builders.interfaces.LionBuilder;
import hse.moscowzoo.builders.interfaces.MonkeyBuilder;
import hse.moscowzoo.builders.interfaces.RabbitBuilder;
import hse.moscowzoo.builders.interfaces.TigerBuilder;
import hse.moscowzoo.builders.interfaces.WolfBuilder;
import hse.moscowzoo.domain.valueobjects.AnimalType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class AnimalBuilderFactoryTest {

    @Test
    void testCreateRabbitBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.RABBIT);
        assertInstanceOf(RabbitBuilder.class, builder);
    }

    @Test
    void testCreateElephantBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.ELEPHANT);
        assertInstanceOf(ElephantBuilder.class, builder);
    }

    @Test
    void testCreateMonkeyBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.MONKEY);
        assertInstanceOf(MonkeyBuilder.class, builder);
    }

    @Test
    void testCreateGiraffeBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.GIRAFFE);
        assertInstanceOf(GiraffeBuilder.class, builder);
    }

    @Test
    void testCreateTigerBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.TIGER);
        assertInstanceOf(TigerBuilder.class, builder);
    }

    @Test
    void testCreateWolfBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.WOLF);
        assertInstanceOf(WolfBuilder.class, builder);
    }

    @Test
    void testCreateLionBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.LION);
        assertInstanceOf(LionBuilder.class, builder);
    }

    @Test
    void testCreateFoxBuilder() {
        AnimalBuilder<?> builder = AnimalBuilderFactory.create(AnimalType.FOX);
        assertInstanceOf(FoxBuilder.class, builder);
    }

    @Test
    void testFactoryMethods() {
        assertNotNull(AnimalBuilderFactory.rabbit());
        assertNotNull(AnimalBuilderFactory.elephant());
        assertNotNull(AnimalBuilderFactory.monkey());
        assertNotNull(AnimalBuilderFactory.giraffe());
        assertNotNull(AnimalBuilderFactory.tiger());
        assertNotNull(AnimalBuilderFactory.wolf());
        assertNotNull(AnimalBuilderFactory.lion());
        assertNotNull(AnimalBuilderFactory.fox());
    }

    @Test
    void testAllBuildersAreDifferentInstances() {
        RabbitBuilder rabbit1 = AnimalBuilderFactory.rabbit();
        RabbitBuilder rabbit2 = AnimalBuilderFactory.rabbit();
        assertNotSame(rabbit1, rabbit2);
    }
}