package hcmute.com.ShoeShop.controller.admin;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @GetMapping
    public String adminHome(RedirectAttributes redirectAttributes, HttpSession session) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if(u == null) {
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("user", u);
        return "admin/admin_home";
    }
}
