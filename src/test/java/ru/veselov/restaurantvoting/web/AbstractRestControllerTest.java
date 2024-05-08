package ru.veselov.restaurantvoting.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import ru.veselov.restaurantvoting.configuration.CacheTestConfiguration;

@SpringBootTest
@Sql(scripts = {"classpath:db/init.sql", "classpath:db/populateDbTest.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@AutoConfigureMockMvc
@ContextConfiguration(classes = {CacheTestConfiguration.class})
@ActiveProfiles("test")
public abstract class AbstractRestControllerTest {

    @Autowired
    protected MockMvc mockMvc;
}
