package vn.nguyenduy.comesticshop.controller.vendor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.comesticshop.domain.Order;
import vn.nguyenduy.comesticshop.domain.OrderDetail;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.service.OrderService;
import vn.nguyenduy.comesticshop.service.ShopService;
import vn.nguyenduy.comesticshop.service.UserService;

@Controller
public class OrderVendorController {

    private final OrderService orderService;
    private final UserService userService;
    private final ShopService shopService;

    public OrderVendorController(OrderService orderService, UserService userService, ShopService shopService) {
        this.orderService = orderService;
        this.userService = userService;
        this.shopService = shopService;
    }

    @GetMapping("/vendor/order")
    public String getDashboard(Model model, @RequestParam("page") Optional<Integer> pageOptional,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("id");
        Optional<User> user = this.userService.getUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/login";
        }

        Shop shop = this.shopService.findByOwner(user.get());
        if (shop == null) {
            model.addAttribute("error", "User không sở hữu shop nào.");
            return "vendor/order/show";
        }

        Long shopId = shop.getId();
        int page = pageOptional.orElse(1) - 1;
        Pageable pageable = PageRequest.of(page, 10);

        Page<OrderDetail> orderDetails = this.orderService.fetchOrdersByShopId(shopId, pageable);

        model.addAttribute("shopId", shopId);
        model.addAttribute("orderDetails", orderDetails.getContent());
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", orderDetails.getTotalPages());
        return "vendor/order/show";
    }

    @GetMapping("/vendor/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        List<OrderDetail> orderDetails = this.orderService.findByOrderId(id);
        model.addAttribute("orderDetails", orderDetails);
        return "vendor/order/detail";
    }

    @GetMapping("/vendor/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newOrder", new Order());
        return "vendor/order/delete";
    }

    @PostMapping("/vendor/order/delete")
    public String postDeleteOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.deleteOrderById(order.getId());
        return "redirect:/vendor/order";
    }

    @GetMapping("/vendor/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Order currentOrder = this.orderService.fetchOrderById(id).get();
        model.addAttribute("newOrder", currentOrder);
        return "vendor/order/update";
    }

    @PostMapping("/vendor/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        return "redirect:/vendor/order";
    }
}
