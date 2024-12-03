package hcmute.com.ShoeShop.controller.user;

import hcmute.com.ShoeShop.entity.Category;
import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.imp.CategoryService;
import hcmute.com.ShoeShop.services.imp.ProductService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/shop")
    public String userHome(HttpSession session, Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "12") int size) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        List<Category> categories = categoryService.findAll();
        int cateCount = categoryService.count();
        if(u!=null) {
            if(categories!=null && cateCount>0) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Product> productsPage = productService.findAllPage(pageable);

                model.addAttribute("cate", categories);
            }
            else {
                model.addAttribute("cate", null);
            }
            model.addAttribute("user", u);
            return "user/shop";
        }
        else {
            return "redirect:/login";
        }
    }
}
