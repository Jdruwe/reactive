package be.jeroendruwe.reactive;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.repository.MovieRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveApplication {

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

