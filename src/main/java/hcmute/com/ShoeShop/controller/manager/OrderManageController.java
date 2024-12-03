package hcmute.com.ShoeShop.controller.manager;


import hcmute.com.ShoeShop.dto.OrderDetailDto;
import hcmute.com.ShoeShop.dto.OrderPaymentDto;
import hcmute.com.ShoeShop.dto.ShipperDto;
import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.entity.Shipment;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.imp.OrderDetailServiceImpl;
import hcmute.com.ShoeShop.services.imp.OrderServiceImpl;
import hcmute.com.ShoeShop.services.imp.ShipmentService;
import hcmute.com.ShoeShop.services.imp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class OrderManageController {
        @Autowired
        OrderServiceImpl orderService;
        @Autowired
        OrderDetailServiceImpl orderDetailService;
        @Autowired
        UserService userService;
        @Autowired
        ShipmentService shipmentService;
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

                //add order to view
                Order order = orderService.findById(orderId);
                model.addAttribute("order", order);

                //add user detail to view
                Users user = order.getUser();
                model.addAttribute("user", user);

                //add shipper to view
                Shipment shipment = shipmentService.findShipmentByOrderId(orderId);
                model.addAttribute("shipper", shipment);

                return "manager/order/order-detail";
        }

        @GetMapping("/order-shipping")
        public String addShipping(@RequestParam("orderid") int orderid,
                                  @RequestParam("userid") int userid){
                shipmentService.insertShipment(orderid, userid);
                return "redirect:/manager/order/" + orderid;
        }
}
