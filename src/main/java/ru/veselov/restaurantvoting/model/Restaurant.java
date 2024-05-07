package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @ToString.Exclude
    @OrderBy("date DESC")
    private Set<Menu> menus;

    //https://stackoverflow.com/questions/4334970/hibernate-throws-multiplebagfetchexception-cannot-simultaneously-fetch-multipl/51055523#51055523
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OrderBy("id")
    @ToString.Exclude
    private Set<Vote> votes;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
