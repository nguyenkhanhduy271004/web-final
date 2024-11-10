package vn.nguyenduy.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.nguyenduy.laptopshop.domain.Product;
import vn.nguyenduy.laptopshop.domain.Shop;

import java.util.List;
import java.util.Optional;

//crud: create, read, update, delete
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Product save(Product eric);

    void deleteById(long id);

    List<Product> findByName(String name);

    // List<Product> findAll();

    Optional<Product> findById(long id);

    Page<Product> findAll(Pageable page);

    Page<Product> findAllByOrderByQuantitySoldDesc(Pageable pageable);

    Page<Product> findAll(Specification specification, Pageable page);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByShop(Shop shop, Pageable pageable);

}
