package hse.moscowzoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения Московского зоопарка.
 * Запускает Spring Boot приложение для управления зоопарком.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@SpringBootApplication
public class ZooApplication {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ZooApplication.class, args);
    }
}