package ru.veselov.restaurantvoting.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "vote")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Vote extends AbstractBaseEntity {

    @Column(name = "voted_at")
    @NotNull
    private LocalDate votedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate votedAt, User user) {
        super(id);
        this.votedAt = votedAt;
        this.user = user;
    }

    public Vote(Integer id, LocalDate votedAt, User user, Restaurant restaurant) {
        super(id);
        this.votedAt = votedAt;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(User user, Restaurant restaurant, LocalDate localDate) {
        this.user = user;
        this.restaurant = restaurant;
        this.votedAt = localDate;
    }
}
