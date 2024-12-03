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

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping("/product/details/{id}/comment")
    public String showFormComment(@PathVariable long id,Rating rating, ModelMap model) {
        model.addAttribute("rating", rating);
        model.addAttribute("id", id);
        return "user/single-product";
    }

    @PostMapping("/product/details/{id}/comment")
    public String addComment(@PathVariable long id, BindingResult bindingResult, Rating rating) {
        if (bindingResult.hasErrors()) {
            return "comment";
        }
        ratingService.addRating(rating);
        return "redirect:/product/details/{id}";
    }
}
