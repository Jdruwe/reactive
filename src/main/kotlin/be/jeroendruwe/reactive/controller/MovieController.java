package be.jeroendruwe.reactive.controller;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.models.MovieEvent;
import be.jeroendruwe.reactive.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/movies")
public class MovieController {

//    private final MovieService movieService;
//
//    public MovieController(MovieService movieService) {
//        this.movieService = movieService;
//    }
//
//    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    Flux<MovieEvent> events(@PathVariable String id) {
//        return movieService.getEvents(id);
//    }
//
//    @GetMapping("/{id}")
//    Mono<Movie> byId(@PathVariable String id) {
//        return movieService.getMovieById(id);
//    }
//
//    @GetMapping
//    Flux<Movie> all() {
//        return movieService.getAllMovies();
//    }
//
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    Mono<Movie> saveMovie(@RequestBody Movie movie) {
//        return movieService.saveMovie(movie);
//    }
}
