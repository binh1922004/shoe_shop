package hcmute.com.ShoeShop.controller.manager;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class ManagerHomeController {

    @GetMapping
    public String managerHome(RedirectAttributes redirectAttributes, HttpSession session) {
        return "manager/manager_home";
    }

}
