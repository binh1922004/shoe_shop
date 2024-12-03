package hcmute.com.ShoeShop.repository;

import hcmute.com.ShoeShop.entity.Product;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SpringBootApplication
public interface ProductRepository extends JpaRepository<Product, Long> {
}
