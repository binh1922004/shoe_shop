package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.entity.Shipment;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.repository.OrderRepository;
import hcmute.com.ShoeShop.repository.ShipmentRepository;
import hcmute.com.ShoeShop.repository.UserRepository;
import hcmute.com.ShoeShop.utlis.ShipmentStatus;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShipmentService {
        @Autowired
        ShipmentRepository shipmentRepository;
        @Autowired
        OrderRepository orderRepository;
        @Autowired
        UserRepository userRepository;
        public Shipment findShipmentByOrderId(int orderId){
                return shipmentRepository.findShipmentByOrderId(orderId);
        }

        public void insertShipment(int orderid, int userid) {
                Shipment shipment = new Shipment();

                Order order = orderRepository.findOrderById(orderid);
                Users user = userRepository.findUsersById(userid);

                shipment.setOrder(order);
                shipment.setShipper(user);
                shipment.setNote("");
                shipment.setUpdatedDate(new Date());
                shipment.setStatus(ShipmentStatus.SHIPPED);

                shipmentRepository.save(shipment);

                order.setStatus(ShipmentStatus.SHIPPED);

                orderRepository.save(order);
        }
}
