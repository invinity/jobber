package me.mattpitts.jobber.repository.dynamodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

@ExtendWith(MockitoExtension.class)
class DyDbJobPostingRepositoryTest {

    @Mock
    private CrudRepository<DyDbJobPosting, String> crudRepository;

    @InjectMocks
    private DyDbJobPostingRepository underTest;

    @Test
    @DisplayName("save should call crudRepository.save")
    void save_shouldCallCrudRepositorySave() {
        // Arrange
        DyDbJobPosting posting = DyDbJobPosting.builder().key("testKey").build();
        when(crudRepository.save(posting)).thenReturn(posting);

        // Act
        DyDbJobPosting savedPosting = underTest.save(posting);

        // Assert
        assertThat(savedPosting).isEqualTo(posting);
        verify(crudRepository).save(posting);
    }

    @Test
    @DisplayName("save should throw exception when called with null argument")
    void save_shouldThrowExceptionWhenCalledWithNullArgument() {
        assertThrows(NullPointerException.class, () -> {
            underTest.save(null);
        });
    }

    @Test
    @DisplayName("findAll should call crudRepository.findAll")
    void findAll_shouldCallCrudRepositoryFindAll() {
        // Arrange
        DyDbJobPosting posting1 = DyDbJobPosting.builder().key("key1").build();
        DyDbJobPosting posting2 = DyDbJobPosting.builder().key("key2").build();
        List<DyDbJobPosting> postings = Arrays.asList(posting1, posting2);
        when(crudRepository.findAll()).thenReturn(postings);

        // Act
        List<DyDbJobPosting> foundPostings = underTest.findAll();

        // Assert
        assertThat(foundPostings).isEqualTo(postings);
        verify(crudRepository).findAll();
    }

    @Test
    @DisplayName("builder based construction should function properly")
    void builder_construction_shouldFunctionProperly() {
        DyDbJobPostingRepository builtRepository =
                DyDbJobPostingRepository.builder().crudRepository(crudRepository).build();
        assertThat(builtRepository.getCrudRepository()).isEqualTo(crudRepository);
    }
}
