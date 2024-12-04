package vn.nguyenduy.comesticshop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.nguyenduy.comesticshop.domain.CartDetail;
import vn.nguyenduy.comesticshop.repository.CartDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CartDetailService {

    private static final Logger logger = LoggerFactory.getLogger(CartDetailService.class);

    private final CartDetailRepository cartDetailRepository;

    public CartDetailService(CartDetailRepository cartDetailRepository) {
        this.cartDetailRepository = cartDetailRepository;
    }

    public void save(CartDetail cartDetail) {
        logger.info("Saving cart detail: {}", cartDetail);
        cartDetailRepository.save(cartDetail);
    }

    public Optional<CartDetail> findById(long cartDetailId) {
        logger.info("Finding cart detail with id: {}", cartDetailId);
        return cartDetailRepository.findById(cartDetailId);
    }

    public void deleteById(long cartDetailId) {
        logger.info("Deleting cart detail with id: {}", cartDetailId);
        cartDetailRepository.deleteById(cartDetailId);
    }

    public double calculateTotalPriceForCart(Long cartId) {
        // Lấy danh sách CartDetail theo cartId
        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);

        // Tính tổng giá trị (price * quantity) cho từng sản phẩm
        return cartDetails.stream()
                .mapToDouble(cartDetail -> cartDetail.getPrice() * cartDetail.getQuantity())
                .sum();
    }

}
