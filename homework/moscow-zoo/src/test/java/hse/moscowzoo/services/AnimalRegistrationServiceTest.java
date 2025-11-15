package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.repositories.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalRegistrationServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private VeterinaryClinic veterinaryClinic;

    @InjectMocks
    private AnimalRegistrationService animalRegistrationService;

    @Test
    void testRegisterAnimalSuccess() {
        Animal animal = mock(Animal.class);
        when(veterinaryClinic.acceptAnimal(animal)).thenReturn(true);

        boolean result = animalRegistrationService.registerAnimal(animal);

        assertTrue(result);
        verify(animalRepository).save(animal);
    }

    @Test
    void testRegisterAnimalFailedInspection() {
        Animal animal = mock(Animal.class);
        when(veterinaryClinic.acceptAnimal(animal)).thenReturn(false);

        boolean result = animalRegistrationService.registerAnimal(animal);

        assertFalse(result);
        verify(animalRepository, never()).save(any());
    }
}