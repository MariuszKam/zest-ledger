package io.zestledger.domain.utils;

class UtilityClassException extends RuntimeException {
    public UtilityClassException() {
        super("Utility Class cannot create instance");
    }
}
