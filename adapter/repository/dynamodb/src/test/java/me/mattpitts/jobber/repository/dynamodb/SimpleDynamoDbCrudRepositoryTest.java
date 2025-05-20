package me.mattpitts.jobber.repository.dynamodb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.awspring.cloud.dynamodb.DynamoDbOperations;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@ExtendWith(MockitoExtension.class)
class SimpleDynamoDbCrudRepositoryTest {

    @Mock
    private DynamoDbOperations mockDbOperations;

    private SimpleDynamoDbCrudRepository<TestEntity, String> underTest;

    @BeforeEach
    void setup() {
        underTest = new SimpleDynamoDbCrudRepository<>(mockDbOperations, TestEntity.class);
    }

    static class TestEntity {
        String id;
        String value;
        // getters/setters/constructors omitted for brevity
        TestEntity(String id, String value) { this.id = id; this.value = value; }
        public String getId() { return id; }
        public String getValue() { return value; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestEntity)) return false;
            TestEntity that = (TestEntity) o;
            return id.equals(that.id) && value.equals(that.value);
        }
        @Override
        public int hashCode() { return id.hashCode() + value.hashCode(); }
    }

    @Test
    void save_delegatesTomockDbOperations() {
        TestEntity entity = new TestEntity("1", "foo");
        when(mockDbOperations.save(entity)).thenReturn(entity);

        TestEntity result = underTest.save(entity);

        assertThat(result).isEqualTo(entity);
        verify(mockDbOperations).save(entity);
    }

    @Test
    void findById_returnsEntityIfFound() {
        TestEntity entity = new TestEntity("1", "foo");
        when(mockDbOperations.load(eq(Key.builder().partitionValue("1").build()), eq(TestEntity.class))).thenReturn(entity);

        Optional<TestEntity> result = underTest.findById("1");

        assertThat(result).contains(entity);
        ArgumentCaptor<Key> captor = ArgumentCaptor.forClass(Key.class);
        verify(mockDbOperations).load(captor.capture(), eq(TestEntity.class));
        assertThat(captor.getValue().partitionKeyValue().s()).isEqualTo("1");
    }

    @Test
    void findById_returnsEmptyIfNotFound() {
        when(mockDbOperations.load(eq(Key.builder().partitionValue("1").build()), eq(TestEntity.class))).thenReturn(null);

        Optional<TestEntity> result = underTest.findById("1");

        assertThat(result).isEmpty();
    }

    @Test
    void saveAll_throwsUnsupportedOperationException() {
        assertThatThrownBy(() -> underTest.saveAll(Collections.emptyList()))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void existsById_returnsTrueIfEntityExists() {
        when(mockDbOperations.load(eq(Key.builder().partitionValue("1").build()), eq(TestEntity.class))).thenReturn(new TestEntity("1", "foo"));

        boolean exists = underTest.existsById("1");

        assertThat(exists).isTrue();
    }

    @Test
    void existsById_returnsFalseIfEntityDoesNotExist() {
        when(mockDbOperations.load(eq(Key.builder().partitionValue("1").build()), eq(TestEntity.class))).thenReturn(null);

        boolean exists = underTest.existsById("1");

        assertThat(exists).isFalse();
    }

    @SuppressWarnings("unchecked")
    @Test
    void findAll_returnsAllEntities() {
        List<TestEntity> entities = Arrays.asList(new TestEntity("1", "foo"), new TestEntity("2", "bar"));
        PageIterable<TestEntity> scanResult = mock(PageIterable.class);
        SdkIterable<TestEntity> page = mock(SdkIterable.class);
        when(page.spliterator()).thenReturn(entities.spliterator());
        when(scanResult.items()).thenReturn(page);
        when(mockDbOperations.scanAll(TestEntity.class)).thenReturn(scanResult);

        Iterable<TestEntity> result = underTest.findAll();
        assertThat(result).isNotNull();
        assertThat(result).containsExactlyElementsOf(entities);
    }

    @SuppressWarnings("unchecked")
    @Test
    void findAllById_returnsAllEntities() {
        List<TestEntity> entities = Arrays.asList(new TestEntity("1", "foo"), new TestEntity("2", "bar"));
        PageIterable<TestEntity> scanResult = mock(PageIterable.class);
        SdkIterable<TestEntity> page = mock(SdkIterable.class);
        when(page.spliterator()).thenReturn(entities.spliterator());
        when(scanResult.items()).thenReturn(page);
        when(mockDbOperations.scanAll(TestEntity.class)).thenReturn(scanResult);

        Iterable<TestEntity> result = underTest.findAllById(Arrays.asList("1", "2"));
        assertThat(result).isNotNull();
        assertThat(result).containsExactlyElementsOf(entities);
    }

    @Test
    void count_throwsUnsupportedOperationException() {
        assertThatThrownBy(() -> underTest.count())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void deleteById_delegatesTomockDbOperations() {
        underTest.deleteById("1");
        verify(mockDbOperations).delete(any(Key.class), eq(TestEntity.class));
    }

    @Test
    void delete_delegatesTomockDbOperations() {
        TestEntity entity = new TestEntity("1", "foo");
        underTest.delete(entity);
        verify(mockDbOperations).delete(entity);
    }

    @Test
    void deleteAll_deletesEachEntity() {
        TestEntity e1 = new TestEntity("1", "foo");
        TestEntity e2 = new TestEntity("2", "bar");
        underTest.deleteAll(Arrays.asList(e1, e2));
        verify(mockDbOperations).delete(e1);
        verify(mockDbOperations).delete(e2);
    }

    @Test
    void deleteAll_throwsUnsupportedOperationException() {
        assertThatThrownBy(() -> underTest.deleteAll())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void deleteAllById_deletesEachById() {
        underTest.deleteAllById(Arrays.asList("1", "2"));
        verify(mockDbOperations, times(2)).delete(any(Key.class), eq(TestEntity.class));
    }
}