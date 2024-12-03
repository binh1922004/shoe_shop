package hcmute.com.ShoeShop.controller.user;
import hcmute.com.ShoeShop.entity.*;
import hcmute.com.ShoeShop.services.imp.*;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.imp.ProductService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/shop")
    public String userHome(HttpSession session, Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "6") int size) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if (u != null) {
            List<Category> categories = categoryService.findAll();

            int cateCount = categoryService.count();
            if (categories != null && cateCount > 0) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Product> productsPage = productService.findAllPage(pageable);

                model.addAttribute("cate", categories);
            } else {
                model.addAttribute("cate", null);
            }
            model.addAttribute("user", u);
            Page<Product> productPage = productService.getPaginatedProducts(PageRequest.of(page, size));

            model.addAttribute("products", productPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());
            return "user/shop";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/my_account")
    public String myAccount(HttpSession session, Model model) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if (u != null) {
            model.addAttribute("user", u);
            return "user/my-account";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("currentPassword") String currentPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {

            setMess(model);

            Users u = (Users) session.getAttribute(Constant.SESSION_USER);
            Users us = userService.findUserByEmail(email);
            if (us == null) {
                return "redirect:/login";
            }
            model.addAttribute("user", us);
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "New Password and Confirm Password do not match.");
                redirectAttributes.addFlashAttribute("error", "New Password and Confirm Password do not match.");
                return "user/my-account";
            }
            if(currentPassword.length()>0){
                if (u.getPass().equals(currentPassword)) {
                    u.setPass(newPassword);
                    userService.saveUser(u);
                    model.addAttribute("success", "Password has been reset successfully.");
                    return "user/my-account";
                } else if (!u.getPass().equals(currentPassword)){
                    model.addAttribute("error", "Current password do not match .");
                    return "user/my-account";
                }
            }
            return "user/my-account";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
    }
    @PostMapping("/changeInformation")
    public String changMyProfile(@RequestParam("email") String email,
                                 @RequestParam("fullname") String fullname,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("address") String adr,
                                 HttpSession session, Model model){
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if(u == null){
            return "redirect:/login";
        }
        model.addAttribute("user", u);
        try{

            setMess(model);

            u.setFullname(fullname);
            u.setPhone(phone);
            u.setAddress(adr);
            userService.saveUser(u);
            model.addAttribute("success1", "Profile has been changed successfully.");
            return "user/my-account";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @PostMapping("/addresses")
    public String addresses(@RequestParam("newAddress") String newAddr,
                            HttpSession session, RedirectAttributes redirectAttributes){
        try{
            Users u = (Users) session.getAttribute(Constant.SESSION_USER);
            if(u == null){
                return "redirect:/login";
            }
            redirectAttributes.addFlashAttribute("user", u);
            Address adr = new Address();
            adr.setAddress(newAddr);
            adr.setUser(u);
            adr.setIsDefault(false);
            addressService.save(adr);
            return "redirect:/user/my_account";

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    public void setMess(Model model){
        model.addAttribute("error", null);
        model.addAttribute("success", null);
        model.addAttribute("success1", null);
    }
}
