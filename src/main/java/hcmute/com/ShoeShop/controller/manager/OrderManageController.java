package hcmute.com.ShoeShop.controller.manager;


import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.services.IOrderService;
import hcmute.com.ShoeShop.services.imp.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class OrderManageController {
        @Autowired
        OrderServiceImpl orderService;


        @GetMapping
        public String getIn(){
                return "manager/order/orders-list";
        }


        @GetMapping("/orders")
        public String getAllOrders(Model model){
                List<Order> listOder = orderService.findAll();
                model.addAttribute("listOrder", listOder);
                System.out.println(listOder.size());
                System.out.println(listOder.get(0));
                return "manager/order/orders-list";
        }
}
