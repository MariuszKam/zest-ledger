package io.zestledger.domain.entity.user.vo.email;

public class IllegalDomainEmailException extends RuntimeException {
    public IllegalDomainEmailException(String message) {
        super(message);
    }
}
