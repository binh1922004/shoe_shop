package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.Role;
import hcmute.com.ShoeShop.services.imp.ProductService;
import hcmute.com.ShoeShop.services.imp.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 Model model) {

        List<String> role = List.of("admin","manager","user","shipper");
        for(int i = 0; i < role.size(); i++){
            if(roleService.findRoleByName(role.get(i))==null){
                Role ro = new Role();
                ro.setRoleName(role.get(i));
                roleService.insertRole(ro);
            }
        }

        Page<Product> productPage = productService.getPaginatedProducts(PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "web/index";
    }
}
