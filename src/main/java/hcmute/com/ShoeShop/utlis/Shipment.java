package hcmute.com.ShoeShop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Shipment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @OneToOne
        @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
        private Order order;

        @ManyToOne
        @JoinColumn(name = "shipper_id", referencedColumnName = "id", nullable = false)
        private Users shipper;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "ENUM('IN_STOCK', 'SHIPPED', 'DELIVERED', 'CANCEL')", nullable = false)
        private ShipmentStatus status;
}
