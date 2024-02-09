package org.itmo.practice.pfm.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserIdFilteringRepository<T, ID> extends JpaRepository<T, ID> {
    Page<T> findAllByUserId(String userId, Pageable pageable);
}
