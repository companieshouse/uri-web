package uk.gov.companieshouse.uri.web.configuration;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.ResourceBundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class SpringWebConfigTest {
    private SpringWebConfig testSpringWebConfig;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        testSpringWebConfig = new SpringWebConfig();
    }

    @Test
    void resourceBundle() {
        assertThat(testSpringWebConfig.resourceBundle(), instanceOf(ResourceBundle.class));
    }
}
