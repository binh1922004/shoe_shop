package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.ProductDetail;
import hcmute.com.ShoeShop.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailRepository productDetailRepository;


    public List<ProductDetail> findProductByProductId(long productId) {
        List<ProductDetail> productDetails = productDetailRepository.findByProductId(productId);
        return productDetails;
    }


}
