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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodCalculationServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private FoodCalculationService foodCalculationService;

    @Test
    void testCalculateTotalFoodConsumption() {
        when(animalRepository.getTotalFoodConsumption()).thenReturn(1500);

        int result = foodCalculationService.calculateTotalFoodConsumption();

        assertEquals(1500, result);
        verify(animalRepository).getTotalFoodConsumption();
    }

    @Test
    void testGetAnimalCount() {
        Animal animal1 = mock(Animal.class);
        Animal animal2 = mock(Animal.class);
        when(animalRepository.findAll()).thenReturn(List.of(animal1, animal2));

        int result = foodCalculationService.getAnimalCount();

        assertEquals(2, result);
        verify(animalRepository).findAll();
    }

    @Test
    void testGetAnimalCountEmpty() {
        when(animalRepository.findAll()).thenReturn(List.of());

        int result = foodCalculationService.getAnimalCount();

        assertEquals(0, result);
    }
}