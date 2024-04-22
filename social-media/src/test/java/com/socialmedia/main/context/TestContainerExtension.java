package com.socialmedia.main.context;

import com.socialmedia.main.util.WithKeycloakMockUser;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@Getter
@SpringBootTest
@Testcontainers
@Transactional
@Rollback
@WithKeycloakMockUser
@ActiveProfiles("test")
public class TestContainerExtension {

    @Autowired
    private EntityManager em;

    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("postgres")
            .withEnv(Map.of("PGDATA", "/var/lib/postgresql/data"))
            .withTmpFs(Map.of("/var/lib/postgresql/data", "rw"))
            .withReuse(true);

    @ServiceConnection
    public static RabbitMQContainer rabbitContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:latest"))
            .withReuse(true);

    @ServiceConnection(name = "redis")
    public static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);

    @BeforeAll
    public static void beforeAll() {
        postgresContainer.start();
        rabbitContainer.start();
        redisContainer.start();
    }

}
