package hcmute.com.ShoeShop.entity;

import jakarta.validation.constraints.*;
import hcmute.com.ShoeShop.utlis.ShipmentStatus;
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
        @NotNull(message = "Order cannot be null")
        private Order order;

        @ManyToOne
        @JoinColumn(name = "shipper_id", referencedColumnName = "id", nullable = false)
        @NotNull(message = "Shipper must not be null")
        private Users shipper;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "ENUM('IN_STOCK', 'SHIPPED', 'DELIVERED', 'CANCEL')", nullable = false)
        @NotNull(message = "Shipment status must not be null")
        private ShipmentStatus status;
}
