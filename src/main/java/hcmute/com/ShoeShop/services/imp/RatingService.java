package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Product;
import hcmute.com.ShoeShop.entity.Rating;
import hcmute.com.ShoeShop.entity.Users;
import hcmute.com.ShoeShop.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public void addRating(String email, String comment, int star, long productId) {
        // lấy thong tin khach hang
        Users user = userService.findUserByEmail(email);

        // kiem tra khach hang da mua product do chua, neu mua roi thi duoc comment(cap nhat sau)

        // lay thong tin product ma khach hang comment
        Product product = productService.getProductById(productId);
        if (product == null) {
            return;
        }

        Rating rating = new Rating();
        rating.setComment(comment);
        rating.setStar(star);
        rating.setUser(user);
        rating.setProduct(product);

        ratingRepository.save(rating);
    }

    public void updateRating(String email, String comment, int star, long productId, long ratingId) {
        // lấy thong tin khach hang
        Users user = userService.findUserByEmail(email);

        if (user == null) {
            return;
        }

        // tim comment ma khach hang edit
        Rating ratingCommented = ratingRepository.findById(ratingId).orElseGet(()->{
            throw new IllegalArgumentException("Cannot find rating with id: " + ratingId);
        });

        // lay thong tin product ma khach hang comment
        Product product = productService.getProductById(productId);
        if (product == null) {
            return;
        }

        ratingCommented.setComment(comment);
        ratingCommented.setStar(star);
        ratingCommented.setUser(user);
        ratingCommented.setProduct(product);

        ratingRepository.save(ratingCommented);
    }

    public void deleteRating(String email) {

        // lấy thong tin khach hang
        Users user = userService.findUserByEmail(email);

        if (user == null) {
            return;
        }

        // tim comment dua tren khach hang
        Rating rating = ratingRepository.findByUser(user);

        ratingRepository.delete(rating);
    }

    public List<Rating> getAllRatingsByProductId(long productId) {
        return ratingRepository.findAllByProductId(productId);
    }

    public Page<Rating> getAllRatingsWithPaginationByProductId(long productId, Pageable pageable) {
        return ratingRepository.findAllByProductId(productId, pageable);
    }
}
