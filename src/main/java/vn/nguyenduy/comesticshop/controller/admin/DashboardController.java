package vn.nguyenduy.comesticshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.nguyenduy.comesticshop.service.UserService;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        model.addAttribute("countUsers", this.userService.countUsers());
        model.addAttribute("countProducts", this.userService.countProducts());
        model.addAttribute("countOrders", this.userService.countOrders());
        return "admin/dashboard/show";
    }
}
