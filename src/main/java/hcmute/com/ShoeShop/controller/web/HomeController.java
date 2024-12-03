package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.services.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 Model model) {
        Page<Product> productPage = productService.getPaginatedProducts(PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "web/index";
    }
    @GetMapping("/1")
    public String home1()
    {
        return "user/shop-list";
    }
    @GetMapping("/2")
    public String home2()
    {
        return "user/my-account";
    }
    @GetMapping("/3")
    public String home3()
    {
        return "user/shop";
    }

    @GetMapping("/4")
    public String home4()
    {
        return "user/contact";
    }
    @GetMapping("/5")
    public String home5()
    {
        return "user/blog";
    }
    @GetMapping("/6")
    public String home6()
    {
        return "user/blog-details";
    }
    @GetMapping("/7")
    public String home7()
    {
        return "user/blog";
    }
    @GetMapping("/8")
    public String home8()
    {
        return "user/wishlist";
    }
}
