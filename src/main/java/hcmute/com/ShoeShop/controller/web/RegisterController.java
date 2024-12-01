package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.RoleService;
import hcmute.com.ShoeShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping("/register")
    public String registerUser(){
        return "web/register";
    }
    @PostMapping("/register")
    public String processRegister(@RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("fullname") String fullname,
                                  @RequestParam("address") String address,
                                  Model model) {
        try{
            Users user = new Users();
            user.setEmail(email);
            user.setFullname(fullname);
            user.setAddress(address);
            user.setPass(password);
            user.setRole(roleService.findRoleById(3));

            if(userService.findUserByEmail(email) == null){
                userService.saveUser(user);
                System.out.println("Register successful");
                System.out.println(userService.findAll());
                return "redirect:/login";
            }
            else {
                model.addAttribute("mess", "Email already in use");
                return "web/register";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("mess", "Error");
            return "web/register";
        }
    }
}
