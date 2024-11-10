package vn.nguyenduy.laptopshop.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.nguyenduy.laptopshop.domain.Shop;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.service.ShopService;
import vn.nguyenduy.laptopshop.service.UploadService;
import vn.nguyenduy.laptopshop.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadService uploadService;

    // @GetMapping("/register")
    // public String showRegisterForm(Model model) {
    // model.addAttribute("shop", new Shop());
    // return "register-shop";
    // }

    @PostMapping("/register")
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
