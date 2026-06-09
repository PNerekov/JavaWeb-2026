package hr.algebra.javaweb.javaweb2026.service.interfaces;

import hr.algebra.javaweb.javaweb2026.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();

    List<CategoryDTO> findByName(String name);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO updateByName(String oldCategoryName, CategoryDTO categoryDTO);

    void deleteByName(String categoryName);
}
