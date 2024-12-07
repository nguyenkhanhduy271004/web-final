package vn.nguyenduy.comesticshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nguyenduy.comesticshop.domain.Promotion;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.service.PromotionService;
import vn.nguyenduy.comesticshop.service.ShopService;
import vn.nguyenduy.comesticshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/admin/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listPromotions(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        model.addAttribute("promotions", this.promotionService.findAll());
        return "admin/promotion/show";
    }

    @GetMapping("/add")
    public String showAddPromotionForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "admin/promotion/create";
    }

    @PostMapping("/add")
    public String addPromotion(@ModelAttribute Promotion promotion, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        Long userId = (Long) session.getAttribute("id");
        Optional<User> user = this.userService.getUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/login";
        }

        // Shop shop = this.shopService.findByOwner(user.get());
        promotion.setShop(null);

        promotionService.savePromotion(promotion);

        return "redirect:/admin/promotion";
    }

    @GetMapping("/edit/{id}")
    public String showEditPromotionForm(@PathVariable Long id, Model model) {
        Optional<Promotion> promotion = this.promotionService.getPromotionById(id);
        if (promotion.isPresent()) {
            model.addAttribute("promotion", promotion.get());
            return "admin/promotion/create";
        }
        return "redirect:/admin/promotion";
    }

    @PostMapping("/edit")
    public String editPromotion(@ModelAttribute Promotion promotion, HttpServletRequest request) {
        Optional<Promotion> existingPromotionOpt = this.promotionService.getPromotionById(promotion.getId());
        if (existingPromotionOpt.isPresent()) {
            Promotion existingPromotion = existingPromotionOpt.get();

            existingPromotion.setName(promotion.getName());
            existingPromotion.setDescription(promotion.getDescription());
            existingPromotion.setDiscountRate(promotion.getDiscountRate());
            existingPromotion.setStartDate(promotion.getStartDate());
            existingPromotion.setEndDate(promotion.getEndDate());

            this.promotionService.savePromotion(existingPromotion);
        }

        return "redirect:/admin/promotion";
    }

    @GetMapping("/delete/{id}")
    public String deletePromotion(@PathVariable Long id) {
        this.promotionService.deletePromotion(id);
        return "redirect:/admin/promotion";
    }
}
