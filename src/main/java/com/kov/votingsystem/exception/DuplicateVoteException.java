package com.kov.votingsystem.exception;

public class DuplicateVoteException extends RuntimeException {
	public DuplicateVoteException(String message) {
		super(message);
	}
}


