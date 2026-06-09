package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.dto.CategoryDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.model.Category;
import hr.algebra.javaweb.javaweb2026.repository.CategoryRepository;
import hr.algebra.javaweb.javaweb2026.repository.ProductRepository;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO){
        String categoryName = categoryDTO.getName().trim();

        if(!categoryRepository.findByNameIgnoreCase(categoryName).isEmpty()){
            throw new IllegalArgumentException("Category with this name already exists");
        }

        Category category = new Category();
        category.setName(categoryName);

        Category savedCategory = categoryRepository.save(category);

        return toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateByName(String oldNameCategory, CategoryDTO categoryDTO){
        String newCategoryName = categoryDTO.getName().trim();

        Category category = categoryRepository.findByNameIgnoreCase(oldNameCategory).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + oldNameCategory));

        boolean nameChanged = !oldNameCategory.equalsIgnoreCase(newCategoryName);

        if(nameChanged && !categoryRepository.findByNameIgnoreCase(newCategoryName).isEmpty()){
            throw new IllegalArgumentException("Category with this name already exists");
        }

        category.setName(newCategoryName);

        Category savedCategory = categoryRepository.save(category);

        return toDTO(savedCategory);
    }

    @Override
    public void deleteByName(String categoryName){
        Category category = categoryRepository.findByNameIgnoreCase(categoryName).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));

        if(!productRepository.findByCategoryNameIgnoreCase(categoryName).isEmpty()){
            throw new IllegalArgumentException("Cannot delete category because it still contains products");
        }

        categoryRepository.delete(category);
    }

    private CategoryDTO toDTO(Category category) {
        return new CategoryDTO(category.getName());
    }
}
