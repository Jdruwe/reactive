package be.jeroendruwe.reactive.web;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.models.MovieEvent;
import be.jeroendruwe.reactive.service.MovieService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

class MovieHandler {

    private final MovieService movieService;

    MovieHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    @NotNull
    Mono<ServerResponse> allMovies(ServerRequest request) {
        return ok().body(movieService.getAllMovies(), Movie.class);
    }

    @NotNull
    Mono<ServerResponse> movieById(ServerRequest request) {

        String movieId = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Movie> movieMono = movieService.getMovieById(movieId);

        return movieMono
                .flatMap(movie -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(movie)))
                .switchIfEmpty(notFound);
    }

    @NotNull
    Mono<ServerResponse> events(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(movieService.getEvents(request.pathVariable("id")), MovieEvent.class);
    }

    @NotNull
    Mono<ServerResponse> saveMovie(ServerRequest request) {
        // https://www.youtube.com/watch?v=upFFlGq5-NU 34:10
        Mono<Movie> movieMono = request.bodyToMono(Movie.class);
        return ok().body(movieService.saveMovie(movieMono), Movie.class);
    }

}
