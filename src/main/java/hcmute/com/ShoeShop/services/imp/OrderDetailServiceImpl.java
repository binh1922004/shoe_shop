package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.dto.OrderDetailDto;
import hcmute.com.ShoeShop.entity.OrderDetail;
import hcmute.com.ShoeShop.repository.OrderDetailRepository;
import hcmute.com.ShoeShop.services.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {
        @Autowired
        OrderDetailRepository orderDetailRepository;

        public Optional<OrderDetail> findByOrderId(int id) {
                return orderDetailRepository.findOrderDetailsByOrderId(id);
        }

        public List<OrderDetailDto> findAllOrderDetailById(int orderId){
                Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findOrderDetailsByOrderId(orderId);

                List<OrderDetailDto> listDetailRes = new ArrayList<>();
                if (optionalOrderDetail.isPresent()){
                        for (var item: optionalOrderDetail.stream().toList()){
                                OrderDetailDto detailDto = new OrderDetailDto();
                                //map data from OrderDetail to OrderDetailDTO
                                detailDto.setSize(item.getProduct().getSize());
                                detailDto.setProduct_name(item.getProduct().getProduct().getTitle());
                                detailDto.setImage(item.getProduct().getProduct().getImage());
                                detailDto.setPrice(item.getPrice());
                                detailDto.setQuantity(item.getQuantity());
                                detailDto.setAmount(item.getPrice() * item.getQuantity());

                                listDetailRes.add(detailDto);
                        }
                }
                return listDetailRes;
        }
}
