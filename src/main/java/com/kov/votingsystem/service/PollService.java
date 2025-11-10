package com.kov.votingsystem.service;

import com.kov.votingsystem.model.Poll;
import org.springframework.stereotype.Service;
import com.kov.votingsystem.exception.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PollService {
	private final Map<String, Poll> store = new ConcurrentHashMap<>();

	public Poll createPoll(String question, List<String> options) {
		Poll poll = new Poll(question, options);
		store.put(poll.getId(), poll);
		return poll;
	}

	public void vote(String pollId, String participantId, String option) {
		Poll poll = store.get(pollId);
		if (poll == null) {
			throw new PollNotFoundException("Poll not found: " + pollId);
		}
		poll.registerVote(participantId, option);
	}

	public Map<String, Integer> results(String pollId) {
		Poll poll = store.get(pollId);
		if (poll == null) {
			throw new PollNotFoundException("Poll not found: " + pollId);
		}
		return poll.getResults();
	}

	public void close(String pollId) {
		Poll poll = store.get(pollId);
		if (poll == null) {
			throw new PollNotFoundException("Poll not found: " + pollId);
		}
		poll.close();
	}
}


