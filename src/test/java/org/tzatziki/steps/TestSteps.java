package org.tzatziki.steps;

import com.decathlon.tzatziki.steps.KafkaSteps;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.tzatziki.StockApplication;

import java.util.Map;

import static com.decathlon.tzatziki.utils.MockFaster.url;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = StockApplication.class)
@ContextConfiguration(initializers = TestSteps.Initializer.class)
public class TestSteps {
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:12").withTmpFs(Map.of("/var/lib/postgresql/data", "rw")).waitingFor(Wait.forListeningPort());

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgres.start();
            KafkaSteps.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword(),
                    "stock.referential.url=" + url() + "/mock/referential", //stock.referential.url is the property used by rest client in the app to reach the referential API
                    "spring.kafka.bootstrap-servers=" + KafkaSteps.bootstrapServers(),
                    "spring.kafka.properties.schema.registry.url=" + KafkaSteps.schemaRegistryUrl(),
                    "spring.kafka.consumer.auto-offset-reset=earliest"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
