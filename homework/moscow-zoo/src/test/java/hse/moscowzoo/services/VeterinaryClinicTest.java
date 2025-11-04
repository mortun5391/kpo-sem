package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.domain.valueobjects.HealthStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VeterinaryClinicTest {

    @InjectMocks
    private VeterinaryClinic veterinaryClinic;

    @Test
    void testExamineAnimalHealthy() throws Exception {
        Animal animal = mock(Animal.class);

        // Используем рефлексию для установки детерминированного Random
        setDeterministicRandom(veterinaryClinic, 0.5); // < 0.8 = healthy

        boolean result = veterinaryClinic.examineAnimal(animal);

        assertTrue(result);
        verify(animal).setHealthStatus(HealthStatus.HEALTHY);
    }

    @Test
    void testExamineAnimalSick() throws Exception {
        Animal animal = mock(Animal.class);

        setDeterministicRandom(veterinaryClinic, 0.9); // >= 0.8 = sick

        boolean result = veterinaryClinic.examineAnimal(animal);

        assertFalse(result);
        verify(animal).setHealthStatus(HealthStatus.SICK);
    }

    @Test
    void testAcceptAnimalHealthy() throws Exception {
        Animal animal = mock(Animal.class);

        setDeterministicRandom(veterinaryClinic, 0.5); // < 0.8 = healthy

        boolean result = veterinaryClinic.acceptAnimal(animal);

        assertTrue(result);
        verify(animal).setHealthStatus(HealthStatus.HEALTHY);
    }

    @Test
    void testAcceptAnimalSick() throws Exception {
        Animal animal = mock(Animal.class);

        setDeterministicRandom(veterinaryClinic, 0.9); // >= 0.8 = sick

        boolean result = veterinaryClinic.acceptAnimal(animal);

        assertFalse(result);
        verify(animal).setHealthStatus(HealthStatus.SICK);
    }

    @Test
    void testExamineAnimalMultipleTimes() throws Exception {
        Animal animal1 = mock(Animal.class);
        Animal animal2 = mock(Animal.class);

        // Первое животное здоровое
        setDeterministicRandom(veterinaryClinic, 0.5);
        boolean result1 = veterinaryClinic.examineAnimal(animal1);

        // Второе животное больное
        setDeterministicRandom(veterinaryClinic, 0.9);
        boolean result2 = veterinaryClinic.examineAnimal(animal2);

        assertTrue(result1);
        assertFalse(result2);
        verify(animal1).setHealthStatus(HealthStatus.HEALTHY);
        verify(animal2).setHealthStatus(HealthStatus.SICK);
    }

    private void setDeterministicRandom(VeterinaryClinic clinic, double value) throws Exception {
        Field randomField = VeterinaryClinic.class.getDeclaredField("random");
        randomField.setAccessible(true);

        java.util.Random mockRandom = new java.util.Random() {
            @Override
            public double nextDouble() {
                return value;
            }
        };

        randomField.set(clinic, mockRandom);
    }
}