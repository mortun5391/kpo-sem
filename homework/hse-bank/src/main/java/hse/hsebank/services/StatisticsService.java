package hse.hsebank.services;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class StatisticsService {
    private final AtomicLong totalOperations = new AtomicLong(0);
    private final AtomicLong totalProcessingTime = new AtomicLong(0);

    public void recordOperation(long processingTime) {
        totalOperations.incrementAndGet();
        totalProcessingTime.addAndGet(processingTime);
    }

    public double getAverageProcessingTime() {
        long operations = totalOperations.get();
        return operations > 0 ? (double) totalProcessingTime.get() / operations : 0.0;
    }

    public long getTotalOperations() {
        return totalOperations.get();
    }

    public void reset() {
        totalOperations.set(0);
        totalProcessingTime.set(0);
    }
}