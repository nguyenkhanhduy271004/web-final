package vn.nguyenduy.comesticshop.controller.vendor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.nguyenduy.comesticshop.service.UserService;

@Controller
public class DashboardVendorController {

    private final UserService userService;

    public DashboardVendorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/vendor")
    public String getDashboard(Model model) {
        model.addAttribute("countUsers", this.userService.countUsers());
        model.addAttribute("countProducts", this.userService.countProducts());
        model.addAttribute("countOrders", this.userService.countOrders());
        return "vendor/dashboard/show";
    }
}
