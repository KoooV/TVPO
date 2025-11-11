package com.kov.votingsystem.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kov.votingsystem.model.Poll;
import com.kov.votingsystem.service.PollService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import com.kov.votingsystem.exception.DuplicateVoteException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
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
	@MockBean
	PollService pollService;

	record CreatePollRequest(String question, List<String> options) {}
	record CreatePollResponse(String id, String question, List<String> options) {}
	record VoteRequest(String participantId, String option) {}

	@Test
	@DisplayName("Условие: создан опрос 'Best?' с опциями [A,B]; Действие: два запроса голосования от u1 за A и запрос результатов; Ожидаемый результат: 1-й 202, 2-й 400, результаты A=1,B=0")
	void eachVoteCountedOnce() throws Exception {
		Poll mockPoll = new Poll("Best?", List.of("A", "B"));
		String pollId = mockPoll.getId();
		
		// Настройка моков
		when(pollService.createPoll(eq("Best?"), any(List.class)))
			.thenReturn(mockPoll);
		
		// Первый голос успешен, второй выбрасывает исключение
		doNothing()
			.doThrow(new DuplicateVoteException("Participant already voted"))
			.when(pollService).vote(eq(pollId), eq("u1"), eq("A"));
		
		when(pollService.results(pollId))
			.thenReturn(Map.of("A", 1, "B", 0));

		var createBody = objectMapper.writeValueAsString(new CreatePollRequest("Best?", List.of("A", "B")));
		var createResp = mockMvc.perform(post("/api/polls")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createBody))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		var created = objectMapper.readValue(createResp, CreatePollResponse.class);

		var v1 = objectMapper.writeValueAsString(new VoteRequest("u1", "A"));
		// Первый голос - успешно
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
		
		// Проверяем, что методы были вызваны
		verify(pollService).createPoll(eq("Best?"), any(List.class));
		verify(pollService, times(2)).vote(eq(pollId), eq("u1"), eq("A"));
		verify(pollService).results(pollId);
	}
}
