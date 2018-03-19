package be.jeroendruwe.reactive.web;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.models.MovieEvent;
import be.jeroendruwe.reactive.service.MovieService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebConfiguration {

    // Same as MovieController but a different style

    private final MovieService movieService;

    public WebConfiguration(MovieService movieService) {
        this.movieService = movieService;
    }

    private Mono<ServerResponse> allMovies(ServerRequest request) {
        return ok().body(movieService.getAllMovies(), Movie.class);
    }

    private Mono<ServerResponse> movieById(ServerRequest request) {
        return ok().body(movieService.getMovieById(request.pathVariable("id")), Movie.class);
    }

    private Mono<ServerResponse> events(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(movieService.getEvents(request.pathVariable("id")), MovieEvent.class);
    }

    @NotNull
    private Mono<ServerResponse> saveMovie(ServerRequest request) {
        Mono<Movie> movieMono = request.bodyToMono(Movie.class);
        return ok().body(movieService.saveMovie(movieMono), Movie.class);
    }

    @Bean
    RouterFunction<?> routerFunction() {
        return nest(path("/movies"),
                route(GET("/{id}/events"), this::events)
                        .andRoute(GET("/{id}"), this::movieById)
                        .andRoute(method(GET), this::allMovies)
                        .andRoute(method(POST).and(contentType(MediaType.APPLICATION_JSON)), this::saveMovie));
    }
}