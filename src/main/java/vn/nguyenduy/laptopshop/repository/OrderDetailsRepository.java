package vn.nguyenduy.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.nguyenduy.laptopshop.domain.OrderDetail;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

}
