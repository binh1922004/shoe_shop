package hcmute.com.ShoeShop.controller.web;

import hcmute.com.ShoeShop.entity.Rating;
import hcmute.com.ShoeShop.services.imp.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/product/details/{id}/comment")
    public String addComment(@PathVariable long id, @RequestParam String comment, @RequestParam int star) {
        Rating rating = new Rating();
        rating.setComment(comment);
        rating.setStar(star);
        return "redirect:/product/details/{id}";
    }
}
