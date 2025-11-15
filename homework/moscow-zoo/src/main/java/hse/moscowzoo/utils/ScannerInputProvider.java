package hse.moscowzoo.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Реализация поставщика ввода данных на основе Scanner.
 * Использует стандартный ввод для получения данных от пользователя.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Component
public class ScannerInputProvider implements InputProvider {
    private Scanner scanner;

    /**
     * Конструктор инициализирует Scanner для чтения из System.in.
     */
    public ScannerInputProvider() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int nextInt() {
        return scanner.nextInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double nextDouble() {
        return scanner.nextDouble();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nextBoolean() {
        return scanner.nextBoolean();
    }
}