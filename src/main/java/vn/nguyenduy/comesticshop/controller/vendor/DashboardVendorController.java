package vn.nguyenduy.comesticshop.controller.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.service.OrderService;
import vn.nguyenduy.comesticshop.service.ShopService;
import vn.nguyenduy.comesticshop.service.UserService;

@Controller
public class DashboardVendorController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopService shopService;

    @GetMapping("/vendor")
    public String getDashboard(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("id");
        Optional<User> user = userService.getUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/login";
        }

        Shop shop = shopService.findByOwner(user.get());
        long shopId = shop.getId();
        int year = 2024;
        List<Object[]> monthlyRevenue = orderService.getMonthlyRevenue(shopId, year);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("countUsers", this.userService.countUsers());
        model.addAttribute("countProducts", this.userService.countProducts());
        model.addAttribute("countOrders", this.userService.countOrders());
        return "vendor/dashboard/show";
    }
}
