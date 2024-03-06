package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dish extends AbstractNamedEntity {

    @Column(name = "price")
    @Range(min = 100, max = 100000)
    private Integer price;

    @Column(name = "added_at")
    @NotNull
    private LocalDate addedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    public Dish(Integer id, String name, Integer price, LocalDate addedAt, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.addedAt = addedAt;
        this.restaurant = restaurant;
    }
}
