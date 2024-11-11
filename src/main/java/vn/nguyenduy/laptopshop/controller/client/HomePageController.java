package vn.nguyenduy.laptopshop.controller.client;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.nguyenduy.laptopshop.domain.Order;
import vn.nguyenduy.laptopshop.domain.Product;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.domain.dto.EmailFormDTO;
import vn.nguyenduy.laptopshop.domain.dto.RegisterDTO;
import vn.nguyenduy.laptopshop.domain.dto.VerifyRegisterDTO;
import vn.nguyenduy.laptopshop.service.EmailService;
import vn.nguyenduy.laptopshop.service.OrderService;
import vn.nguyenduy.laptopshop.service.ProductService;
import vn.nguyenduy.laptopshop.service.UserService;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;
    private final EmailService emailService;

    public HomePageController(
            ProductService productService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            OrderService orderService,
            EmailService emailService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> prs = this.productService.fetchProducts(pageable);
        List<Product> products = prs.getContent();
        List<Product> topSellingProducts = this.productService.fetchTopSellingProducts(10);
        model.addAttribute("topSellingProducts", topSellingProducts);
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult, Model model, HttpServletRequest request) {
        System.out.println("Redirecting to /verify-otp");

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(registerDTO);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        String otp = this.userService.generateOTP();
        this.emailService.sendOtpEmail(user.getEmail(), otp);

        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("user", user);

        this.userService.handleSaveUser(user);

        System.out.println("Redirecting to /verify-otp");

        return "redirect:/verify-otp";
    }

    @GetMapping("/verify-otp")
    public String getVerifyPage(Model model) {
        model.addAttribute("otpForm", new VerifyRegisterDTO());
        return "client/auth/verify";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @ModelAttribute("otpForm") @Valid VerifyRegisterDTO otpForm,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "client/auth/verify";
        }

        HttpSession session = request.getSession();
        String sessionOtp = (String) session.getAttribute("otp");

        if (sessionOtp != null && otpForm.getOtp().equals(sessionOtp)) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                user.setActive(true);
                this.userService.handleSaveUser(user);
                session.removeAttribute("otp");
                session.removeAttribute("user");

                return "redirect:/login";
            }
        }

        return "client/auth/verify-otp-error";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {
        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        long id = (long) session.getAttribute("id");
        Optional<User> userOptional = this.userService.getUserById(id);
        if (!userOptional.isPresent()) {
            return "redirect:/login";
        }

        List<Order> orders = this.orderService.fetchOrderByUser(userOptional.get());
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        int size = 10;
        List<Product> products = productService.searchProductsByName(query, page, size);
        model.addAttribute("products", products);
        model.addAttribute("query", query);

        return "client/product/show";
    }

    @GetMapping("/profile")
    public String getInfoUserPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        long id = (long) session.getAttribute("id");
        Optional<User> userOptional = this.userService.getUserById(id);

        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            model.addAttribute("error", "User not found");
        }
        return "client/infor/profile";
    }

    @GetMapping("/shop/register")
    public String getRegisterShopPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        return "client/shop/register";

    }

}
