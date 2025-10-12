package hse.moscowzoo.runner;

import hse.moscowzoo.ui.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Главный класс для запуска приложения Московского зоопарка.
 * Реализует интерфейс CommandLineRunner для выполнения кода при старте Spring Boot приложения.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Component
public class ApplicationRunner implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;

    /**
     * Конструктор класса ApplicationRunner.
     *
     * @param consoleMenu объект консольного меню для отображения пользовательского интерфейса
     */
    public ApplicationRunner(ConsoleMenu consoleMenu) {
        this.consoleMenu = consoleMenu;
    }

    /**
     * Метод, выполняемый при запуске приложения.
     * Выводит приветственное сообщение и отображает главное меню.
     *
     * @param args аргументы командной строки
     */
    @Override
    public void run(String... args) {
        System.out.println("Running Moscow ZOO...");
        consoleMenu.showMainMenu();
    }
}