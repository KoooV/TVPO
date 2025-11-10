package com.kov.votingsystem.repository;

import com.kov.votingsystem.model.Poll;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPollRepository implements PollRepository {
	private final Map<String, Poll> store = new ConcurrentHashMap<>();

	@Override
	public Optional<Poll> findById(String id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Poll save(Poll poll) {
		store.put(poll.getId(), poll);
		return poll;
	}

	@Override
	public Collection<Poll> findAll() {
		return store.values();
	}
}


