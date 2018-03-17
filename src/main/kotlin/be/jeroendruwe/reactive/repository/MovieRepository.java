package be.jeroendruwe.reactive.repository;

import be.jeroendruwe.reactive.models.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveCrudRepository<Movie, String> {
    Flux<Movie> findByTitle(String title);
}
