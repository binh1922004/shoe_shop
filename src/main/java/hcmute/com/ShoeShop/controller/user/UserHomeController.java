package hcmute.com.ShoeShop.controller.user;

import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.ProductService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/shop")
    public String userHome(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "6") int size,
                           HttpSession session, Model model) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if(u!=null) {
            model.addAttribute("user", u);
            Page<Product> productPage = productService.getPaginatedProducts(PageRequest.of(page, size));
            model.addAttribute("products", productPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());
            return "user/shop";
        }
        else {
            return "redirect:/login";
        }
    }

}
