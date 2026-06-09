package hr.algebra.javaweb.javaweb2026.controller.rest;

import hr.algebra.javaweb.javaweb2026.dto.CategoryDTO;
import hr.algebra.javaweb.javaweb2026.dto.ProductDTO;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CategoryService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/product")
@AllArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> productDTOOptional = productService.findById(id);


        return productDTOOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/category/filter/{category}")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category) {
        List<ProductDTO> productDTOList = productService.findByCategory(category);
        return ResponseEntity.ok(productDTOList);
    }

    @PostMapping("/new")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProductDTO = productService.save(productDTO);
        return ResponseEntity.ok(createdProductDTO);
    }


}
