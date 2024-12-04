package vn.nguyenduy.comesticshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.nguyenduy.comesticshop.domain.Order;
import vn.nguyenduy.comesticshop.domain.OrderDetail;
import vn.nguyenduy.comesticshop.domain.Shop;
import vn.nguyenduy.comesticshop.domain.User;
import vn.nguyenduy.comesticshop.repository.OrderDetailsRepository;
import vn.nguyenduy.comesticshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderDetailsRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    public Page<Order> fetchAllOrders(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void deleteOrderById(long id) {
        // delete order detail
        Optional<Order> orderOptional = this.fetchOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }

        this.orderRepository.deleteById(id);
    }

    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public Page<Order> fetchOrdersByShop(Shop shop, Pageable pageable) {
        return orderRepository.findByShop(shop, pageable);
    }

    public List<Order> getOrdersByShipperId(Long shipperId) {
        return orderRepository.findByShipperId(shipperId);
    }

    public List<Order> getDeliveredOrdersByShipper(Long shipperId) {
        return orderRepository.findByShipperIdAndStatus(shipperId, "delivered");
    }

    public long countDeliveredOrdersByShipper(Long shipperId) {
        return orderRepository.countByShipperIdAndStatus(shipperId, "delivered");
    }

    public long countPendingOrdersByShipper(Long shipperId) {
        return orderRepository.countByShipperIdAndStatus(shipperId, "pending");
    }

}
