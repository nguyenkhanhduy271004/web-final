package vn.nguyenduy.comesticshop.controller.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.nguyenduy.comesticshop.domain.Review;

@Controller
public class ReviewWebSocketController {

    @MessageMapping("/new-review") // Endpoint để client gửi dữ liệu
    @SendTo("/topic/reviews") // Client sẽ nhận thông báo từ topic này
    public Review broadcastNewReview(Review review) {
        // Xử lý dữ liệu nếu cần trước khi gửi tới các client
        return review;
    }
}
