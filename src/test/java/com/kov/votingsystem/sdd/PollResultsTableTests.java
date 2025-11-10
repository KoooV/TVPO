package com.kov.votingsystem.sdd;

import com.kov.votingsystem.service.PollService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// SDD: табличные сценарии. Входные данные и ожидаемый результат задаются таблицей.
class PollResultsTableTests {
	@ParameterizedTest(name = "A={0}, B={1} -> ожидается A={2}, B={3}")
	@CsvSource({
		"'A,A,B', 'A,B', 2, 1",
		"'B,B,B', 'A,B', 0, 3",
		"'A',      'A,B', 1, 0"
	})
	void tableDriven(String votesCsv, String optionsCsv, int expectedA, int expectedB) {
		var service = new PollService();
		var poll = service.createPoll("Q", List.of(optionsCsv.split(",")));
		String[] votes = votesCsv.split(",");
		for (int i = 0; i < votes.length; i++) {
			service.vote(poll.getId(), "u" + (i + 1), votes[i]);
		}
		var res = service.results(poll.getId());
		assertThat(res.get("A")).isEqualTo(expectedA);
		assertThat(res.get("B")).isEqualTo(expectedB);
	}
}


