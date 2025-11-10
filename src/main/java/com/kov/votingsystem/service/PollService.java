package com.kov.votingsystem.service;

import com.kov.votingsystem.model.Poll;
import com.kov.votingsystem.repository.PollRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class PollService {
	private final PollRepository repository;

	public PollService(PollRepository repository) {
		this.repository = repository;
	}

	public Poll createPoll(String question, List<String> options) {
		Poll poll = new Poll(question, options);
		return repository.save(poll);
	}

	public void vote(String pollId, String participantId, String option) {
		Poll poll = repository.findById(pollId).orElseThrow(() -> new PollNotFoundException("Poll not found: " + pollId));
		poll.registerVote(participantId, option);
	}

	public Map<String, Integer> results(String pollId) {
		Poll poll = repository.findById(pollId).orElseThrow(() -> new PollNotFoundException("Poll not found: " + pollId));
		return poll.getResults();
	}

	public void close(String pollId) {
		Poll poll = repository.findById(pollId).orElseThrow(() -> new PollNotFoundException("Poll not found: " + pollId));
		poll.close();
	}
}


