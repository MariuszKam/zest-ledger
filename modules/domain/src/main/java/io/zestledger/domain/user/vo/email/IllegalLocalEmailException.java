package io.zestledger.domain.user.vo.email;

public class IllegalLocalEmailException extends RuntimeException {
	public IllegalLocalEmailException(String message) {
		super(message);
	}
}
