package thoneSpring.sexy.controller;

import org.springframework.web.bind.annotation.*;
import thoneSpring.sexy.model.VoteOption;
import thoneSpring.sexy.service.PollManager;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/options")
public class VoteOptionController {

    private final PollManager pollManager;

    public VoteOptionController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public Collection<VoteOption> getAllOptions() {
        return pollManager.getAllVoteOptions();
    }

    @GetMapping("/{id}")
    public VoteOption getOption(@PathVariable UUID id) {
        return pollManager.getVoteOption(id);
    }

    @PostMapping
    public VoteOption createOption(@RequestBody VoteOption option) {
        return pollManager.addVoteOption(option);
    }

    @PutMapping("/{id}")
    public VoteOption updateOption(@PathVariable UUID id, @RequestBody VoteOption updated) {
        return pollManager.updateVoteOption(id, updated);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOption(@PathVariable UUID id) {
        return pollManager.deleteVoteOption(id);
    }
}
