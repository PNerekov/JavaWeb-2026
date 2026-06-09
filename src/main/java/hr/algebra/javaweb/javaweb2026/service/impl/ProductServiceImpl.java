package hr.algebra.javaweb.javaweb2026.service.impl;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.dto.ProductDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.model.Category;
import hr.algebra.javaweb.javaweb2026.model.Product;
import hr.algebra.javaweb.javaweb2026.repository.CategoryRepository;
import hr.algebra.javaweb.javaweb2026.repository.ProductRepository;
import hr.algebra.javaweb.javaweb2026.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .map(this::toDTO)
                .toList();
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .map(this::toDTO);
    }

    @Override
    public List<ProductDTO> findByName(String name) {
        return productRepository.findByName(name).stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        if (!productRepository.findByName(productDTO.getName()).isEmpty()) {
            throw new IllegalArgumentException("Product with this name already exists.");
        }

        Category category = categoryRepository.findByNameIgnoreCase(productDTO.getCategoryName())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found: " + productDTO.getCategoryName()));

        Product product = toEntity(productDTO, category);
        Product savedProduct = productRepository.save(product);

        return toDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategoryNameIgnoreCase(category)
                .stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .map(this::toDTO)
                .toList();
    }

    @Override
    public void decreaseStock(String productName, Integer quantity) {
        Product product = productRepository.findByName(productName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(WebConstants.ErrorMessages.PRODUCT + productName));

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock for product: " + productName);
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);

        productRepository.save(product);
    }

    @Override
    public void deleteByName(String productName) {
        Product product = productRepository.findByName(productName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(WebConstants.ErrorMessages.PRODUCT + productName));

        productRepository.delete(product);
    }

    @Override
    public ProductDTO updateByName(String oldProductName, ProductDTO productDTO){
        Product product = productRepository.findByName(oldProductName).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(WebConstants.ErrorMessages.PRODUCT + oldProductName));

        boolean nameChanged = !oldProductName.equals(productDTO.getName());

        if(nameChanged && !productRepository.findByName(productDTO.getName()).isEmpty()){
            throw new IllegalArgumentException("Product with this name already exists");
        }

        Category category = categoryRepository.findByNameIgnoreCase(productDTO.getCategoryName()).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found: " + productDTO.getCategoryName()
                ));

        product.setName(productDTO.getName());
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageUrl(productDTO.getImageUrl());

        Product savedProduct = productRepository.save(product);

        return toDTO(savedProduct);
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl()
        );
    }

    private Product toEntity(ProductDTO dto, Category category) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setCategory(category);
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());

        return product;
    }
}
