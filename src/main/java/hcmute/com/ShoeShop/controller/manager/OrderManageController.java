package hcmute.com.ShoeShop.controller.manager;


import hcmute.com.ShoeShop.dto.OrderDetailDto;
import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.entity.OrderDetail;
import hcmute.com.ShoeShop.services.IOrderService;
import hcmute.com.ShoeShop.services.imp.OrderDetailServiceImpl;
import hcmute.com.ShoeShop.services.imp.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class OrderManageController {
        @Autowired
        OrderServiceImpl orderService;
        @Autowired
        OrderDetailServiceImpl orderDetailService;

        @GetMapping
        public String getIn(){
                return "manager/order/order-detail";
        }


        @GetMapping("/orders")
        public String getAllOrders(Model model){
                List<Order> listOder = orderService.findAll();
                model.addAttribute("listOrder", listOder);
                System.out.println(listOder.size());
                System.out.println(listOder.get(0));
                return "manager/order/orders-list";
        }

        @GetMapping("/order/{id}")
        public String getOrderDetail(@PathVariable("id") int orderId, Model model){
                List<OrderDetailDto> list = orderDetailService.findAllOrderDetailById(orderId);
                model.addAttribute("list", list);
                return "manager/order/order-detail";
        }
}
