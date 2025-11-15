package studying.observers;

import studying.domains.Report;
import studying.enums.ProductionType;
import studying.domains.Customer;

public interface SalesObserver {
    void onSale(Customer customer, ProductionType type, int vin);
    Report generateReport();
}
