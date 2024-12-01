package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderDetail {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "order_id", nullable = false)
        private Order order;

        @ManyToOne
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        private int quantity;

        private double price;
}
