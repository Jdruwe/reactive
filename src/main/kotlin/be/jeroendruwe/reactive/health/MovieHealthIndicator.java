package be.jeroendruwe.reactive.health;

import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MovieHealthIndicator extends AbstractReactiveHealthIndicator {

    private final WebClient webClient;

    public MovieHealthIndicator(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {

        Mono<String> movieFlux = webClient.get()
                .uri("/movies/service")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .flatMap(response -> response.bodyToMono(String.class));

        return movieFlux.map(serviceName -> up(builder, serviceName));
    }

    private Health up(Health.Builder builder, String serviceName) {
        return builder.up().withDetail("service", serviceName).build();
    }
}