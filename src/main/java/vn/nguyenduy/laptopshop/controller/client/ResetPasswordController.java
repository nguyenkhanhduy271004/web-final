package vn.nguyenduy.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.nguyenduy.laptopshop.domain.dto.EmailFormDTO;
import vn.nguyenduy.laptopshop.domain.dto.ResetPasswordFormDTO;
import vn.nguyenduy.laptopshop.service.EmailService;
import vn.nguyenduy.laptopshop.service.UserService;

@Controller
public class ResetPasswordController {
    private final UserService userService;
    private final EmailService emailService;

    public ResetPasswordController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model) {
        model.addAttribute("emailForm", new EmailFormDTO());
        return "client/auth/forgot-password";
    }

    @PostMapping("/send-otp")
    public String sendOtpToEmail(@ModelAttribute("emailForm") @Valid EmailFormDTO emailForm,
            BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "client/auth/forgot-password";
        }

        if (!this.userService.checkEmailExist(emailForm.getEmail())) {
            model.addAttribute("error", "Email không tồn tại trong hệ thống");
            return "client/auth/forgot-password";
        }

        String otp = this.userService.generateOTP();
        this.emailService.sendOtpEmail(emailForm.getEmail(), otp);

        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("email", emailForm.getEmail());

        return "redirect:/reset-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordFormDTO());
        return "client/auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @ModelAttribute("resetPasswordForm") @Valid ResetPasswordFormDTO resetPasswordForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "client/auth/reset-password";
        }

        if (!resetPasswordForm.getNewPassword().equals(resetPasswordForm.getConfirmPassword())) {
            model.addAttribute("error", "Mật khẩu và Xác nhận mật khẩu không khớp");
            return "client/auth/reset-password";
        }

        String sessionOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email");

        if (sessionOtp == null || !sessionOtp.equals(resetPasswordForm.getOtp())) {
            model.addAttribute("error", "OTP không hợp lệ hoặc đã hết hạn");
            return "client/auth/reset-password";
        }

        this.userService.updatePassword(email, resetPasswordForm.getNewPassword());

        session.removeAttribute("otp");
        session.removeAttribute("email");

        return "redirect:/login?resetSuccess";
    }

}
