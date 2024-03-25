package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraph(name = Menu.DISHES_ENTITY_GRAPH, attributeNodes = {@NamedAttributeNode("dishes")})
public class Menu extends AbstractBaseEntity {

    public static final String DISHES_ENTITY_GRAPH = "Menu.dishes";

    @Column(name = "added_at")
    @NotNull
    private LocalDate addedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_dish",
            joinColumns = {@JoinColumn(name = "menu_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"menu_id", "dish_id"}, name = "menu_dish_idx")
    )
    @OrderBy("name")
    private List<Dish> dishes;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @OrderBy("id")
    @ToString.Exclude
    private List<Vote> votes;

    public Menu(Integer id, LocalDate addedAt, Restaurant restaurant, Dish... dishes) {
        super(id);
        this.addedAt = addedAt;
        this.restaurant = restaurant;
        this.dishes = List.of(dishes);
    }

    public Menu(Integer id, LocalDate addedAt, Restaurant restaurant, List<Dish> dishes) {
        super(id);
        this.addedAt = addedAt;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }
}
