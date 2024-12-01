package hcmute.com.ShoeShop.entity;

import hcmute.com.ShoeShop.utlis.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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

        @Column(name = "update_date")
        private Date lastupdate;

//        @Enumerated(EnumType.STRING)
//        @Column(name = "status", columnDefinition = "ENUM('IN_STOCK', 'SHIPPED', 'DELIVERED', 'CANCEL')", nullable = false)
//        private ShipmentStatus status;
}
