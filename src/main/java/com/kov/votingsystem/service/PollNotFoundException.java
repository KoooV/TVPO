package com.kov.votingsystem.service;

public class PollNotFoundException extends RuntimeException {
	public PollNotFoundException(String message) {
		super(message);
	}
}


