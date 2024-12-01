package hcmute.com.ShoeShop.controller;

import hcmute.com.ShoeShop.dto.ProductDto;
import hcmute.com.ShoeShop.entity.Category;
import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.repository.CategoryRepository;
import hcmute.com.ShoeShop.repository.ProductRepository;
import hcmute.com.ShoeShop.services.ProductService;
import hcmute.com.ShoeShop.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private StorageService storageService;

//    @GetMapping("")
//    public String productPage(Model model) {
//        model.addAttribute("products", productRepository.findAll());
//        return "/admin/products/product-list";
//    }
    @GetMapping("/insertProductPage")
    public String insertProductPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);
        return "webapp/admin/products/product-add";

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
        return "webapp/admin/products/product-edit";

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


    @GetMapping("")
    public String productPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "webapp/admin/products/product-list";
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "webapp/user/single-product";
    }

}