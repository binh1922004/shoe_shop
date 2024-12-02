package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.repository.OrderRepository;
import hcmute.com.ShoeShop.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
        @Autowired
        OrderRepository orderRepository;

        @Override
        public List<Order> findAll() {
                return orderRepository.findAll();
        }
}
