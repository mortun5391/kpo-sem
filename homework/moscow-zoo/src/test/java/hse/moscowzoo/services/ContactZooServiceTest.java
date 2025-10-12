package hse.moscowzoo.services;

import hse.moscowzoo.domain.entities.alive.Animal;
import hse.moscowzoo.repositories.AnimalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactZooServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private ContactZooService contactZooService;

    @Test
    void testGetContactZooAnimals() {
        Animal animal1 = mock(Animal.class);
        Animal animal2 = mock(Animal.class);
        List<Animal> expectedAnimals = List.of(animal1, animal2);

        when(animalRepository.findContactZooEligible()).thenReturn(expectedAnimals);

        List<Animal> result = contactZooService.getContactZooAnimals();

        assertEquals(2, result.size());
        verify(animalRepository).findContactZooEligible();
    }

    @Test
    void testGetContactZooAnimalsEmpty() {
        when(animalRepository.findContactZooEligible()).thenReturn(List.of());

        List<Animal> result = contactZooService.getContactZooAnimals();

        assertTrue(result.isEmpty());
    }
}