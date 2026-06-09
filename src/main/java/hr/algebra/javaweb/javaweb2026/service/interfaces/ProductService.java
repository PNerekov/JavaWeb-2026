package hr.algebra.javaweb.javaweb2026.service.interfaces;

import hr.algebra.javaweb.javaweb2026.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();
    Optional<ProductDTO> findById(Long id);
    List<ProductDTO> findByName(String name);
    ProductDTO save(ProductDTO product);
    List<ProductDTO> findByCategory(String category);
    void decreaseStock(String productName, Integer quantity);
    void deleteByName(String productName);
    ProductDTO updateByName(String oldProductName, ProductDTO productDTO);
}
