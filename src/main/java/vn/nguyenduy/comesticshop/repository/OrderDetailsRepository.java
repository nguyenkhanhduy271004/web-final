package vn.nguyenduy.comesticshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.nguyenduy.comesticshop.domain.OrderDetail;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {

}
