package vn.nguyenduy.comesticshop.controller.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.comesticshop.domain.Product;
import vn.nguyenduy.comesticshop.domain.Review;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.service.ProductService;
import vn.nguyenduy.comesticshop.service.ReviewService;
import vn.nguyenduy.comesticshop.service.UploadService;
import vn.nguyenduy.comesticshop.service.UserService;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create-review")
    public String createReview(
            HttpServletRequest request,
            @RequestParam("productId") long productId,
            @RequestParam("rating") int rating,
            @RequestParam("reviewText") String reviewText,
            @RequestParam(value = "reviewMedia", required = false) MultipartFile[] reviewMedia) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        long userId = (long) session.getAttribute("id");

        Product product = productService.fetchProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setReviewDate(LocalDate.now());

        if (reviewMedia != null && reviewMedia.length > 0) {
            List<String> mediaUrls = new ArrayList<>();
            for (MultipartFile media : reviewMedia) {
                String mediaUrl = this.uploadService.handleSaveUploadFile(media, "review");
                mediaUrls.add(mediaUrl);
            }
            review.setImages(mediaUrls);
        }

        reviewService.createReview(review);

        messagingTemplate.convertAndSend("/topic/reviews", review);

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

    @GetMapping("/product/{id}/reviews")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long id, @RequestParam int rating) {
        List<Review> reviews = reviewService.getReviewsByProductIdAndRating(id, rating);
        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("reviews: " + reviews.size());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(reviews);
    }

}
