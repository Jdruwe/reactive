package be.jeroendruwe.reactive;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.properties.MovieProperties;
import be.jeroendruwe.reactive.repository.MovieRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
@EnableConfigurationProperties(MovieProperties.class)
public class ReactiveApplication {

    @Bean
    WebClient client() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    ApplicationRunner demoData(MovieRepository movieRepository) {
        return args -> {
            movieRepository.deleteAll().thenMany(
                    Flux.just("The Silence of the Lambdas", "Back to the Future",
                            "AEon Flux", "Meet the Fluxers", "The Fluxxinator", "Flux Gordon", "Y Tu Momo Tambien")
                            .map(Movie::new)
                            .flatMap(movieRepository::save))
                    .thenMany(movieRepository.findAll())
                    .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }
}

