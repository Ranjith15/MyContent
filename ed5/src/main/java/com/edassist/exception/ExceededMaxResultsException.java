package com.edassist.exception;

public class ExceededMaxResultsException extends Exception {

	private static final long serialVersionUID = -3955223446175817186L;
	private static final String defaultMessage = "Result set exceeded the maximum allowed. Please specify additional criteria to narrow your search.";

	public ExceededMaxResultsException(String message) {
		super(message);
	}

	public ExceededMaxResultsException(int maxAllowed) {
		super(defaultMessage /* + " ["+maxAllowed+"]" */);
	}

	public ExceededMaxResultsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceededMaxResultsException(Throwable cause) {
		super(cause);
	}
}
