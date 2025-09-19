package io.zestledger.architecture;

public enum Modules {
    DOMAIN("..domain.."),
    APPLICATION("..application.."),
    ADAPTERS_OUT("..adapters.out.."),
    ADAPTERS_IN("..adapters.in.."),
    PLATFORM("..platform..");

    private final String packageName;

    Modules(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}
