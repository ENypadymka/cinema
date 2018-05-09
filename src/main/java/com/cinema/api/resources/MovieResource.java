package com.cinema.api.resources;

import com.cinema.api.model.ErrorResponse;
import com.cinema.api.model.movie.MovieRequest;
import com.cinema.api.model.movie.MovieResponse;
import com.cinema.api.model.movie.MovieUpdateRequest;
import com.cinema.data.entity.Movie;
import com.cinema.data.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MovieResource.
 */
@RestController
public class MovieResource {

    @Autowired
    private MovieRepository movieRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/private/movie", method = RequestMethod.POST, produces = "application/json")
    public MovieResponse addMovie(@RequestBody MovieRequest request) {
        Movie movie = movieRepository.save(new Movie(request.getName(), request.getDescription(), request.getDirector()));
        return new MovieResponse(
                movie.getId().toHexString(),
                movie.getName(),
                movie.getDescription(),
                movie.getDirector()
        );
    }

    @RequestMapping(path = "/public/movie", method = RequestMethod.GET, produces = "application/json")
    public List<MovieResponse> getMovies() {
        return toMovieResponses(movieRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/private/movie/{movie_id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity removeMovie(@PathVariable("movie_id") String movieId) {
        movieRepository.deleteById(new ObjectId(movieId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/private/movie/{movie_id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateDescription(@PathVariable("movie_id") String movieId, @RequestBody MovieUpdateRequest request) {
        Optional<Movie> movieOptional = movieRepository.findById(new ObjectId(movieId));
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            movie.setDescription(request.getDescription());
            movieRepository.save(movie);
            return ResponseEntity.ok(new MovieResponse(
                    movie.getId().toHexString(),
                    movie.getName(),
                    movie.getDescription(),
                    movie.getDirector())
            );
        }

        return ResponseEntity.badRequest().body(new ErrorResponse("Movie can't be found"));
    }

    private List<MovieResponse> toMovieResponses(Iterable<Movie> movies) {
        List<MovieResponse> movieResponses = new ArrayList<>();
        movies.forEach(movie -> movieResponses.add(new MovieResponse(
                movie.getId().toHexString(),
                movie.getName(),
                movie.getDescription(),
                movie.getDirector()
        )));

        return movieResponses;
    }

}
