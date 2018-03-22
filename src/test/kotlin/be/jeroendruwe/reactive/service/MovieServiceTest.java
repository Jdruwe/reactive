package be.jeroendruwe.reactive.service;

import be.jeroendruwe.reactive.models.Movie;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.swing.*;

import java.time.Duration;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getEventsTake10() {
        String movieId = movieService.getAllMovies().blockFirst().getId();

        StepVerifier.withVirtualTime(() -> movieService.getEvents(movieId).take(10))
                .thenAwait(Duration.ofHours(10))
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void testGetAllMovies() {
        webTestClient.get().uri("/movies")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Movie.class);
    }

    @Test
    public void testSaveMovie() {

        Movie movie = new Movie("Test movie");

        webTestClient.post().uri("/movies")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(movie), Movie.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.title").isEqualTo("Test movie");
    }

    @Test
    public void testGetSingleMovie() {

        Movie movie = movieService.saveMovie(Mono.just(new Movie("Single movie"))).block();

        webTestClient.get()
                .uri("/movies/{id}", Collections.singletonMap("id", movie.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }


}