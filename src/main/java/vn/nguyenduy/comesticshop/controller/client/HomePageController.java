package vn.nguyenduy.comesticshop.controller.client;

import java.util.List;
import java.util.Optional;

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
import vn.nguyenduy.comesticshop.domain.Order;
import vn.nguyenduy.comesticshop.domain.Product;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.domain.dto.RegisterDTO;
import vn.nguyenduy.comesticshop.domain.dto.VerifyRegisterDTO;
import vn.nguyenduy.comesticshop.service.EmailService;
import vn.nguyenduy.comesticshop.service.OrderService;
import vn.nguyenduy.comesticshop.service.ProductService;
import vn.nguyenduy.comesticshop.service.UserService;

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
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "criteria", required = false) String criteria,
            @RequestParam(value = "factory", required = false) List<String> factories,
            @RequestParam(value = "target", required = false) List<String> targets,
            @RequestParam(value = "price", required = false) List<String> priceRange,
            @RequestParam(value = "sort", defaultValue = "gia-nothing") String sort,
            Model model) {

        int size = 10;

        // Tìm kiếm sản phẩm theo tên và áp dụng các bộ lọc
        List<Product> products = productService.searchProducts(query, page, size, criteria, factories, targets,
                priceRange, sort);

        // Đưa dữ liệu vào model
        model.addAttribute("products", products);
        model.addAttribute("query", query);
        model.addAttribute("criteria", criteria);
        model.addAttribute("factories", factories);
        model.addAttribute("targets", targets);
        model.addAttribute("priceRange", priceRange);
        model.addAttribute("sort", sort);

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

    @GetMapping("/payment")
    public String handlePaymentResponse(HttpServletRequest request, Model model) {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String message = "00".equals(vnp_ResponseCode) ? "Thanh toán thành công!" : "Thanh toán thất bại!";

        model.addAttribute("message", message);
        model.addAttribute("vnp_Amount", request.getParameter("vnp_Amount"));
        model.addAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
        model.addAttribute("vnp_TxnRef", request.getParameter("vnp_TxnRef"));
        model.addAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
        model.addAttribute("vnp_ResponseCode", vnp_ResponseCode);

        return "client/payment/payment-result";
    }

}
