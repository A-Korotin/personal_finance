package org.itmo.practice.pfm.presentation.http.mapper;

/**
 * @param <E> Entity
 * @param <I> Input DTO POJO
 * @param <O> Output DTO POJO
 *
 */
public interface EntityMapper<E, I, O> {
    E fromDto(I dto, String userId);
    O toDto(E entity);
}
