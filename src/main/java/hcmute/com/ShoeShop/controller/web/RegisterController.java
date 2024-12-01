package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.EmailService;
import hcmute.com.ShoeShop.services.RoleService;
import hcmute.com.ShoeShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    EmailService emailService;

    private String verificationCode = null;

    @GetMapping("/register")
    public String registerUser(){
        return "web/register";
    }
    @PostMapping("/register")
    public String processRegister(@RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("fullname") String fullname,
                                  @RequestParam("address") String address,
                                  @RequestParam("verity") String verity,
                                  Model model) {
        try{
            Users user = new Users();
            user.setEmail(email);
            user.setFullname(fullname);
            user.setAddress(address);
            user.setPass(password);

            user.setRole(roleService.findRoleById(3));

            if(userService.findUserByEmail(email) == null ) {
                if(verificationCode.equals(verity)){
                    userService.saveUser(user);
                    System.out.println("Register successful");
                    System.out.println(userService.findAll());
                    return "redirect:/login";
                }
                else {
                    model.addAttribute("mess", "Verity code not match");
                    verificationCode = null;
                    return "web/register";
                }
            }
            else {
                model.addAttribute("mess", "Email already in use");
                verificationCode = null;
                return "web/register";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            verificationCode = null;
            model.addAttribute("mess", "Error");
            return "web/register";
        }
    }

    @PostMapping("/send-code")
    @ResponseBody
    public String sendVerificationCode(@RequestParam("email") String email) {
        try {
            if (userService.findUserByEmail(email) == null) {
                verificationCode = generateRandomCode();
                // Gửi email với mã xác minh
                emailService.sendVerificationCode(email, verificationCode);
                return "success";
            } else {
                return "email_exists";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    private String generateRandomCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // bắt đầu từ 100000 tới 999999
        return String.valueOf(code);
    }
}
