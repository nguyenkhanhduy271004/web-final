package vn.nguyenduy.comesticshop.controller.client;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.comesticshop.domain.CartDetail;
import vn.nguyenduy.comesticshop.domain.Product;
import vn.nguyenduy.comesticshop.service.CartDetailService;
import vn.nguyenduy.comesticshop.service.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

class CartRequest {
    private long quantity;
    private long productId;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}

@RestController
public class CartAPI {

    @Autowired
    private ProductService productService;
    @Autowired
    private CartDetailService cartDetailService;

    @PostMapping("/api/add-product-to-cart")
    public ResponseEntity<String> addProductToCart(
            @RequestBody() CartRequest cartRequest,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        Product product = this.productService.fetchProductById(cartRequest.getProductId()).get();
        System.out.println("Product quantity: " + product.getQuantity());
        System.out.println("Cart quantity: " + cartRequest.getQuantity());
        if (product.getQuantity() < cartRequest.getQuantity()) {
            return ResponseEntity.badRequest().body("Số lượng sản phẩm vượt quá số lượng còn lại trong kho");
        }

        this.productService.handleAddProductToCart(email, cartRequest.getProductId(), session,
                cartRequest.getQuantity());

        int sum = (int) session.getAttribute("sum");

        return ResponseEntity.ok().body(String.valueOf(sum));
    }

    @PostMapping("/cart/increase")
    @ResponseBody
    public ResponseEntity<?> increaseCartItem(@RequestParam("cartDetailId") long cartDetailId) {
        try {
            Optional<CartDetail> optionalCartDetail = cartDetailService.findById(cartDetailId);
            if (optionalCartDetail.isPresent()) {
                CartDetail cartDetail = optionalCartDetail.get();
                cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                cartDetailService.save(cartDetail);
                System.out.println("cart: " + cartDetail.getQuantity());
                return ResponseEntity.ok("Quantity increased successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while increasing the quantity: " + e.getMessage());
        }
    }

    @PostMapping("/cart/decrease")
    @ResponseBody
    public ResponseEntity<?> decreaseCartItem(@RequestParam("cartDetailId") long cartDetailId) {
        try {
            Optional<CartDetail> optionalCartDetail = cartDetailService.findById(cartDetailId);
            if (optionalCartDetail.isPresent()) {
                CartDetail cartDetail = optionalCartDetail.get();
                long newQuantity = cartDetail.getQuantity() - 1;
                if (newQuantity > 0) {
                    cartDetail.setQuantity(newQuantity);
                    cartDetailService.save(cartDetail);
                    return ResponseEntity.ok("Quantity decreased successfully");
                } else {
                    cartDetailService.deleteById(cartDetailId);
                    return ResponseEntity.ok("Item removed from cart");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while decreasing the quantity: " + e.getMessage());
        }
    }

    @PostMapping("/update-cart/{id}")
    public ResponseEntity<?> updateCartQuantity(@PathVariable Long id, @RequestParam int quantity) {
        try {
            Optional<CartDetail> cartDetailOptional = cartDetailService.findById(id);
            if (cartDetailOptional.isPresent()) {
                CartDetail cartDetail = cartDetailOptional.get();
                Product product = this.productService.fetchProductById(cartDetail.getProduct().getId()).get();
                product.setQuantity(product.getQuantity() + (cartDetail.getQuantity() - quantity));
                this.productService.saveProduct(product);
                cartDetail.setQuantity(quantity);
                cartDetailService.save(cartDetail);
                return ResponseEntity.ok("Cập nhật thành công");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cập nhật thất bại");
        }
    }

    @GetMapping("/total/{cartId}")
    public ResponseEntity<Double> getTotalPriceForCart(@PathVariable Long cartId) {
        double totalPrice = cartDetailService.calculateTotalPriceForCart(cartId);
        return ResponseEntity.ok(totalPrice);
    }

}
