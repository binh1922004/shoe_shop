package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Cart;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.imp.CartService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        // lay thong tin nguoi dung tu session
        Users logginedUser = (Users) session.getAttribute(Constant.SESSION_USER);
        if (logginedUser == null) {
            return "redirect:/login";
        }

        String email = logginedUser.getEmail();

        Cart cart = cartService.getCartByUser(email);

        // Truyền thông báo nếu có
        String alert = (String) model.asMap().get("alert");

        model.addAttribute("alert", alert);

        model.addAttribute("cart", cart);

        return "user/cart";
    }

    @PostMapping("/add")
    public String addCart(@RequestParam int productDetailId, @RequestParam int quantity, HttpSession session,
                          RedirectAttributes redirectAttributes) {
        // lay thong tin nguoi dung tu session
        Users logginedUser = (Users) session.getAttribute(Constant.SESSION_USER);
        if (logginedUser == null) {
            return "redirect:/login";
        }

        String email = logginedUser.getEmail();

        // goi ham add to cart
        cartService.addToCart(email, productDetailId, quantity);

        String alert = "Thêm vào giỏ hàng thành công";
        // Dùng RedirectAttributes để giữ lại dữ liệu sau redirect
        redirectAttributes.addFlashAttribute("alert", alert);

        // return ve trang view gio hang
        return "redirect:/cart/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteCart(@PathVariable("id") long cartDetailId, HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Users logginedUser = (Users) session.getAttribute(Constant.SESSION_USER);
        if (logginedUser == null) {
            return "redirect:/login";
        }
        String email = logginedUser.getEmail();

        // xoa khoi gio hang
        cartService.removeFromCart(email, cartDetailId);

        String alert = "Xóa khỏi giỏ hàng thành công";
        // Dùng RedirectAttributes để giữ lại dữ liệu sau redirect
        redirectAttributes.addFlashAttribute("alert", alert);

        // tra ve view
        return "redirect:/cart/view";
    }

    @PostMapping("/edit/{id}")
    public String editCart(@PathVariable("id") long cartDetailId, @RequestParam int quantity,
                           HttpSession session, RedirectAttributes redirectAttributes) {

        // lay thong tin khach hang
        Users logginedUser = (Users) session.getAttribute(Constant.SESSION_USER);

        if (logginedUser == null) {

            return "redirect:/login";
        }

        String email = logginedUser.getEmail();

        // update gio hang
        cartService.updateMyCart(email, cartDetailId, quantity);

        String alert = "Chỉnh sửa giỏ hàng thành công";
        // Dùng RedirectAttributes để giữ lại dữ liệu sau redirect
        redirectAttributes.addFlashAttribute("alert", alert);

        return "redirect:/cart/view";
    }


}
