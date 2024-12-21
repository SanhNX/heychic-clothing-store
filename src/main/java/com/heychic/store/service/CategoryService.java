package com.heychic.store.service;

import com.heychic.store.domain.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
}
