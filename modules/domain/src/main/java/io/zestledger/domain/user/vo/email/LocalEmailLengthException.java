package io.zestledger.domain.user.vo.email;

class LocalEmailLengthException extends RuntimeException {
    public LocalEmailLengthException() {
        super("Email Local part cannot have more than 64 characters");
    }
}
