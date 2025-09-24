package io.zestledger.domain.user.vo.email;

public class IllegalDomainEmailException extends RuntimeException {
	public IllegalDomainEmailException(String message) {
		super(message);
	}
}
