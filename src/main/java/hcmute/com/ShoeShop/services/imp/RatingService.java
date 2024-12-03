package hcmute.com.ShoeShop.services.imp;

import hcmute.com.ShoeShop.entity.Rating;
import hcmute.com.ShoeShop.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }

    public List<Rating> getAllRatingsByProductId(long productId) {
        return ratingRepository.findAllByProductId(productId);
    }
}
