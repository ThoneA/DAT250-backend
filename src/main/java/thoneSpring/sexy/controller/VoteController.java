package thoneSpring.sexy.controller;

import org.springframework.web.bind.annotation.*;
import thoneSpring.sexy.model.Vote;
import thoneSpring.sexy.service.PollManager;

import java.util.Collection;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/votes")
public class VoteController {

    private final PollManager pollManager;

    public VoteController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public Collection<Vote> getAllVotes() {
        return pollManager.getAllVotes();
    }

    @GetMapping("/{id}")
    public Vote getVote(@PathVariable UUID id) {
        return pollManager.getVote(id);
    }

    @PostMapping
    public Vote createVote(@RequestBody Vote vote) {
        return pollManager.addVote(vote);
    }

    @PutMapping("/{id}")
    public Vote updateVote(@PathVariable UUID id, @RequestBody Vote updated) {
        return pollManager.updateVote(id, updated);
    }

    @DeleteMapping("/{id}")
    public boolean deleteVote(@PathVariable UUID id) {
        return pollManager.deleteVote(id);
    }
}
