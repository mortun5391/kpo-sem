package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.ComputerBuilder;
import hse.moscowzoo.domain.entities.things.Computer;

/**
 * Реализация строителя компьютеров.
 * Позволяет создавать объекты компьютеров с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class ComputerBuilderImpl extends BaseThingBuilder<ComputerBuilder> implements ComputerBuilder {

    /**
     * Создает объект компьютера с установленными параметрами.
     *
     * @return созданный объект компьютера
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Computer build() {
        validateBase();
        return new Computer(inventoryNumber, name);
    }
}