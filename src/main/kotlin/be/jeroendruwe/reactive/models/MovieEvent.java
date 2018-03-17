package be.jeroendruwe.reactive.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieEvent {
    private String movieId;
    private Date dateViewed;

}
