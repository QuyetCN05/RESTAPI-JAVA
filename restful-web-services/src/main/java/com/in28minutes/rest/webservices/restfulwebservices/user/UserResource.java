package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private UserDaoService service;

    public UserResource(UserDaoService service){
        this.service=service;
    }

    //GET/ User
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.finAll();

    }

    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id){

        User user = service.findOne(id);

        if(user==null)
            throw new UserNotFoundException("id:"+id);

        return user;

    }

    //Post/users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
