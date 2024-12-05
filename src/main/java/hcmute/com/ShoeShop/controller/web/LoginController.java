package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.imp.UserService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(HttpServletRequest req, Model model) {
        Cookie[] cookies = req.getCookies();
        String username = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constant.COOKIE_REMEMBER)) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        if (username != null) {
            model.addAttribute("username", username);
        }
        return "web/login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam(value = "rememberMe", required = false) Boolean rememberMe,
                            Model model, HttpSession session, HttpServletResponse response) {
        try{
            Users users = userService.findUserByEmail(username);
            if(users != null) {
                if (passwordEncoder.matches(password, users.getPass())) {
                    if (rememberMe != null && rememberMe) {
                        saveRemeberMe(response,users.getEmail());
                        System.out.println("yes");
                    }
                    else {
                        System.out.println("no");
                    }
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
        }
        catch (Exception e) {
            model.addAttribute("mess", "An error occurred. Please try again later.");
            e.printStackTrace();
        }
        return "web/login";
    }

    private void saveRemeberMe(HttpServletResponse resp, String username) {
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
        cookie.setMaxAge(60*60);
        cookie.setHttpOnly(true);  // Set cookie as HttpOnly for security
        cookie.setSecure(true);  // Set cookie to secure (use only over HTTPS)
        resp.addCookie(cookie);
    }
}
