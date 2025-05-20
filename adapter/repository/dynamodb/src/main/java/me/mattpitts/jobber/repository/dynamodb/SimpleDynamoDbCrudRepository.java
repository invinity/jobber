package me.mattpitts.jobber.repository.dynamodb;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import io.awspring.cloud.dynamodb.DynamoDbOperations;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@lombok.RequiredArgsConstructor
public class SimpleDynamoDbCrudRepository<T, ID> implements CrudRepository<T, ID> {

    private final DynamoDbOperations dynamoDbTemplate;
    private final Class<T> entityClass;

    @Override
    @NonNull
    public <S extends T> S save(@NonNull S entity) {
        return this.dynamoDbTemplate.save(entity);
    }

    @Override
    @NonNull
    public Optional<T> findById(@NonNull ID primaryKey) {
        Key key = Key.builder().partitionValue(primaryKey.toString()).build();
        T result = this.dynamoDbTemplate.load(key, this.entityClass);
        return Optional.ofNullable(result);
    }

    @Override
    @NonNull
    public <S extends T> Iterable<S> saveAll(@NonNull Iterable<S> entities) {
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public boolean existsById(@NonNull ID id) {
        return findById(id).isPresent();
    }

    @Override
    @NonNull
    public Iterable<T> findAll() {
        return this.dynamoDbTemplate.scanAll(this.entityClass).items();
    }

    @Override
    @NonNull
    public Iterable<T> findAllById(@NonNull Iterable<ID> ids) {
        return this.dynamoDbTemplate.scanAll(this.entityClass).items();
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void deleteById(@NonNull ID id) {
        this.dynamoDbTemplate.delete(Key.builder().partitionValue(id.toString()).build(), this.entityClass);
    }

    @Override
    public void delete(@NonNull T entity) {
        this.dynamoDbTemplate.delete(entity);
    }

    @Override
    public void deleteAll(@NonNull Iterable<? extends T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAllById(@NonNull Iterable<? extends ID> ids) {
        for (ID id : ids) {
            deleteById(id);
        }
    }
}
