package hcmute.com.ShoeShop.controller.manager;


import hcmute.com.ShoeShop.dto.OrderDetailDto;
import hcmute.com.ShoeShop.dto.OrderPaymentDto;
import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.entity.OrderDetail;
import hcmute.com.ShoeShop.services.IOrderService;
import hcmute.com.ShoeShop.services.imp.OrderDetailServiceImpl;
import hcmute.com.ShoeShop.services.imp.OrderServiceImpl;
import hcmute.com.ShoeShop.services.imp.UserService;
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
        @Autowired
        UserService userService;

        @GetMapping
        public String getIn(){
                return "manager/order/order-detail";
        }


        @GetMapping("/orders")
        public String getAllOrders(Model model){

                model.addAttribute("title", "Order");

                List<Order> listOder = orderService.findAll();
                model.addAttribute("listOrder", listOder);
                return "manager/order/orders-list";
        }

        @GetMapping("/order/{id}")
        public String getOrderDetail(@PathVariable("id") int orderId, Model model){
                model.addAttribute("title", "Order detail");

                //add list order detail to view
                List<OrderDetailDto> list = orderDetailService.findAllOrderDetailById(orderId);
                model.addAttribute("list", list);

                //add payment detail to view
                OrderPaymentDto orderPaymentDto = orderDetailService.getOrderPayment(orderId);
                model.addAttribute("payment", orderPaymentDto);
                return "manager/order/order-detail";
        }
}
