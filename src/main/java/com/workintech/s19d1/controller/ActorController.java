package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/actor")
public class ActorController {

    private ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public Actor findById(@PathVariable Long id) {
        return actorService.findById(id);
    }

    @PostMapping
    public Actor save(@RequestBody ActorRequest actorRequest) {
        Actor actor = actorRequest.getActor();
        List<Movie> movies = actorRequest.getMovies();
        
        if (movies != null && !movies.isEmpty()) {
            for (Movie movie : movies) {
                actor.addMovie(movie);
            }
        }
        
        return actorService.save(actor);
    }

    @PutMapping("/{id}")
    public Actor update(@PathVariable Long id, @RequestBody Actor actor) {
        Actor existingActor = actorService.findById(id);
        
        actor.setId(existingActor.getId());
        
        return actorService.save(actor);
    }

    @DeleteMapping("/{id}")
    public Actor delete(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        actorService.delete(actor);
        return actor;
    }
}
