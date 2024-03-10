package ru.veselov.restaurantvoting.repository;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.veselov.restaurantvoting.model.Vote;
import ru.veselov.restaurantvoting.util.RestaurantTestData;
import ru.veselov.restaurantvoting.util.VoteTestData;

import java.util.List;

@DataJpaTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
class VoteRepositoryTest {

    @Autowired
    VoteRepository voteRepository;

    @Test
    void findAllBetweenDatesByRestaurant() {
        List<Vote> votes = voteRepository.findAllBetweenDatesByRestaurant(RestaurantTestData.sushiRestaurant.id(),
                VoteTestData.VOTED_AT_DATE, VoteTestData.VOTED_AT_DATE);
        Assertions.assertThat(votes).hasSize(1).element(0).extracting(Vote::getVotedAt)
                .asInstanceOf(InstanceOfAssertFactories.LOCAL_DATE)
                .isEqualTo(VoteTestData.VOTED_AT_DATE);
    }
}