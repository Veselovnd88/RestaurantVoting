package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
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
@NamedEntityGraphs({
        @NamedEntityGraph(name = Restaurant.MENU_ENTITY_GRAPH, attributeNodes = {
                @NamedAttributeNode("menus")
        }),
        @NamedEntityGraph(name = Restaurant.ALL_ATTR_ENTITY_GRAPH, includeAllAttributes = true)
}
)

public class Restaurant extends AbstractNamedEntity {

    public static final String MENU_ENTITY_GRAPH = "menuEntityGraph";
    public static final String ALL_ATTR_ENTITY_GRAPH = "allAttributesEntityGraph";

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Vote> votes;

    public Restaurant(Integer id, String name, List<Menu> menus, List<Vote> votes) {
        super(id, name);
        this.menus = menus;
        this.votes = votes;
    }

    public Restaurant(Integer id, String name, List<Menu> menus) {
        super(id, name);
        this.menus = menus;
    }
}
