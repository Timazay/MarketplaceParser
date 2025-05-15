package by.tima_zaytsev.MarketplaceParser.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
@Configuration
public class SwaggerConfig {
    @Value("${application-description}")String appDescription;
    @Value("${application-version}")String appVersion;
    @Value("${swagger.name}")String name;
    @Value("${swagger.email}")String email;
    @Value("${swagger.description}")String description;
    @Value("${server.address-url}")String url;
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("Application API")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact().name(name)
                                .email(email)))
                .servers(List.of(new Server().url(url)
                                .description(description)));
    }
}
