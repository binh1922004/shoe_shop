package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WaitingController {
    @GetMapping("/waiting")
    public String waitingPage(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute(Constant.SESSION_USER);//Lấy thông tin từ session
        System.out.println(loggedInUser.getFullname() + " " + loggedInUser.getId());
        if (loggedInUser != null) {
            int roleId = loggedInUser.getRole().getRoleId();
            if (roleId == 1) {
                return "user/my-account";
            } else if (roleId == 2) {
                return "user/my-account";
            } else if (roleId == 3) {
                return "redirect:/user/shop";
            } else {
                return "redirect:/login";
            }
        } else {
            System.out.println("No account");
            return "redirect:/login";
        }
    }
}
