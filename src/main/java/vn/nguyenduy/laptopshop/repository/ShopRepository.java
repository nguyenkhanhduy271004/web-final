package vn.nguyenduy.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nguyenduy.laptopshop.domain.Shop;
import vn.nguyenduy.laptopshop.domain.User;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findByOwner(User owner);

}
