package carami.todo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "carami.todo.dao",
        "carami.todo.service"
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}
