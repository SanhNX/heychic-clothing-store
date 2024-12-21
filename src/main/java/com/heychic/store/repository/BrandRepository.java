package com.heychic.store.repository;

import com.heychic.store.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Custom query methods (if needed) can be added here
}
