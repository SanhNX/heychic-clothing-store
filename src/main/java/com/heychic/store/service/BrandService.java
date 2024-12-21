package com.heychic.store.service;

import com.heychic.store.domain.Brand;
import java.util.List;

public interface BrandService {
    List<Brand> findAll();
    Brand findById(Long id);
    Brand save(Brand brand);
    void deleteById(Long id);
}
