package vn.nguyenduy.laptopshop.controller.shipper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.nguyenduy.laptopshop.domain.Shipper;
import vn.nguyenduy.laptopshop.service.OrderService;
import vn.nguyenduy.laptopshop.service.ShipperService;

@Controller
@RequestMapping("/shippers")
public class ShipperController {
    @Autowired
    private ShipperService shipperService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listShippers(Model model) {
        model.addAttribute("shippers", shipperService.getAllShippers());
        return "shippers/list";
    }

    @GetMapping("/{id}")
    public String viewShipperOrders(@PathVariable Long id, Model model) {
        Shipper shipper = shipperService.getShipperById(id);
        model.addAttribute("shipper", shipper);
        model.addAttribute("orders", orderService.getOrdersByShipperId(id));
        model.addAttribute("deliveredOrderCount", this.orderService.countDeliveredOrdersByShipper(id));
        model.addAttribute("pendingOrderCount", this.orderService.countPendingOrdersByShipper(id));
        return "shippers/orders";
    }

    @GetMapping("/{id}/delivered")
    public String viewDeliveredOrders(@PathVariable Long id, Model model) {
        Shipper shipper = shipperService.getShipperById(id);
        model.addAttribute("shipper", shipper);
        model.addAttribute("deliveredOrders", orderService.getDeliveredOrdersByShipper(id));
        model.addAttribute("deliveredOrderCount", orderService.countDeliveredOrdersByShipper(id));
        return "shippers/delivered_orders";
    }
}
