package hcmute.com.ShoeShop.controller;

import hcmute.com.ShoeShop.dto.ProductDto;
import hcmute.com.ShoeShop.entity.*;
import hcmute.com.ShoeShop.services.imp.ProductService;
import hcmute.com.ShoeShop.services.imp.ProductDetailService;
import hcmute.com.ShoeShop.services.StorageService;
import hcmute.com.ShoeShop.services.imp.CategoryService;
import hcmute.com.ShoeShop.services.imp.WishlistService;
import hcmute.com.ShoeShop.utlis.Constant;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WishlistService wishListService;

    @GetMapping("/insertProductPage")
    public String insertProductPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", new ProductDto());
        return "admin/products/product-add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "product") ProductDto productDto,
                       @RequestParam(name = "image", required = false) MultipartFile image,
                       @RequestParam Map<String, String> productDetails) {
        productService.saveProduct(productDto, image, productDetails);
        return "redirect:/product";
    }

    @GetMapping("/updateProduct/{id}")
    public String getFormUpdateProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.getProductDtoById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/product-edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "product") ProductDto productDto,
                         @RequestParam(name = "image", required = false) MultipartFile image,
                         @RequestParam Map<String, String> productDetails) {
        productService.updateProduct(productDto, image, productDetails);
        return "redirect:/product";
    }

    @GetMapping("/deleteProduct/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product";
    }

    @GetMapping("/web")
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 Model model) {
        Page<Product> productPage = productService.getPaginatedProducts(PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(@PathVariable long id, ModelMap model, Rating rating, HttpSession session) {
        Users u = (Users) session.getAttribute(Constant.SESSION_USER);
        if(u==null)
            return "redirect:/login";
        model.addAttribute("user", u);
        List<Product> wishlist = wishListService.getWishlist(u.getId());
        model.addAttribute("wishlist", wishlist);
        Product product = productService.getProductById(id);
        List<ProductDetail> productDetails = productDetailService.findProductByProductId(id);
        model.addAttribute("productDetails", productDetails);
        model.addAttribute("product", product);
        model.addAttribute("rating", rating);
        return "user/single-product";
    }

    @GetMapping("")
    public String getAllProduct(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size,
                                Model model) {
        Page<Product> productPage = productService.getPaginatedProducts(PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "admin/products/product-list";
    }
}
