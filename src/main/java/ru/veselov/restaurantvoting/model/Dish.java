package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "price")
    @Range(min = 100, max = 100000)
    private Integer price;

    @ManyToMany
    @JoinTable(
            name = "menu_dish",
            joinColumns = {@JoinColumn(name = "dish_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"menu_id", "dish_id"}, name = "menu_dish_idx")
    )
    @ToString.Exclude
    private List<Menu> menus;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public void addMenu(Menu menu) {
        List<Menu> menuList = menus == null ? new ArrayList<>() : menus;
        menuList.add(menu);
    }
}
