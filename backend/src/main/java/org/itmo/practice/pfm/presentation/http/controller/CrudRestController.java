package org.itmo.practice.pfm.presentation.http.controller;

import lombok.RequiredArgsConstructor;
import org.itmo.practice.pfm.application.service.CrudService;
import org.itmo.practice.pfm.presentation.http.mapper.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @param <ID> Entity identifier
 * @param <E>  Entity
 * @param <I>  Input DTO POJO
 * @param <O>  Output DTO POJO
 */
@RequiredArgsConstructor
public abstract class CrudRestController<ID, E, I, O> {
    protected final EntityMapper<E, I, O> mapper;
    protected final CrudService<E, ID> service;

    protected ResponseEntity<O> findById(ID id) {
        return service
                .findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    protected ResponseEntity<Page<O>> findAllByUserId(String userId, Pageable pageable) {
        Page<O> targetPage = service
                .findAllByUserId(userId, pageable)
                .map(mapper::toDto);

        return ResponseEntity.ok(targetPage);
    }

    protected ResponseEntity<O> create(I dto, String userId) {
        E entity = mapper.fromDto(dto, userId);
        entity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(entity), HttpStatus.CREATED);
    }

    protected abstract ResponseEntity<O> update(ID id, I dto, String userId);

    protected ResponseEntity<Void> delete(ID id) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
