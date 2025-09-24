package io.zestledger.domain.user.vo.email;

class EmailLengthException extends RuntimeException {
    public EmailLengthException() {
        super("Email cannot have more than 254 characters");
    }
}
