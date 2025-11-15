package hse.moscowzoo.utils;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

/**
 * Валидатор ввода данных от пользователя.
 * Обеспечивает проверку и корректный ввод различных типов данных.
 *
 * @author mortun5391
 * @version 1.0
 * @since 2025
 */
@Component
public class InputValidator {
    private InputProvider inputProvider;

    /**
     * Конструктор валидатора ввода.
     *
     * @param inputProvider поставщик ввода данных
     */
    public InputValidator(InputProvider inputProvider) {
        this.inputProvider = inputProvider;
    }

    /**
     * Запрашивает у пользователя ввод целого числа и проверяет его корректность.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Целое число, введённое пользователем.
     */
    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return inputProvider.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: please enter an integer.");
            } finally {
                inputProvider.nextLine();
            }
        }
    }

    /**
     * Запрашивает у пользователя ввод дробного числа и проверяет его корректность.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Положительное дробное число, введённое пользователем.
     */
    public double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = inputProvider.nextDouble();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Error: please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: please enter a number.");
            } finally {
                inputProvider.nextLine();
            }
        }
    }

    /**
     * Запрашивает у пользователя ввод строки и проверяет, что она не пустая.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Строка, введённая пользователем.
     */
    public String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = inputProvider.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Error: input cannot be empty.");
            }
        }
    }

    /**
     * Запрашивает у пользователя ввод boolean значения.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Логическое значение, введённое пользователем.
     */
    public boolean getBooleanInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (true/false): ");
                return inputProvider.nextBoolean();
            } catch (InputMismatchException e) {
                System.out.println("Error: please enter 'true' or 'false'.");
            } finally {
                inputProvider.nextLine();
            }
        }
    }

    /**
     * Альтернативный метод для boolean с вариантами да/нет.
     *
     * @param prompt Сообщение, которое будет показано пользователю.
     * @return Логическое значение на основе ввода пользователя.
     */
    public boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = inputProvider.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes") || input.equals("true")) {
                return true;
            } else if (input.equals("n") || input.equals("no") || input.equals("false")) {
                return false;
            } else {
                System.out.println("Error: please enter 'y' or 'n'.");
            }
        }
    }

    /**
     * Запрашивает у пользователя ввод уровня доброты животного.
     *
     * @return Объект KindnessLevel с валидным значением от 1 до 10.
     */
    public KindnessLevel readKindnessLevel() {
        while (true) {
            int level = getIntInput("Kindness level (1-10): ");
            if (level >= 1 && level <= 10) {
                return KindnessLevel.of(level);
            }
            System.out.println("Error: kindness level must be between 1 and 10!");
        }
    }
}