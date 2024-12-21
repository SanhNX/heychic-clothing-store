package com.heychic.store.repository;

import com.heychic.store.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods (if needed) can be added here
}
