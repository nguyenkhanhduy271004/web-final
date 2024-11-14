package vn.nguyenduy.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.nguyenduy.laptopshop.domain.Cart;
import vn.nguyenduy.laptopshop.domain.CartDetail;
import vn.nguyenduy.laptopshop.domain.Product;
import vn.nguyenduy.laptopshop.domain.Product_;
import vn.nguyenduy.laptopshop.domain.Review;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.nguyenduy.laptopshop.service.ProductService;
import vn.nguyenduy.laptopshop.service.ReviewService;
import vn.nguyenduy.laptopshop.service.UserService;

@Controller
public class ItemController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final UserService userService;

    public ItemController(ProductService productService, ReviewService reviewService, UserService userService) {
        this.productService = productService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id, @RequestParam(defaultValue = "1") int page,
            HttpServletRequest request) {
        Product pr = this.productService.fetchProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Review> reviews = this.reviewService.getAllReviewsByProductId(id, pageable);

        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("id");

        boolean isFavorited = this.userService.isProductInFavorites(id, userId);

        model.addAttribute("product", pr);
        model.addAttribute("reviews", reviews);
        model.addAttribute("id", id);
        model.addAttribute("currentPage", page);
        model.addAttribute("userId", userId);
        model.addAttribute("isFavorited", isFavorited);

        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long productId = id;
        String email = (String) session.getAttribute("email");

        this.productService.handleAddProductToCart(email, productId, session, 1);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);
        System.out.println("Fetched cart: " + cart);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        System.out.println("cart" + cartDetails);

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);

        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return "redirect:/login";
        }

        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);
        System.out.println("Fetched cart: " + cart);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        System.out.println("cart" + cartDetails);

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);
        double totalPrice = 0;
        if (cart != null && cart.getCartDetails() != null) {
            for (CartDetail cd : cart.getCartDetails()) {
                totalPrice += cd.getPrice() * cd.getQuantity();
            }
        }

        session.setAttribute("totalPrice", totalPrice);

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        if ("VNPay".equalsIgnoreCase(paymentMethod)) {
            return "redirect:/pay";
        }
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model) {

        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/products")
    public String getProductPage(Model model,
            ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {
        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            }
        } catch (Exception e) {
            // page = 1
        }

        Pageable pageable = PageRequest.of(page - 1, 10);

        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.PRICE).ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.PRICE).descending());
            }
        }

        if (productCriteriaDTO.getCriteria() != null && productCriteriaDTO.getCriteria().isPresent()) {
            String criteria = productCriteriaDTO.getCriteria().get();
            if (criteria.equals("best_seller")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.QUANTITY_SOLD).descending());
            } else if (criteria.equals("new")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.CREATE_DATE).descending());
            } else if (criteria.equals("evaluate")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.STAR).descending());
            } else if (criteria.equals("favorite")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.LIKES).descending());
            }
        }

        Page<Product> prs = this.productService.fetchProductsWithSpec(pageable, productCriteriaDTO);

        List<Product> products = prs.getContent().size() > 0 ? prs.getContent() : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        model.addAttribute("queryString", qs);

        return "client/product/show";
    }

}
