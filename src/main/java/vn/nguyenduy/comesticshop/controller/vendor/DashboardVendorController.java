package vn.nguyenduy.comesticshop.controller.vendor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.nguyenduy.comesticshop.service.OrderService;
import vn.nguyenduy.comesticshop.service.UserService;

@Controller
public class DashboardVendorController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/vendor")
    public String getDashboard(Model model) {
        model.addAttribute("countUsers", this.userService.countUsers());
        model.addAttribute("countProducts", this.userService.countProducts());
        model.addAttribute("countOrders", this.userService.countOrders());

        long shopId = 1L;
        int year = 2024;
        List<Object[]> monthlyRevenue = orderService.getMonthlyRevenue(shopId, year);
        model.addAttribute("monthlyRevenue", monthlyRevenue);

        model.addAttribute("monthlyRevenue", monthlyRevenue);
        return "vendor/dashboard/show";
    }
}
