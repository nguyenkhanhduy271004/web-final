package vn.nguyenduy.comesticshop.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.service.ShopService;
import vn.nguyenduy.comesticshop.service.UploadService;
import vn.nguyenduy.comesticshop.service.UserService;

@Controller
// @RequestMapping("/admin/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/shops")
    public String listShops(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10;
        Page<Shop> shops = shopService.getAllShops(page, pageSize);
        System.out.println("shops" + shops.getContent());
        model.addAttribute("shops", shops.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shops.getTotalPages());
        return "admin/shop/list";
    }

    @GetMapping("/admin/shops/view/{id}")
    public String viewShop(@PathVariable("id") Long id, Model model) {
        Shop shop = shopService.getShopById(id);
        model.addAttribute("shop", shop);
        return "admin/shop/view";
    }

    @GetMapping("/admin/shops/edit/{id}")
    public String editShop(@PathVariable("id") Long id, Model model) {
        Shop shop = shopService.getShopById(id);
        model.addAttribute("shop", shop);
        return "admin/shop/edit";
    }

    @PostMapping("/admin/shops/edit/{id}")
    public String updateShop(@PathVariable("id") Long id, @ModelAttribute Shop shop) {
        shop.setId(id);
        shopService.saveShop(shop);
        return "redirect:/admin/shops";
    }

    @PostMapping("/admin/shops/delete/{id}")
    public String deleteShop(@PathVariable("id") Long id) {
        shopService.deleteShop(id);
        return "redirect:/admin/shops";
    }

    @PostMapping("/shop/register")
    public String registerShop(@Valid Shop shop, BindingResult result,
            @RequestParam("logo") MultipartFile logoFile,
            Model model, HttpServletRequest request) {
        // if (result.hasErrors()) {
        // return "client/shop/register";
        // }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        long userId = (long) session.getAttribute("id");
        Optional<User> currentUser = this.userService.getUserById(userId);
        if (!currentUser.isPresent()) {
            model.addAttribute("error", "Bạn cần đăng nhập để đăng ký shop.");
            return "redirect:/login";
        }

        shop.setOwner(currentUser.get());
        shop.setActive(false);

        if (logoFile.isEmpty()) {
            model.addAttribute("error", "Logo file is required.");
            return "client/shop/register";
        }

        String logoFileName = uploadService.handleSaveUploadFile(logoFile, "logo-shop");
        if (logoFileName == null) {
            model.addAttribute("error", "Error saving logo file.");
            return "client/shop/register";
        }
        shop.setLogo(logoFileName);

        this.shopService.saveShop(shop);

        model.addAttribute("message", "Shop đã được đăng ký thành công!");
        return "client/shop/register";
    }
}