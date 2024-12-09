package vn.nguyenduy.comesticshop.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public Page<OrderDetail> fetchAllOrdersDetail(Pageable pageable) {
        return this.orderDetailRepository.findAll(pageable);
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

            for (OrderDetail orderDetail : currentOrder.getOrderDetails()) {
                orderDetail.setStatus(order.getStatus());
                this.orderDetailRepository.save(orderDetail);
            }

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

    public List<Object[]> getMonthlyRevenue(long shopId, int year) {
        return orderRepository.findMonthlyRevenueByShopId(shopId, year);
    }

    // public Page<OrderDetail> fetchOrdersByShopId(Long shopId, Pageable pageable)
    // {
    // return orderDetailRepository.findByProductShopId(shopId, pageable);
    // }

    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    public Page<OrderDetail> fetchOrdersByShopId(Long shopId, Pageable pageable) {
        // Lấy ra các OrderDetail theo shopId
        Page<OrderDetail> orderDetails = orderDetailRepository.findByProductShopId(shopId, pageable);

        Set<Long> seenOrderIds = new HashSet<>();
        List<OrderDetail> uniqueOrderDetails = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            if (!seenOrderIds.contains(orderDetail.getOrder().getId())) {
                seenOrderIds.add(orderDetail.getOrder().getId());
                uniqueOrderDetails.add(orderDetail);
            }
        }

        return new PageImpl<>(uniqueOrderDetails, pageable, orderDetails.getTotalElements());
    }

    public List<OrderDetail> findByOrderId(long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    // public void updateOrderDetails(List<OrderDetail> orderDetails) {
    // for (OrderDetail orderDetail : orderDetails) {
    // orderDetailRepository.save(orderDetail);
    // }
    // }

    public void updateOrderDetail(OrderDetail orderDetail) {
        this.orderDetailRepository.save(orderDetail);
    }

    public void save(Order order) {
        this.orderRepository.save(order);
    }

}
