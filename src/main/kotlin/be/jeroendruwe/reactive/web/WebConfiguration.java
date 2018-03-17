package be.jeroendruwe.reactive.web;

import be.jeroendruwe.reactive.models.Movie;
import be.jeroendruwe.reactive.models.MovieEvent;
import be.jeroendruwe.reactive.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebConfiguration {

    // Same as MovieController but a different style

//    @Bean
//    RouterFunction<?> routerFunction(MovieService ms) {
//        return route(GET("/movies"),
//                serverRequest -> ok().body(ms.getAllMovies(), Movie.class))
//                .andRoute(GET("/movies/{id}"),
//                        serverRequest -> ok().body(ms.getMovieById(serverRequest.pathVariable("id")), Movie.class))
//                .andRoute(GET("/movies/{id}/events"),
//                        serverRequest -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(ms.getEvents(serverRequest.pathVariable("id")), MovieEvent.class));
//    }
}