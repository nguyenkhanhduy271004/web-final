package vn.nguyenduy.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.nguyenduy.laptopshop.domain.Order;
import vn.nguyenduy.laptopshop.domain.Shop;
import vn.nguyenduy.laptopshop.domain.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByShop(Shop shop, Pageable pageable);

    List<Order> findByShipperId(Long shipperId);

    List<Order> findByShipperIdAndStatus(Long shipperId, String status);

    List<Order> getOrdersByShipperId(Long shipperId);

    // List<Order> getDeliveredOrdersByShipper(Long shipperId);

    long countByShipperIdAndStatus(Long shipperId, String status);

}
