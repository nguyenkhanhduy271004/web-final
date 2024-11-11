package vn.nguyenduy.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.nguyenduy.laptopshop.domain.Product;
import vn.nguyenduy.laptopshop.domain.Shop;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.service.ProductService;
import vn.nguyenduy.laptopshop.service.ShopService;
import vn.nguyenduy.laptopshop.service.UploadService;
import vn.nguyenduy.laptopshop.service.UserService;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductService productService;
    private final UserService userService;
    private final ShopService shopService;

    public ProductController(
            UploadService uploadService,
            ProductService productService,
            UserService userService,
            ShopService shopService) {
        this.uploadService = uploadService;
        this.productService = productService;
        this.userService = userService;
        this.shopService = shopService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
                page = 1;
            }
        } catch (Exception e) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<Product> prs = this.productService.fetchProducts(pageable);
        List<Product> products = prs.getContent();
        List<Shop> shops = this.shopService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("shops", shops);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "admin/product/show";
    }

    @RequestMapping("/admin/product/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = this.productService.fetchProductById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
        } else {
            return "redirect:/admin/product";
        }

        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @RequestMapping("/admin/product/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = this.productService.fetchProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("newProduct", product);
        } else {
            return "redirect:/admin/product";
        }

        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(Model model, @ModelAttribute("newProduct") Product product) {
        Optional<Product> currentProductOpt = this.productService.fetchProductById(product.getId());
        if (currentProductOpt.isPresent()) {
            Product currentProduct = currentProductOpt.get();
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setFactory(product.getFactory());

            this.productService.createProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String createProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult bindingResult,
            @RequestParam("nguyenduyFile") MultipartFile file) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveUploadFile(file, "product");

        newProduct.setImage(image);

        this.productService.createProduct(newProduct);

        return "redirect:/admin/product";
    }

    @GetMapping(value = "/admin/product/search")
    public String getProductByShop(
            @RequestParam(name = "shopId", required = false) Long shopId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page - 1, 10);

        List<Shop> shops = this.shopService.findAll();

        Page<Product> products;
        if (shopId != null) {
            Shop shop = this.shopService.findById(shopId);
            if (shop != null) {
                products = this.productService.fetchProductsByShop(shop, pageable);
            } else {
                products = Page.empty(pageable);
            }
        } else {
            products = this.productService.fetchProducts(pageable);
        }

        System.out.println("products:" + products);
        System.out.println("shops" + shops);

        model.addAttribute("products", products.getContent());
        model.addAttribute("shops", shops);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());

        return "admin/product/show";
    }

    @GetMapping("/user/wishlist")
    public String getUserWishlist(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            Long userId = (Long) session.getAttribute("id");

            Optional<User> user = this.userService.getUserById(userId);

            if (user.isPresent()) {
                Set<Product> wishlist = user.get().getFavorites();
                System.out.println("wishlist" + wishlist);
                model.addAttribute("wishlist", wishlist);
            } else {
                model.addAttribute("error", "User not found");
            }
        } else {
            model.addAttribute("error", "User session not found");
        }

        return "client/product/wishlist";
    }

}
