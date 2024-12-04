package vn.nguyenduy.comesticshop.controller.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.comesticshop.domain.Carrier;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.service.ShopService;
import vn.nguyenduy.comesticshop.service.UserService;
import vn.nguyenduy.comesticshop.service.CarrierService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private ShopService shopService;

    @Autowired
    private CarrierService carrierService;

    @Autowired
    private UserService userService;

    @GetMapping("/add-carriers")
    public String showAddCarriersPage(HttpServletRequest request, Model model) {
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

        List<Carrier> allCarriers = carrierService.getAllCarriers();
        List<Carrier> shopCarriers = shop.getCarriers();

        model.addAttribute("allCarriers", allCarriers);
        model.addAttribute("shopCarriers", shopCarriers);
        return "vendor/carrier/add-carriers";
    }

    @PostMapping("/add-carriers")
    public String addCarriersToShop(
            HttpServletRequest request,
            @RequestParam List<Long> carrierIds,
            RedirectAttributes redirectAttributes) {

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
            redirectAttributes.addFlashAttribute("error", "User không sở hữu shop nào.");
            return "redirect:/vendor";
        }

        List<Carrier> selectedCarriers = carrierService.findByIds(carrierIds);
        shop.setCarriers(selectedCarriers);
        shopService.save(shop);

        redirectAttributes.addFlashAttribute("success", "Thêm nhà vận chuyển thành công!");
        return "redirect:/vendor/carriers";
    }

    @GetMapping("/carriers")
    public String viewShopCarriers(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("id");
        Optional<User> user = userService.getUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/login";
        }

        Shop shop = shopService.findByOwner(user.get());
        if (shop == null) {
            model.addAttribute("error", "User không sở hữu shop nào.");
            return "vendor/order/show";
        }

        model.addAttribute("carriers", shop.getCarriers());
        return "vendor/carrier/list";
    }

}
