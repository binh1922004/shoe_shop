package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class ProductDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @NotNull(message = "Product cannot be null")
        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        @NotNull(message = "Size cannot be null")
        private int size;

        @Min(value = 0, message = "Price add must be greater than or equal to 0")
        @Column(name = "price_add")
        private double priceadd;
}
