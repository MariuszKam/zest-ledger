package io.zestledger.domain.entity.user.vo.email;

class DomainEmailLengthException extends RuntimeException {
    public DomainEmailLengthException() {
        super("Email domain part cannot have more than 253 characters");
    }
}
