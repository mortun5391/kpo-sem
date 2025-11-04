package hse.hsebank.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Scanner-based input provider implementation
 */
@Component
public class ScannerInputProvider implements InputProvider {
    private final Scanner scanner;

    public ScannerInputProvider() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    @Override
    public double nextDouble() {
        return scanner.nextDouble();
    }

    @Override
    public boolean nextBoolean() {
        return scanner.nextBoolean();
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public long nextLong() {
        return scanner.nextLong();
    }
}