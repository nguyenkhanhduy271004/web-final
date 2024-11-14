package vn.nguyenduy.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.nguyenduy.laptopshop.domain.Status;
import vn.nguyenduy.laptopshop.domain.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    void deleteById(long id);

    List<User> findOneByEmail(String email);

    List<User> findAllByStatus(Status status);

    List<User> findAll();

    Optional<User> findById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
