package hcmute.com.ShoeShop.controller;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WaitingController {
    @GetMapping("/waiting")
    public String waitingPage(HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute(Constant.SESSION_USER);//Lấy thông tin từ session
        System.out.println(loggedInUser.getFullname() + " "+loggedInUser.getId());
        int roleId = loggedInUser.getId();
        if (roleId == 1) {
            return "user/my-account";
        }
        else if (roleId == 2) {
            return "manager/home";
        }
        else if (roleId == 3) {
            return "user/home";
        }
        else{
            return "web/login";
        }
    }

}
