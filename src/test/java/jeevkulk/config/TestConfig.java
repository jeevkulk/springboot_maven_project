package jeevkulk.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;

import java.util.Properties;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "jeevkulk")
@PropertySources(
        {
                @PropertySource({"classpath:application-dev.properties"}),
                @PropertySource({"classpath:FILE.DAT"})
        }
)
public class TestConfig {

    @Bean
    public static PropertyPlaceholderConfigurer properties() throws Exception {
        final PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        Properties prop = new Properties();
        prop.setProperty("data.filename","FILE.DAT");
        propertyPlaceholderConfigurer.setProperties(prop);
        return propertyPlaceholderConfigurer;
    }
}
