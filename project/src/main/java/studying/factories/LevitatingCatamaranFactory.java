package studying.factories;

import org.springframework.stereotype.Component;
import studying.domains.Catamaran;
import studying.domains.LevitatingEngine;
import studying.interfaces.CatamaranFactory;
import studying.params.EmptyEngineParams;

/**
 * Factory for levitating engine catamarans.
 *
 * @author Khalilbekov Khalilbek
 * @since 2025-09-28
 */
@Component
public class LevitatingCatamaranFactory implements CatamaranFactory<EmptyEngineParams> {
    /**
     * Create car with levitating engine and unique number.
     *
     * @param emptyEngineParams empty param
     * @param vin unique number
     * @return created car
     */
    @Override
    public Catamaran createCatamaran(EmptyEngineParams emptyEngineParams, int vin) {
        var engine = new LevitatingEngine();
        return new Catamaran(engine, vin);
    }
}
