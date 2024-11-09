package vn.nguyenduy.laptopshop.controller.client;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.laptopshop.domain.Product;
import vn.nguyenduy.laptopshop.domain.Review;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.service.ProductService;
import vn.nguyenduy.laptopshop.service.ReviewService;
import vn.nguyenduy.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final ProductService productService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, ProductService productService, UserService userService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/create-review")
    public String handleCreateReview(HttpServletRequest request,
            @RequestParam("productId") long productId,
            @RequestParam("rating") int rating,
            @RequestParam("reviewText") String reviewText) {
        HttpSession session = request.getSession(false);
        long userId = (long) session.getAttribute("id");

        LocalDate reviewDate = LocalDate.now();

        Product product = productService.fetchProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        User user = userService.getUserById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setReviewDate(reviewDate);

        reviewService.createReview(review);

        return "redirect:/product/" + productId;
    }

    @PostMapping("/delete-review")
    public String handleDeleteReview(HttpServletRequest request, @RequestParam("reviewId") long reviewId,
            @RequestParam("productId") long productId) {
        HttpSession session = request.getSession(false);
        long userId = (long) session.getAttribute("id");

        Optional<Review> rv = this.reviewService.findById(reviewId);

        if (rv.isPresent()) {
            Review review = rv.get();
            User user = review.getUser();
            if (user.getId() == userId) {
                this.reviewService.deleteById(reviewId);
            }
        }
        return "redirect:/product/" + productId;
    }

}
