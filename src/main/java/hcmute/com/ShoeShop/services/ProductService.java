package hcmute.com.ShoeShop.services;

import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product getProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }
}