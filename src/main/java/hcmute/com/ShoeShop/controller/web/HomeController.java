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
}
