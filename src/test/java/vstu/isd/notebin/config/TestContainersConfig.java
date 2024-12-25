package vstu.isd.notebin.config;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class TestContainersConfig {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    private final static int REDIS_POST = 6379;
    @Container
    public static RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:latest"))
            .withExposedPorts(REDIS_POST);

    static {
        postgresContainer.start();
        redisContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                            "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgresContainer.getUsername(),
                            "spring.datasource.password=" + postgresContainer.getPassword(),
                            "spring.redis.host=" + redisContainer.getHost(),
                            "spring.redis.port=" + redisContainer.getMappedPort(REDIS_POST)
                    )
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
