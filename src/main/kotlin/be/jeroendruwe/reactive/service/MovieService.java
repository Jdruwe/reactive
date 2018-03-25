package be.jeroendruwe.reactive.service;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.models.MovieEvent;
import be.jeroendruwe.reactive.properties.MovieProperties;
import be.jeroendruwe.reactive.repository.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieProperties movieProperties;

    public MovieService(MovieRepository movieRepository, MovieProperties movieProperties) {
        this.movieRepository = movieRepository;
        this.movieProperties = movieProperties;
    }

    public Flux<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    public Flux<String> getAllTitles() {
        Flux<Movie> all = this.movieRepository.findAll();
        return all.map(movie -> movieProperties.getPrefix() + " - " + movie.getTitle());
    }

    public Mono<String> getServiceName() {
        return Mono.just(MovieService.class.getSimpleName());
    }

    public Mono<Movie> getMovieById(String id) {
        return this.movieRepository.findById(id);
    }

    public Flux<MovieEvent> getEvents(String movieId) {
        return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date())))
                .delayElements(Duration.ofSeconds(1));
    }

    public Mono<Movie> saveMovie(Mono<Movie> movieMono) {
        return movieMono.flatMap(movieRepository::save);
    }
}
