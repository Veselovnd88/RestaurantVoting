package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Restaurant extends AbstractNamedEntity {

    public static final String MENU_ENTITY_GRAPH = "menuEntityGraph";
    public static final String ALL_ATTR_ENTITY_GRAPH = "allAttributesEntityGraph";

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Menu> menus;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
