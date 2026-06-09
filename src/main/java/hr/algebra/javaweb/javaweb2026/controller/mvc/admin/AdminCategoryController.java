package hr.algebra.javaweb.javaweb2026.controller.mvc.admin;

import hr.algebra.javaweb.javaweb2026.constants.WebConstants;
import hr.algebra.javaweb.javaweb2026.dto.CategoryDTO;
import hr.algebra.javaweb.javaweb2026.exeptions.ResourceNotFoundException;
import hr.algebra.javaweb.javaweb2026.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public String showCategories(Model model){
        model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
        model.addAttribute(WebConstants.ModelAttributes.CATEGORY_DTO, new CategoryDTO());

        return WebConstants.Views.ADMIN_CATEGORIES;
    }

    @PostMapping("/create")
    public String createCategory(
            @Valid @ModelAttribute(WebConstants.ModelAttributes.CATEGORY_DTO) CategoryDTO categoryDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());
            return WebConstants.Views.ADMIN_CATEGORIES;
        }

        try{
            categoryService.save(categoryDTO);

            redirectAttributes.addFlashAttribute(
                    WebConstants.ModelAttributes.SUCCESS_MESSAGE,
                    "Category created successfully"
            );

            return WebConstants.Redirects.ADMIN_CATEGORIES;
        }catch (IllegalArgumentException e){
            model.addAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, e.getMessage());
            model.addAttribute(WebConstants.ModelAttributes.CATEGORIES, categoryService.findAll());

            return WebConstants.Views.ADMIN_CATEGORIES;
        }
    }

    @GetMapping("/edit")
    public String showEditCategoryForm(@RequestParam String categoryName, Model model){
        CategoryDTO categoryDTO = categoryService.findByName(categoryName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));

        model.addAttribute(WebConstants.ModelAttributes.CATEGORY_DTO, categoryDTO);
        model.addAttribute(WebConstants.ModelAttributes.OLD_CATEGORY_NAME, categoryName);

        return WebConstants.Views.ADMIN_CATEGORY_EDIT;
    }

    @PostMapping("/edit")
    public String updateCategory(
            @RequestParam String oldCategoryName,
            @Valid @ModelAttribute(WebConstants.ModelAttributes.CATEGORY_DTO) CategoryDTO categoryDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute(WebConstants.ModelAttributes.OLD_CATEGORY_NAME, oldCategoryName);
            return WebConstants.Views.ADMIN_CATEGORY_EDIT;
        }

        try{
            categoryService.updateByName(oldCategoryName, categoryDTO);

            redirectAttributes.addFlashAttribute(
                    WebConstants.ModelAttributes.SUCCESS_MESSAGE,
                    "Category updated successfully"
            );

            return WebConstants.Redirects.ADMIN_CATEGORIES;
        }catch (IllegalArgumentException e){
            model.addAttribute(WebConstants.ModelAttributes.ERROR_MESSAGE, e.getMessage());
            model.addAttribute(WebConstants.ModelAttributes.OLD_CATEGORY_NAME, oldCategoryName);

            return WebConstants.Views.ADMIN_CATEGORY_EDIT;
        }
    }

    @PostMapping("/delete")
    public String deleteCategory(
            @RequestParam String categoryName,
            RedirectAttributes redirectAttributes
    ){
        try{
            categoryService.deleteByName(categoryName);

            redirectAttributes.addFlashAttribute(
                    WebConstants.ModelAttributes.SUCCESS_MESSAGE,
                    "Category deleted successfully"
            );
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute(
                    WebConstants.ModelAttributes.ERROR_MESSAGE,
                    e.getMessage()
            );
        }

        return WebConstants.Redirects.ADMIN_CATEGORIES;
    }
}
