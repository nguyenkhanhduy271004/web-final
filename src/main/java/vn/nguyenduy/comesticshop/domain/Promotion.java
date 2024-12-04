package vn.nguyenduy.comesticshop.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@ToString(exclude = { "shop", "cart" })
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private String name;
    private String description;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
}
