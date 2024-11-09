package vn.nguyenduy.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.nguyenduy.laptopshop.domain.User;
import vn.nguyenduy.laptopshop.service.UploadService;
import vn.nguyenduy.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(
            UploadService uploadService,
            UserService userService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("1@gmail.com");
        System.out.println(arrUsers);

        model.addAttribute("eric", "test");
        model.addAttribute("nguyenduy", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
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
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<User> prs = this.userService.getAllUsers(pageable);
        List<User> users = prs.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        Optional<User> user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User nguyenduy,
            BindingResult bindingResult,
            @RequestParam("nguyenduyFile") MultipartFile file) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors()) {
            return "admin/user/create";
        }
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(nguyenduy.getPassword());

        nguyenduy.setAvatar(avatar);
        nguyenduy.setPassword(hashPassword);
        nguyenduy.setRole(this.userService.getRoleByName(nguyenduy.getRole().getName()));
        // save
        this.userService.handleSaveUser(nguyenduy);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Optional<User> currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User nguyenduy) {
        Optional<User> currentUserOptional = this.userService.getUserById(nguyenduy.getId());

        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            currentUser.setAddress(nguyenduy.getAddress());
            currentUser.setFullName(nguyenduy.getFullName());
            currentUser.setPhone(nguyenduy.getPhone());

            this.userService.handleSaveUser(currentUser);
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User eric) {
        this.userService.deleteAUser(eric.getId());
        return "redirect:/admin/user";
    }

    @PostMapping("/favorite-product")
    public String toggleFavoriteProduct(@RequestParam("productId") Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        User user = this.userService.getUserByEmail(email);

        if (user != null) {
            if (this.userService.isProductInFavorites(productId, user.getId())) {
                this.userService.removeFavoriteProduct(productId, user.getId());
            } else {
                this.userService.addFavoriteProduct(productId, user.getId());
            }
        }

        return "redirect:/product/" + productId;
    }

}
