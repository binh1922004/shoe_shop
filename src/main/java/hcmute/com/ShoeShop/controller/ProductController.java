package hcmute.com.ShoeShop.controller;

import hcmute.com.ShoeShop.dto.ProductDto;
import hcmute.com.ShoeShop.entity.*;
import hcmute.com.ShoeShop.repository.CategoryRepository;
import hcmute.com.ShoeShop.repository.ProductRepository;
import hcmute.com.ShoeShop.services.imp.ProductDetailService;
import hcmute.com.ShoeShop.services.imp.ProductService;
import hcmute.com.ShoeShop.services.StorageService;
import hcmute.com.ShoeShop.services.imp.ProductService;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private WishlistService wishListService;

    @GetMapping("/insertProductPage")
    public String insertProductPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);
        return "admin/products/product-add";

    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "product") ProductDto productDto, @RequestParam(name = "image", required = false)  MultipartFile image) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setVoucher(productDto.getVoucher());
        Category category = categoryRepository.findById(productDto.getCategoryId()).get();
        product.setCategory(category);

        if(image != null) {
            String fileName = "pro_" + LocalDateTime.now().toString();
            fileName = storageService.uploadFile(image, fileName);
            product.setImage(fileName);
        }

        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/updateProduct/{id}")
    public String getFormUpdateProduct(@PathVariable("id") Long id, Model model){
        Product product = productRepository.findById(id).get();

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setVoucher(product.getVoucher());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getType());

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("product", productDto);
        model.addAttribute("id", productDto.getId());
        return "admin/products/product-edit";

    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "product") ProductDto productDto, @RequestParam(name = "image", required = false)  MultipartFile image) {
        Product product = productRepository.findById(productDto.getId()).get();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setVoucher(productDto.getVoucher());

        if(image != null) {
            String fileName = "pro_" + LocalDateTime.now().toString();
            fileName = storageService.uploadFile(image, fileName);
            product.setImage(fileName);
        }

        Category category = categoryRepository.findById(productDto.getCategoryId()).get();
        product.setCategory(category);
        productRepository.save(product);

        return "redirect:/product";
    }

    @GetMapping("/deleteProduct/{id}")
    public String delete(@PathVariable("id") Long id){
        productRepository.deleteById(id);
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
        model.addAttribute("rating",rating);
        return "user/single-product";
    }

    @GetMapping("")
    public String getAllProduct(Model model){
        List<Product> list = productService.getAllProducts();
        model.addAttribute("products", list);
        return "admin/products/product-list";
    }
}