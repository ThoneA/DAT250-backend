package thoneSpring.sexy.controller;

import org.springframework.web.bind.annotation.*;
import thoneSpring.sexy.model.Poll;
import thoneSpring.sexy.service.PollManager;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/polls")
public class PollController {
    
    private final PollManager pollManager;
    
    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public Collection<Poll> getAllPolls() {
        return pollManager.getAllPolls();
    }

    @GetMapping("/{id}")
    public Poll getPoll(@PathVariable UUID id) {
        return pollManager.getPoll(id);
    }       

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollManager.addPoll(poll);
    }

    @PutMapping("/{id}")
    public Poll updatePoll(@PathVariable UUID id, @RequestBody Poll poll) {
        return pollManager.updatePoll(id, poll);
    }   

    @DeleteMapping("/{id}")
    public boolean deletePoll(@PathVariable UUID id) {
        return pollManager.deletePoll(id);
    }
}