package vn.nguyenduy.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.nguyenduy.laptopshop.domain.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {
}
