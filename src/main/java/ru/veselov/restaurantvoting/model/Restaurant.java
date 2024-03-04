package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@Getter
@Setter
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> dishes;

    @OneToMany(mappedBy = "restaurant")
    private List<Vote> votes;
}
