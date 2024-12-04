package hcmute.com.ShoeShop.repository;

import hcmute.com.ShoeShop.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
        public Shipment findShipmentByOrderId(int orderId);
}
