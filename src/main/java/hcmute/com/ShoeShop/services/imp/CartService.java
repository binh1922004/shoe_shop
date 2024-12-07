package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Cart;
import hcmute.com.ShoeShop.entity.CartDetail;
import hcmute.com.ShoeShop.entity.ProductDetail;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.repository.CartDetailRepository;
import hcmute.com.ShoeShop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private UserService userService;

    public void addToCart(String email, int productDetailId, int quantity) {

        // lấy thong tin khach hang
        Users user = userService.findUserByEmail(email);

        // lay gio hang cua nguoi dung neu chua co thi tao moi
        Cart cart = cartRepository.findByUserId(user).orElseGet(()->{
            Cart newCart = new Cart();
            newCart.setUserId(user);
            newCart.setTotalPrice(0.0);
            return cartRepository.save(newCart);
        });

        // lay thong tin san pham
        ProductDetail productDetail = productDetailService.findProductDetailById(productDetailId).orElse(null);

        // tinh gia cua product dua tren size da chon ( gia goc + gia size)
        double finalPrice = productDetail.getProduct().getPrice() + productDetail.getPriceadd();

        // kiem tra san pham da ton tai trong gio hang chua
        CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, productDetail).orElse(null);

        // neu chua co trong gio hang thi them moi
        if (cartDetail == null) {
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(productDetail);
            cartDetail.setQuantity(quantity);
            cartDetail.setPrice(finalPrice);

            // Thêm vào orderDetailSet
            cart.getOrderDetailSet().add(cartDetail);
        }
        else {
            // neu co roi thi cap nhat so luong va gia
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            cartDetail.setPrice(finalPrice);
        }

        // luu thong tin CartDetail
        cartDetailRepository.save(cartDetail);

        // cập nhật tổng giá trị của giỏ hàng
        // duyet qua set lay gia * quantity
        double totalPrice = cart.getOrderDetailSet().stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getQuantity()).sum();

        // set tong gia tri gio hang
        cart.setTotalPrice(totalPrice);

        // luu thong tin gio hang
        cartRepository.save(cart);
    }

    public void updateMyCart(String email, long cartDetailId, int quantity) {

        // lấy thong tin khach hang
        Users user = userService.findUserByEmail(email);

        // lay thong tin gio hang cua nguoi dung
        Cart cart = cartRepository.findByUserId(user).orElseGet(() ->{
            throw new IllegalArgumentException("Can not find cart with user: " + user.getFullname());
        });

        // neu so luong la 0 thi xoa
        if(quantity == 0){
            this.removeFromCart(email, cartDetailId);
            return;
        }


        // lay thong tin cua CartDetail trong gio hang can update
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElseGet(()->{
            throw new IllegalArgumentException("Can not find cart detail");
        });

        // set quantity
        cartDetail.setQuantity(quantity);

        // luu thong tin cartDetail
        cartDetailRepository.save(cartDetail);

        // cập nhật tổng giá trị của giỏ hàng
        // duyet qua set gia = (gia goc + gia size) * quantity
        double totalPrice = cart.getOrderDetailSet().stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getQuantity()).sum();

        // set tong gia tri gio hang
        cart.setTotalPrice(totalPrice);

        // luu thong tin gio hang
        cartRepository.save(cart);
    }

    public void removeFromCart(String email, long cartDetailId) {
        Users user = userService.findUserByEmail(email);

        // lay thong tin gio hang
        Cart cart = cartRepository.findByUserId(user).orElse(null);

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user: " + email);
        }

        // tim cartdetail trong gio hang
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElse(null);

        if (cartDetail == null) {
            throw new IllegalArgumentException("Product not found in cart");
        }
        // xoa san pham khoi gio hang
        cart.getOrderDetailSet().remove(cartDetail);
        cartDetailRepository.delete(cartDetail);

        // cap nhat gia tri gio hang
        double totalPrice = cart.getOrderDetailSet().stream().mapToDouble(CartDetail::getPrice).sum();
        cart.setTotalPrice(totalPrice);

        // luu gio hang sau khi xoa
        cartRepository.save(cart);
    }


    public Cart getCartByUser(String email) {

        // kiem tra nguoi dung co ton tai khong
        Users user = userService.findUserByEmail(email);

        if(user == null) {

            return null;
        }

        // kiem tra co gio hang chua neu chua thi tao moi
        return cartRepository.findByUserId(user).orElseGet(() ->{
            Cart cart = new Cart();
            cart.setUserId(user);
            cart.setTotalPrice(0.0);

            return cartRepository.save(cart);
        });

    }

    public Cart getCartById(int cartId) {
        return cartRepository.findCartsById(cartId);
    }
}
