package hse.moscowzoo.builders;

import hse.moscowzoo.builders.interfaces.TableBuilder;
import hse.moscowzoo.domain.entities.things.Table;

/**
 * Реализация строителя столов.
 * Позволяет создавать объекты столов с заданными параметрами.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
public class TableBuilderImpl extends BaseThingBuilder<TableBuilder> implements TableBuilder {

    /**
     * Создает объект стола с установленными параметрами.
     *
     * @return созданный объект стола
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    @Override
    public Table build() {
        validateBase();
        return new Table(inventoryNumber, name);
    }
}