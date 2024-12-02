package hcmute.com.ShoeShop.controller.user;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    @GetMapping("/shop")
    public String userHome(HttpSession session, Model model) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if(u!=null) {
            model.addAttribute("user", u);
            return "redirect:/shop";
        }
        else {
            return "redirect:/login";
        }
    }
}
