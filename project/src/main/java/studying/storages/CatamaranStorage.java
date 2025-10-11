package studying.storages;

import studying.domains.Car;
import studying.domains.Catamaran;
import studying.domains.Customer;
import studying.interfaces.CatamaranFactory;
import studying.interfaces.CatamaranProvider;

import java.util.ArrayList;
import java.util.List;

public class CatamaranStorage implements CatamaranProvider {
    private final List<Catamaran> catamarans = new ArrayList<>();

    private int catamaranNumberCounter = 0;

    /**
     * Issues a suitable car to a customer based on their characteristics.
     * The car is selected based on compatibility with the customer and is removed
     * from the available cars list after being issued.
     *
     * @param customer the customer for whom the car is being selected
     * @return a suitable car or {@code null} if no compatible car is found
     * @see Car#isCompatible(Customer)
     */
    @Override
    public Catamaran takeCatamaran(Customer customer) {
        var filteredCatamarans = catamarans.stream().filter(catamaran -> catamaran.isCompatible(customer)).toList();

        var firstCatamaran = filteredCatamarans.stream().findFirst();

        firstCatamaran.ifPresent(catamarans::remove);

        return firstCatamaran.orElse(null);
    }

    /**
     * Adds a new car to the service using the specified car factory.
     * The car is created with the provided parameters and assigned a unique car number.
     *
     * @param <T> the type of parameters required by the car factory
     * @param catamaranFactory the factory used to create the car
     * @param params the parameters required to create the car
     */
    public <T> void addCatamaran(CatamaranFactory<T> catamaranFactory, T params) {
        var catamaran = catamaranFactory.createCatamaran(params, ++catamaranNumberCounter);
        catamarans.add(catamaran);
    }
}
