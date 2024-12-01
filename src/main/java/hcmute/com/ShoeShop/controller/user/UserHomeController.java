package hcmute.com.ShoeShop.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    @GetMapping("/shop")
    public String userHome(){
        return "user/shop";
    }
}
