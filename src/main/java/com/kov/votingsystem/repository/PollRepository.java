package com.kov.votingsystem.repository;

import com.kov.votingsystem.model.Poll;

import java.util.Collection;
import java.util.Optional;

public interface PollRepository {
	Optional<Poll> findById(String id);
	Poll save(Poll poll);
	Collection<Poll> findAll();
}


