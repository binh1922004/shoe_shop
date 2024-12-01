package hcmute.com.ShoeShop.controller;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.UserService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "web/login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("user-name") String username,
                            @RequestParam("user-password") String password, Model model,
                            HttpSession session) {
        Users users = userService.findUserByEmail(username);
        if(users != null) {
            //System.out.println(users.getFullname());
            if (password.equals(users.getPass())) {
                System.out.println("Successfull");
                // Lưu thông tin người dùng vào session
                session.setAttribute(Constant.SESSION_USER, users);
                return "redirect:/waiting";
            } else {
                model.addAttribute("mess", "Incorrect password");
            }
        }
        else {
            System.out.println("Not found");
            model.addAttribute("mess", "Not found account");
        }
        return "web/login";
    }
}
