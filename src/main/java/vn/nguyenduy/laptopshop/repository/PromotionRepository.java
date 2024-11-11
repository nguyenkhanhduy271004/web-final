package vn.nguyenduy.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nguyenduy.laptopshop.domain.Promotion;
import vn.nguyenduy.laptopshop.domain.Shop;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findAll();

    List<Promotion> findByShop(Shop shop);
}
