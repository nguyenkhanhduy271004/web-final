package vn.nguyenduy.comesticshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.nguyenduy.comesticshop.domain.Review;
import vn.nguyenduy.comesticshop.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(
            ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Optional<Review> findById(long reviewId) {
        return this.reviewRepository.findById(reviewId);
    }

    public Review createReview(Review rv) {
        return this.reviewRepository.save(rv);
    }

    public void deleteById(long reviewId) {
        this.reviewRepository.deleteById(reviewId);
    }

    public Page<Review> getAllReviewsByProductId(long id, Pageable pageable) {
        return this.reviewRepository.findAllByProductId(id, pageable);
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByProductIdAndRating(Long productId, int rating) {
        try {
            return reviewRepository.findByProductIdAndRating(productId, rating);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching reviews", e);
        }
    }

}
