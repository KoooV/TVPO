package com.kov.votingsystem.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PollApiAtddTests {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	record CreatePollRequest(String question, List<String> options) {}
	record CreatePollResponse(String id, String question, List<String> options) {}
	record VoteRequest(String participantId, String option) {}

	@Test
	@DisplayName("ATDD: Каждый голос учитывается один раз")
	void eachVoteCountedOnce() throws Exception {
		var createBody = objectMapper.writeValueAsString(new CreatePollRequest("Best?", List.of("A", "B")));
		var createResp = mockMvc.perform(post("/api/polls")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createBody))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		var created = objectMapper.readValue(createResp, CreatePollResponse.class);

		var v1 = objectMapper.writeValueAsString(new VoteRequest("u1", "A"));
		mockMvc.perform(post("/api/polls/" + created.id() + "/votes")
				.contentType(MediaType.APPLICATION_JSON).content(v1))
			.andExpect(status().isAccepted());

		// Повторный голос того же участника — 400
		mockMvc.perform(post("/api/polls/" + created.id() + "/votes")
				.contentType(MediaType.APPLICATION_JSON).content(v1))
			.andExpect(status().isBadRequest());

		// Проверяем результаты
		var resBody = mockMvc.perform(get("/api/polls/" + created.id() + "/results"))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		Map<String, Integer> results = objectMapper.readValue(resBody, Map.class);
		assertThat(results.get("A")).isEqualTo(1);
	}
}


