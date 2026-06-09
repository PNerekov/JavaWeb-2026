package hr.algebra.javaweb.javaweb2026.controller.mvc;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.dto.ProductDTO;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CategoryService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String showProducts(Model model){
        model.addAttribute(WebConstants.ModelAttributes.PRODUCTS, productService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.SELECTED_CATEGORY, WebConstants.Values.ALL);

        return WebConstants.Views.PRODUCTS;
    }

    @GetMapping("/category/{categoryName}")
    public String showProductsByCategory(@PathVariable String categoryName, Model model) {
        model.addAttribute(WebConstants.ModelAttributes.PRODUCTS, productService.findByCategory(categoryName));
        model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.SELECTED_CATEGORY, categoryName);

        return WebConstants.Views.PRODUCTS;
    }

    @GetMapping("/list")
    public String getProductList(String categoryName, Model model) {
        List<ProductDTO> products;

        if (categoryName == null || categoryName.isBlank()) {
            products = productService.findAll();
        } else {
            products = productService.findByCategory(categoryName);
        }

        model.addAttribute(WebConstants.ModelAttributes.PRODUCTS, products);

        return "fragments/product-list :: productList";
    }
}
