package studying.interfaces;

import studying.domains.Catamaran;

/**
 * Interface for catamaran factories.
 *
 * @param <T> engine parameters
 */
public interface CatamaranFactory<T> {
    Catamaran createCatamaran(T params, int vin);
}
