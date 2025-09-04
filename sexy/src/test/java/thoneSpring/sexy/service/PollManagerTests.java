package thoneSpring.sexy.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import thoneSpring.sexy.model.User;
import thoneSpring.sexy.model.Poll;

import java.time.Instant;
import java.util.UUID;


@SpringBootTest
class PollManagerTests {

    private PollManager pollManager;

    @BeforeEach
    void
    setup() {
        pollManager = new PollManager();
    }   

 // USER TEST
    @Test
    void testAddAndGetUser() {
        User user = new User();
        user.setUsername("thone");
        user.setEmail("thone@example.com");

        User saved = pollManager.addUser(user);

        assertNotNull(saved.getId(), "User ID should be generated");
        assertEquals("thone", saved.getUsername());
        assertEquals("thone@example.com", saved.getEmail());

        User found = pollManager.getUser(saved.getId());
        assertNotNull(found, "User should be found by ID");
        assertEquals(saved.getId(), found.getId());
    }

    // @Test
    // void testUpdateUser() {
    //     User user = new User();
    //     user.setUsername("anlaug");
    //     user.setEmail("anlaug@example.com");
    //     User saved = pollManager.addUser(user);

    //     User updated = new User();
    //     updated.setUsername("anlaug_updated");
    //     updated.setEmail("anlaug@updated.com");

    //     User result = pollManager.updateUser(saved.getId(), updated);
    //     assertNotNull(result, "Updated user should not be null");
    //     assertEquals("anlaug_updated", result.getUsername());
    //     assertEquals("saved.getId()", result.getId(), "ID should remain the same");
    // }

// POLL TEST
    @Test
    void testAddAndGetPoll() {
        Poll poll = new Poll();
        poll.setQuestion("Is this a test?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));

        Poll saved = pollManager.addPoll(poll);

        assertNotNull(saved.getId(), "Poll ID should be generated");
        assertEquals("Is this a test?", saved.getQuestion());

        Poll found = pollManager.getPoll(saved.getId());
        assertNotNull(found, "Poll should be found by ID");
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void testUpdatePoll() {
        Poll poll = new Poll();
        poll.setQuestion("Initial Question?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));
        Poll saved = pollManager.addPoll(poll);

        Poll updated = new Poll();
        updated.setQuestion("Updated Question?");
        updated.setPublishedAt(saved.getPublishedAt());
        updated.setValidUntil(saved.getValidUntil());

        Poll result = pollManager.updatePoll(saved.getId(), updated);
        assertNotNull(result);
        assertEquals("Updated Question?", result.getQuestion());
        assertEquals(saved.getId(), result.getId(), "ID should remain the same");
    }

    @Test
    void testDeletePoll() {
        Poll poll = new Poll();
        poll.setQuestion("To be deleted?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));
        Poll saved = pollManager.addPoll(poll);

        boolean deleted = pollManager.deletePoll(saved.getId());
        assertTrue(deleted, "Poll should be deleted");
        assertNull(pollManager.getPoll(saved.getId()), "Poll should no longer exist");
    }
}




