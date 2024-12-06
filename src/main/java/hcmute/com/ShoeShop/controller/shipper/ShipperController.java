package hcmute.com.ShoeShop.controller.shipper;

import hcmute.com.ShoeShop.dto.OrderDetailDto;
import hcmute.com.ShoeShop.dto.OrderPaymentDto;
import hcmute.com.ShoeShop.dto.OrderStaticDto;
import hcmute.com.ShoeShop.entity.Order;
import hcmute.com.ShoeShop.entity.Shipment;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.services.imp.OrderDetailServiceImpl;
import hcmute.com.ShoeShop.services.imp.OrderServiceImpl;
import hcmute.com.ShoeShop.services.imp.ShipmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/shipper")
public class ShipperController {
    @Autowired
    ShipmentService shipmentService;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderDetailServiceImpl orderDetailService;

    @GetMapping("/order/list")
    public String orderList(@RequestParam(value = "page-size", defaultValue = "5")int pagesize,
                            @RequestParam(name = "page-num", defaultValue = "0") int pageNum,
            Model model, HttpSession session) {
        Pageable pageable = PageRequest.of(pageNum, pagesize);

        model.addAttribute("title", "Order");

        Users user = (Users) session.getAttribute("user");
        Page<Shipment> shipmentPage = shipmentService.findByShipperID(2, pageable);
        Page<Order> orderList = shipmentPage.map(Shipment::getOrder);
        model.addAttribute("listOrder", orderList);

        int totalPages = orderList.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        OrderStaticDto orderStaticDto = orderService.getStatic(orderList.stream().toList());
        model.addAttribute("static", orderStaticDto);
        return "shipper/orders-list";
    }

    @GetMapping("/order/detail/{id}")
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

        return "shipper/order-detail";
    }
}
