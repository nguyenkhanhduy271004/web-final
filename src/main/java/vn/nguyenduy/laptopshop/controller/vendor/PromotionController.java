package vn.nguyenduy.laptopshop.controller.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nguyenduy.laptopshop.domain.Promotion;
import vn.nguyenduy.laptopshop.domain.Shop;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.service.PromotionService;
import vn.nguyenduy.laptopshop.service.ShopService;
import vn.nguyenduy.laptopshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/vendor/promotion")
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

        Long userId = (Long) session.getAttribute("id");
        Optional<User> user = this.userService.getUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/login";
        }

        Shop shop = this.shopService.findByOwner(user.get());
        if (shop == null) {
            model.addAttribute("error", "No shop associated with this user.");
            return "vendor/product/show";
        }

        if (shop != null) {
            model.addAttribute("promotions", this.promotionService.getPromotionsByShop(shop));
        } else {
            model.addAttribute("error", "Shop không tồn tại.");
        }
        return "vendor/promotion/show";
    }

    @GetMapping("/add")
    public String showAddPromotionForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "vendor/promotion/create";
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

        Shop shop = this.shopService.findByOwner(user.get());

        if (shop != null) {
            promotion.setShop(shop);
            promotionService.savePromotion(promotion);
            return "redirect:/vendor/promotion";
        }
        return "vendor/promotion/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditPromotionForm(@PathVariable Long id, Model model) {
        Optional<Promotion> promotion = this.promotionService.getPromotionById(id);
        if (promotion.isPresent()) {
            model.addAttribute("promotion", promotion.get());
            return "vendor/promotion/create";
        }
        return "redirect:/vendor/promotion";
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

        return "redirect:/vendor/promotion";
    }

    @GetMapping("/delete/{id}")
    public String deletePromotion(@PathVariable Long id) {
        this.promotionService.deletePromotion(id);
        return "redirect:/vendor/promotion";
    }
}
