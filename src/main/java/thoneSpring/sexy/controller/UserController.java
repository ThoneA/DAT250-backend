package thoneSpring.sexy.controller;

import org.springframework.web.bind.annotation.*;
import thoneSpring.sexy.model.User;
import thoneSpring.sexy.service.PollManager;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PollManager pollManager;

    public UserController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public Collection<User> getAllUsers() {
        return pollManager.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) {
        return pollManager.getUser(id);
    }       

    @PostMapping
    public User createUser(@RequestBody User user) {
        return pollManager.addUser(user);
    }

    // @PutMapping("/{id}")
    // public User updateUser(@PathVariable UUID id, @RequestBody User user) {
    //     return pollManager.updateUser(id, user);
    // }   

}