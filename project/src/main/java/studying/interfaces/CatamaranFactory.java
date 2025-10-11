package studying.interfaces;

import studying.domains.Catamaran;

public interface CatamaranFactory<T> {
    Catamaran createCatamaran(T params, int vin);
}
