package be.jeroendruwe.reactive.web;

import be.jeroendruwe.reactive.service.MovieService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfiguration {

    // Same as MovieController but a different style
    private final MovieHandler movieHandler;

    public WebConfiguration(MovieService movieService, MeterRegistry meterRegistry) {
        this.movieHandler = new MovieHandler(movieService, meterRegistry);
    }

    // Composed router functions are evaluated in order, so it makes sense to put specific functions before generic ones.

    @Bean
    RouterFunction<?> routerFunction() {
        return nest(path("/movies"),
                route(GET("/{id}/events"), movieHandler::events)
                        .andRoute(GET("/service"), movieHandler::serviceName)
                        .andRoute(GET("/{id}"), movieHandler::movieById)
                        .andRoute(method(GET), movieHandler::allMovies)
                        .andRoute(method(POST).and(contentType(MediaType.APPLICATION_JSON)), movieHandler::saveMovie));
    }
}