package vn.nguyenduy.laptopshop.controller.admin;

import vn.nguyenduy.laptopshop.domain.Carrier;
import vn.nguyenduy.laptopshop.service.chat.CarrierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/carrier")
public class CarrierController {

    @Autowired
    private CarrierService carrierService;

    @GetMapping("")
    public String listCarriers(Model model) {
        model.addAttribute("carriers", carrierService.getAllCarriers());
        return "admin/carrier/list"; // Hiển thị list.jsp
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("carrier", new Carrier());
        return "admin/carrier/add"; // Hiển thị add.jsp
    }

    @PostMapping("/add")
    public String addCarrier(@ModelAttribute Carrier carrier) {
        carrierService.saveCarrier(carrier);
        return "redirect:/admin/carrier";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Carrier carrier = carrierService.getAllCarriers().stream()
                .filter(c -> c.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Carrier not found"));
        model.addAttribute("carrier", carrier);
        return "admin/carrier/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCarrier(@PathVariable Long id, @ModelAttribute Carrier carrier) {
        carrierService.updateCarrier(id, carrier);
        return "redirect:/admin/carrier";
    }

    @GetMapping("/delete/{id}")
    public String deleteCarrier(@PathVariable Long id) {
        carrierService.deleteCarrier(id);
        return "redirect:/admin/carrier";
    }
}
