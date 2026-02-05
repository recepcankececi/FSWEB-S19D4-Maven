package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.MovieRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie save(@RequestBody MovieRequest movieRequest) {
        Movie movie = movieRequest.getMovie();
        List<Actor> actors = movieRequest.getActors();
        
        if (actors != null && !actors.isEmpty()) {
            for (Actor actor : actors) {
                movie.addActor(actor);
            }
        }
        
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie movie) {
        Movie existingMovie = movieService.findById(id);
        
        movie.setId(existingMovie.getId());
        
        return movieService.save(movie);
    }

    @DeleteMapping("/{id}")
    public Movie delete(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        movieService.delete(movie);
        return movie;
    }
}
