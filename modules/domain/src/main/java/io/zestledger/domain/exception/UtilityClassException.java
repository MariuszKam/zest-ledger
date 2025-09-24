package io.zestledger.domain.exception;

public class UtilityClassException extends RuntimeException {
	public UtilityClassException() {
		super("Utility Class cannot create instance");
	}
}
