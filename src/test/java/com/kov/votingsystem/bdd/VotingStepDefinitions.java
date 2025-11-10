package com.kov.votingsystem.bdd;

import com.kov.votingsystem.service.PollService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class VotingStepDefinitions {
	private final PollService pollService;
	private String pollId;

	public VotingStepDefinitions(PollService pollService) {
		this.pollService = pollService;
	}

	@Given("создано голосование с вопросом {string} и опциями {string}")
	public void createPoll(String question, String optionsCsv) {
		List<String> options = Arrays.stream(optionsCsv.split(",")).map(String::trim).toList();
		var poll = pollService.createPoll(question, options);
		this.pollId = poll.getId();
	}

	@When("участник {string} голосует за {string}")
	public void vote(String user, String option) {
		pollService.vote(pollId, user, option);
	}

	@Then("результат для {string} равен {int}")
	public void thenResult(String option, Integer expected) {
		Map<String, Integer> results = pollService.results(pollId);
		Assertions.assertThat(results.get(option)).isEqualTo(expected);
	}
}


