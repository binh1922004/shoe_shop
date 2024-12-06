package hcmute.com.ShoeShop.controller;

import hcmute.com.ShoeShop.dto.CategoryDto;
import hcmute.com.ShoeShop.entity.Category;
import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.repository.CategoryRepository;
import hcmute.com.ShoeShop.services.imp.ProductService;
import hcmute.com.ShoeShop.services.imp.WishlistService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("")
    public String category(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories/category-list";
    }
    @GetMapping("/insertCategoryPage")
    public String insertProductPage(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/categories/category-add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "category") Category category) {
        categoryRepository.save(category);

        return "redirect:/category";
    }

    @GetMapping("/updateCategory/{id}")
    public String getFormUpdateCategory(@PathVariable("id") Long id, Model model){
        Category category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);
        model.addAttribute("id", category.getId());
        return "admin/categories/category-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "category") Category category) {
        categoryRepository.save(category);
        return "redirect:/category";
    }
    @GetMapping("/deleteCategory/{id}")
    public String delete(@PathVariable("id") Long id){
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }

    @GetMapping("/{id}")
    public String getCategoryContent(@PathVariable("id") Long id, ModelMap model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size, HttpSession session){
        Category cate = categoryRepository.findById(id).get();
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if(cate == null){

            return "Can not find by category ID: " + id;
        }
        List<Product> wishlist = wishlistService.getWishlist(u.getId());
        Page<Product> products = productService.getPaginatedProductsByCategory(id, PageRequest.of(page, size));
        model.addAttribute("products", products);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("currentPage", products.getNumber());
        model.addAttribute("totalPages", products.getTotalPages());
        return "user/product-content";
    }

}