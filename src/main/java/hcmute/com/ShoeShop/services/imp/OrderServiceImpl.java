package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.dto.OrderStaticDto;
import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.repository.OrderRepository;
import hcmute.com.ShoeShop.services.IOrderService;
import hcmute.com.ShoeShop.utlis.ShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
        @Autowired
        OrderRepository orderRepository;

        public Page<Order> findAll(Pageable pageable) {
                return orderRepository.findAll(pageable);
        }

        public Order findById(int orderId) {
                return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("can not find order"));
        }

        public void cancelOrder(int orderId) {
                Order order = findById(orderId);
                order.setStatus(ShipmentStatus.CANCEL);
                orderRepository.save(order);
        }

        public OrderStaticDto getStatic(){
                OrderStaticDto orderStaticDto = new OrderStaticDto();
                orderStaticDto.setShipping(orderRepository.countByStatus(ShipmentStatus.SHIPPED));
                orderStaticDto.setCancel(orderRepository.countByStatus(ShipmentStatus.CANCEL));
                orderStaticDto.setInStock(orderRepository.countByStatus(ShipmentStatus.IN_STOCK));
                orderStaticDto.setRollBack(orderRepository.countByStatus(ShipmentStatus.ROLLBACK));
                orderStaticDto.setDelivered(orderRepository.countByStatus(ShipmentStatus.DELIVERED));

                return orderStaticDto;
        }
}
