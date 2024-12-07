package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.dto.ProductDto;
import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.ProductDetail;
import hcmute.com.ShoeShop.repository.ProductRepository;
import hcmute.com.ShoeShop.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    @Lazy
    private ProductDetailService productDetailService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WishlistService wishlistService;
    // Save product
    @Transactional
    public void saveProduct(ProductDto productDto, MultipartFile image, Map<String, String> productDetails) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setVoucher(productDto.getVoucher());
        product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()));

        if (image != null) {
            String fileName = "pro_" + System.currentTimeMillis();
            fileName = storageService.uploadFile(image, fileName);
            product.setImage(fileName);
        }

        productRepository.save(product);

        for (int size = 38; size <= 45; size++) {
            String priceaddStr = productDetails.get("productDetails[" + size + "].priceadd");
            if (priceaddStr != null && !priceaddStr.isEmpty()) {
                double priceadd = Double.parseDouble(priceaddStr);

                ProductDetail productDetail = new ProductDetail();
                productDetail.setProduct(product);
                productDetail.setSize(size);
                productDetail.setPriceadd(priceadd);

                productDetailService.save(productDetail);
            }
        }
    }
    public Page<Product> findAllPage(Pageable pageable) {
        return productRepository.findAll(pageable); // Đây là phương thức đúng
    }

    // Update product
    @Transactional
    public void updateProduct(ProductDto productDto, MultipartFile image, Map<String, String> productDetails) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setVoucher(productDto.getVoucher());
        product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()));

        if (image != null) {
            String fileName = "pro_" + System.currentTimeMillis();
            fileName = storageService.uploadFile(image, fileName);
            product.setImage(fileName);
        }

        productRepository.save(product);

        for (int size = 38; size <= 45; size++) {
            String priceaddStr = productDetails.get("productDetails[" + size + "].priceadd");
            if (priceaddStr != null && !priceaddStr.isEmpty()) {
                double priceadd = Double.parseDouble(priceaddStr);

                ProductDetail productDetail = productDetailService.findByProductAndSize(product, size);
                if (productDetail == null) {
                    productDetail = new ProductDetail();
                    productDetail.setProduct(product);
                    productDetail.setSize(size);
                }
                productDetail.setPriceadd(priceadd);
                productDetailService.save(productDetail);
            }
        }
    }
    // Delete product
    @Transactional
    public void deleteProduct(Long id) {
        wishlistService.deleteByProductId(id);
        //khong xoa -> isdelete = true
        productRepository.deleteById(id);
    }

    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Paginated products
    public Page<Product> getPaginatedProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    // Get product DTO by ID for editing
    public ProductDto getProductDtoById(Long id) {
        Product product = getProductById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setVoucher(product.getVoucher());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getType());
        return productDto;
    }

    public Page<Product> getPaginatedProductsByCategory(long categoryId, PageRequest pageRequest) {
        return productRepository.findAllByCategoryId(categoryId, pageRequest);
    }

    public List<Product> getTopRatedProducts() {
        List<Product> ratedProducts = productRepository.findTop20ByAvgRating();

        if(ratedProducts.size() > 20){
            ratedProducts = ratedProducts.subList(0, 20);
        }
        return ratedProducts;
    }

}
