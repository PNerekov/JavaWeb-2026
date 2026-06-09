package hr.algebra.javaweb.javaweb2026.controller.mvc.admin;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.dto.ProductDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CategoryService;
import hr.algebra.javaweb.javaweb2026.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/products")
@AllArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String showProducts(Model model){
        model.addAttribute(WebConstants.ModelAttributes.PRODUCTS, productService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.PRODUCT_DTO, new ProductDTO());

        return WebConstants.Views.ADMIN_PRODUCTS;
    }

    @PostMapping("/create-async")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createProductAsync(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(createValidationErrorResponse(bindingResult));
        }
        try {
            ProductDTO savedProduct = productService.save(productDTO);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put(WebConstants.Values.SUCCESS, true);
            response.put("message", "Product created successfully");
            response.put("product", savedProduct);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new LinkedHashMap<>();
            Map<String, String> errors = new LinkedHashMap<>();

            errors.put("name", e.getMessage());

            response.put(WebConstants.Values.SUCCESS, false);
            response.put("errors", errors);

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam String productName,
                                RedirectAttributes redirectAttributes) {
        productService.deleteByName(productName);

        redirectAttributes.addFlashAttribute(
                WebConstants.ModelAttributes.SUCCESS_MESSAGE,
                "Product deleted successfully"
        );

        return WebConstants.Redirects.ADMIN_PRODUCTS;
    }

    @GetMapping("/edit")
    public String showEditProductForm(@RequestParam String productName, Model model){
        ProductDTO productDTO = productService.findByName(productName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + productName));

        model.addAttribute(WebConstants.ModelAttributes.PRODUCT_DTO, productDTO);
        model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.OLD_PRODUCT_NAME, productName);

        return WebConstants.Views.ADMIN_PRODUCT_EDIT;
    }

    @PostMapping("/edit")
    public String updateProduct(@RequestParam String oldProductName,
                                @Valid @ModelAttribute(WebConstants.ModelAttributes.PRODUCT_DTO) ProductDTO productDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
            model.addAttribute(WebConstants.ModelAttributes.OLD_PRODUCT_NAME, oldProductName);

            return WebConstants.Views.ADMIN_PRODUCT_EDIT;
        }

        try{
            productService.updateByName(oldProductName, productDTO);

            redirectAttributes.addFlashAttribute(
                    WebConstants.ModelAttributes.SUCCESS_MESSAGE,
                    "Product updated successfully"
            );

            return WebConstants.Redirects.ADMIN_PRODUCTS;
        }catch (IllegalArgumentException e){
            model.addAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, e.getMessage());
            model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
            model.addAttribute(WebConstants.ModelAttributes.OLD_PRODUCT_NAME, oldProductName);

            return WebConstants.Views.ADMIN_PRODUCT_EDIT;
        }


    }

    private Map<String, Object> createValidationErrorResponse(BindingResult bindingResult){
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> errors = new LinkedHashMap<>();

        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        response.put(WebConstants.Values.SUCCESS, false);
        response.put("errors", errors);

        return response;
    }
}
