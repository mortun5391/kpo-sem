package studying.factories;

import org.springframework.stereotype.Component;
import studying.domains.Car;
import studying.domains.Catamaran;
import studying.domains.PedalEngine;
import studying.interfaces.CatamaranFactory;
import studying.params.PedalEngineParams;

/**
 * Factory for pedal engine catamarans.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@Component
public class PedalCatamaranFactory implements CatamaranFactory<PedalEngineParams>  {
    /**
     * Create car with pedal engine and unique number.
     *
     * @param pedalEngineParams pedal engine params
     * @param vin unique number
     * @return created car
     */
    @Override
    public Catamaran createCatamaran(PedalEngineParams pedalEngineParams, int vin) {
        var engine = new PedalEngine(pedalEngineParams.pedalSize());
        return new Catamaran(engine, vin);
    }
}
