package ru.veselov.restaurantvoting.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraph(name = Menu.GRAPH_DISHES_VOTES_USERS,
        attributeNodes = {
                @NamedAttributeNode(value = "dishes"),
                @NamedAttributeNode(value = "votes", subgraph = "votes")
        },
        subgraphs = @NamedSubgraph(
                name = "votes",
                attributeNodes = {@NamedAttributeNode(value = "user")}
        )
)
public class Menu extends AbstractBaseEntity {

    public static final String GRAPH_DISHES_VOTES_USERS = "Menu.dishes.votes.users";

    @Column(name = "added_at")
    @NotNull
    private LocalDate addedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @OrderBy("name")
    private Set<Dish> dishes;

    //https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl/51055523#51055523
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @OrderBy("id")
    @ToString.Exclude
    private Set<Vote> votes;

    public Menu(Integer id, LocalDate addedAt, Restaurant restaurant, Dish... dishes) {
        super(id);
        this.addedAt = addedAt;
        this.restaurant = restaurant;
        this.dishes = Set.of(dishes);
    }

    public Menu(Integer id, LocalDate addedAt, Restaurant restaurant, Set<Dish> dishes) {
        super(id);
        this.addedAt = addedAt;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }
}
