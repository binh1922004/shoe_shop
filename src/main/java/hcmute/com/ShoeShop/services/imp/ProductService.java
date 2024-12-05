package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.repository.CategoryRepository;
import hcmute.com.ShoeShop.repository.ProductDetailRepository;
import hcmute.com.ShoeShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }
    public Page<Product> findAllPage(Pageable pageable) {
        return productRepository.findAll(pageable); // Đây là phương thức đúng
    }

    public Product getProductByIdWithDetails(long id) {
        return productRepository.findById(id).map(product -> {
                    product.setDetails(productDetailRepository.findByProductId(id));
                    return product;
                })
                .orElse(null);
    }
}

