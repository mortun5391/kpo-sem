package studying.observers;

import studying.builders.ReportBuilder;
import studying.domains.Customer;
import studying.domains.Report;
import studying.enums.ProductionType;

public class ReportSalesObserver implements SalesObserver{
    private final ReportBuilder reportBuilder = new ReportBuilder();

    @Override
    public void onSale(Customer customer, ProductionType productType, int vin) {
        String message = String.format(
                "Продажа: %s VIN-%d клиенту %s (Сила рук: %d, Сила ног: %d, IQ: %d)",
                productType, vin, customer.getName(),
                customer.getArmStrength(), customer.getLegStrength(), customer.getIq()
        );
        reportBuilder.addOperation(message);
    }

    @Override
    public Report generateReport() {
        return reportBuilder.build();
    }
}
