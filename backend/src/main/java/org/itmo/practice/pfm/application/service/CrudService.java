package org.itmo.practice.pfm.application.service;

import org.itmo.practice.pfm.infrastructure.repository.UserIdFilteringRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class CrudService<T, ID> {

    private final UserIdFilteringRepository<T, ID> repository;

    public CrudService(UserIdFilteringRepository<T, ID> repository) {
        this.repository = repository;
    }

    public Page<T> findAllByUserId(String userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

}
