package com.kov.votingsystem.service;

import com.kov.votingsystem.exception.DuplicateVoteException;
import com.kov.votingsystem.exception.PollClosedException;
import com.kov.votingsystem.exception.UnknownOptionException;
import com.kov.votingsystem.model.Poll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PollServiceTests {
	private PollService service;
	@Mock
	private Poll mockPoll;

	@BeforeEach
	void setUp() {
		service = new PollService();
	}

	@Test
	@DisplayName("Подсчёт голосов: каждый голос увеличивает счёт выбранной опции")
	void countVotes() {
		var poll = service.createPoll("Q", List.of("A", "B"));
		service.vote(poll.getId(), "u1", "A");
		service.vote(poll.getId(), "u2", "A");
		service.vote(poll.getId(), "u3", "B");
		Map<String, Integer> results = service.results(poll.getId());
		assertThat(results).containsEntry("A", 2).containsEntry("B", 1);
	}

	@Test
	@DisplayName("Исключение при повторном голосе одного участника")
	void duplicateVote() {
		var poll = service.createPoll("Q", List.of("A", "B"));
		service.vote(poll.getId(), "u1", "A");
		assertThatThrownBy(() -> service.vote(poll.getId(), "u1", "B"))
			.isInstanceOf(DuplicateVoteException.class);
	}

	@Test
	@DisplayName("Исключение при голосе за неизвестную опцию")
	void unknownOption() {
		var poll = service.createPoll("Q", List.of("A"));
		assertThatThrownBy(() -> service.vote(poll.getId(), "u1", "X"))
			.isInstanceOf(UnknownOptionException.class);
	}

	@Test
	@DisplayName("Исключение при голосе в закрытом голосовании")
	void closedPoll() {
		var poll = service.createPoll("Q", List.of("A"));
		service.close(poll.getId());
		assertThatThrownBy(() -> service.vote(poll.getId(), "u1", "A"))
			.isInstanceOf(PollClosedException.class);
	}

	@Test
	@DisplayName("Пример использования мока: тестирование с мокированным Poll")
	void mockPollExample() {
		// Пример демонстрации использования @Mock
		// В реальных тестах Poll - это простая модель, мокирование не требуется
		when(mockPoll.getId()).thenReturn("mock-id");
		when(mockPoll.getQuestion()).thenReturn("Mock Question");
		
		assertThat(mockPoll.getId()).isEqualTo("mock-id");
		assertThat(mockPoll.getQuestion()).isEqualTo("Mock Question");
		verify(mockPoll).getId();
		verify(mockPoll).getQuestion();
	}
}


