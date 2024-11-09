package vn.nguyenduy.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.nguyenduy.laptopshop.domain.Review;
import vn.nguyenduy.laptopshop.repository.ReviewRepository;

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
}
