package be.jeroendruwe.reactive.service;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.models.MovieEvent;
import be.jeroendruwe.reactive.repository.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    public Mono<Movie> getMovieById(String id) {
        return this.movieRepository.findById(id);
    }

    public Flux<MovieEvent> getEvents(String movieId) {
        return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date())))
                .delayElements(Duration.ofSeconds(1));
    }
}
