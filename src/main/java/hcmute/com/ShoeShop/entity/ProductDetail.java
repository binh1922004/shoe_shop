package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProductDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        private int size;
        @Column(name = "price_add")
        private double priceadd;
}
