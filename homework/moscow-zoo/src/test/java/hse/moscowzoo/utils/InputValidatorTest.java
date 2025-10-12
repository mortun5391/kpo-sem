package hse.moscowzoo.utils;

import hse.moscowzoo.domain.valueobjects.KindnessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputValidatorTest {

    @Mock
    private InputProvider inputProvider;

    private InputValidator inputValidator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidator(inputProvider);
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetIntInputSuccess() {
        when(inputProvider.nextInt()).thenReturn(42);
        when(inputProvider.nextLine()).thenReturn("");

        int result = inputValidator.getIntInput("Enter number: ");

        assertEquals(42, result);
        verify(inputProvider).nextInt();
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetIntInputWithRetry() {
        when(inputProvider.nextInt())
                .thenThrow(new InputMismatchException())
                .thenReturn(42);
        when(inputProvider.nextLine()).thenReturn("");

        int result = inputValidator.getIntInput("Enter number: ");

        assertEquals(42, result);
        verify(inputProvider, times(2)).nextInt();
        verify(inputProvider, times(2)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: please enter an integer."));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetDoubleInputSuccess() {
        when(inputProvider.nextDouble()).thenReturn(3.14);
        when(inputProvider.nextLine()).thenReturn("");

        double result = inputValidator.getDoubleInput("Enter number: ");

        assertEquals(3.14, result, 0.001);
        verify(inputProvider).nextDouble();
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetDoubleInputPositiveValidation() {
        when(inputProvider.nextDouble())
                .thenReturn(-5.0)
                .thenReturn(0.0)
                .thenReturn(2.5);
        when(inputProvider.nextLine()).thenReturn("");

        double result = inputValidator.getDoubleInput("Enter number: ");

        assertEquals(2.5, result, 0.001);
        verify(inputProvider, times(3)).nextDouble();
        verify(inputProvider, times(3)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: please enter a positive number."));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetDoubleInputWithInputMismatch() {
        when(inputProvider.nextDouble())
                .thenThrow(new InputMismatchException())
                .thenReturn(1.5);
        when(inputProvider.nextLine()).thenReturn("");

        double result = inputValidator.getDoubleInput("Enter number: ");

        assertEquals(1.5, result, 0.001);
        verify(inputProvider, times(2)).nextDouble();
        verify(inputProvider, times(2)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: please enter a number."));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetStringInputSuccess() {
        when(inputProvider.nextLine()).thenReturn("Test String");

        String result = inputValidator.getStringInput("Enter text: ");

        assertEquals("Test String", result);
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetStringInputWithEmptyInput() {
        when(inputProvider.nextLine())
                .thenReturn("")
                .thenReturn("   ")
                .thenReturn("Valid");

        String result = inputValidator.getStringInput("Enter text: ");

        assertEquals("Valid", result);
        verify(inputProvider, times(3)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: input cannot be empty."));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetBooleanInputSuccess() {
        when(inputProvider.nextBoolean()).thenReturn(true);
        when(inputProvider.nextLine()).thenReturn("");

        boolean result = inputValidator.getBooleanInput("Is it true?");

        assertTrue(result);
        verify(inputProvider).nextBoolean();
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetBooleanInputWithInputMismatch() {
        when(inputProvider.nextBoolean())
                .thenThrow(new InputMismatchException())
                .thenReturn(false);
        when(inputProvider.nextLine()).thenReturn("");

        boolean result = inputValidator.getBooleanInput("Is it true?");

        assertFalse(result);
        verify(inputProvider, times(2)).nextBoolean();
        verify(inputProvider, times(2)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: please enter 'true' or 'false'."));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetYesNoInputWithYes() {
        when(inputProvider.nextLine())
                .thenReturn("y");

        boolean result = inputValidator.getYesNoInput("Continue?");

        assertTrue(result);
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetYesNoInputWithNo() {
        when(inputProvider.nextLine())
                .thenReturn("n");

        boolean result = inputValidator.getYesNoInput("Continue?");

        assertFalse(result);
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetYesNoInputWithInvalidInput() {
        when(inputProvider.nextLine())
                .thenReturn("invalid")
                .thenReturn("y");

        boolean result = inputValidator.getYesNoInput("Continue?");

        assertTrue(result);
        verify(inputProvider, times(2)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: please enter 'y' or 'n'."));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testReadKindnessLevelSuccess() {
        when(inputProvider.nextInt()).thenReturn(7);
        when(inputProvider.nextLine()).thenReturn("");

        KindnessLevel result = inputValidator.readKindnessLevel();

        assertNotNull(result);
        assertEquals(7, result.getValue());
        verify(inputProvider).nextInt();
        verify(inputProvider).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testReadKindnessLevelWithRetry() {
        when(inputProvider.nextInt())
                .thenReturn(0)
                .thenReturn(5);
        when(inputProvider.nextLine()).thenReturn("");

        KindnessLevel result = inputValidator.readKindnessLevel();

        assertNotNull(result);
        assertEquals(5, result.getValue());
        verify(inputProvider, times(2)).nextInt();
        verify(inputProvider, times(2)).nextLine();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: kindness level must be between 1 and 10!"));
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testReadKindnessLevelBoundaryValues() {
        when(inputProvider.nextInt())
                .thenReturn(1);
        when(inputProvider.nextLine()).thenReturn("");

        KindnessLevel result = inputValidator.readKindnessLevel();

        assertNotNull(result);
        assertEquals(1, result.getValue());
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testReadKindnessLevelWithInputMismatch() {
        when(inputProvider.nextInt())
                .thenThrow(new InputMismatchException())
                .thenReturn(8);
        when(inputProvider.nextLine()).thenReturn("");

        KindnessLevel result = inputValidator.readKindnessLevel();

        assertNotNull(result);
        assertEquals(8, result.getValue());
        verify(inputProvider, times(2)).nextInt();
        verify(inputProvider, times(2)).nextLine();
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetStringInputSimple() {
        when(inputProvider.nextLine()).thenReturn("Simple");

        String result = inputValidator.getStringInput("Prompt: ");

        assertEquals("Simple", result);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testGetYesNoInputSimple() {
        when(inputProvider.nextLine()).thenReturn("yes");

        boolean result = inputValidator.getYesNoInput("Prompt: ");

        assertTrue(result);
    }
}