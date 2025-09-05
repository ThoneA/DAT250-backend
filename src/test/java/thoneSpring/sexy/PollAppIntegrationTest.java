package thoneSpring.sexy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import thoneSpring.sexy.model.Poll;
import thoneSpring.sexy.model.User;
import thoneSpring.sexy.model.Vote;
import thoneSpring.sexy.model.VoteOption;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PollAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fullScenario() throws Exception {
        // 1. Create user1 thone
        User user1 = new User();
        user1.setUsername("thone");
        user1.setEmail("thone@example.com");
        String user1Json = objectMapper.writeValueAsString(user1);

        String user1Response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user1Json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User savedUser1 = objectMapper.readValue(user1Response, User.class);

        // 2. Create poll by user1
        Poll poll = new Poll();
        poll.setQuestion("Best programming language?");
        poll.setPublishedAt(Instant.now());
        poll.setValidUntil(Instant.now().plusSeconds(3600));
        String pollJson = objectMapper.writeValueAsString(poll);

        mockMvc.perform(post("/polls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pollJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Best programming language?"));

        // 3. List users
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("thone"));
    }
}
