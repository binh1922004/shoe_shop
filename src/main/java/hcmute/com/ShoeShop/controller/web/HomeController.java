package hcmute.com.ShoeShop.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home()
    {
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
