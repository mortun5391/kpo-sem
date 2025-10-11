package studying.factories;

import studying.domains.Car;
import studying.domains.Catamaran;
import studying.domains.HandEngine;
import studying.interfaces.CatamaranFactory;
import studying.interfaces.CatamaranProvider;
import studying.params.EmptyEngineParams;

public class HandCatamaranFactory implements CatamaranFactory<EmptyEngineParams> {
    /**
     * Create catamaran with hand engine and unique number.
     *
     * @param emptyEngineParams empty param
     * @param vin unique number
     * @return created car
     */
    @Override
    public Catamaran createCatamaran(EmptyEngineParams emptyEngineParams, int vin) {
        var engine = new HandEngine();
        return new Catamaran(engine, vin);
    }
}
