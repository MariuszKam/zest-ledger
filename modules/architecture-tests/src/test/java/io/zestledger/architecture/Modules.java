package io.zestledger.architecture;

public enum Modules {
	DOMAIN("..domain.."),
	APPLICATION("..application.."),
	ADAPTERS_OUT("..out.."),
	ADAPTERS_IN("..in.."),
	PLATFORM("..platform..");

	private final String packageName;

	Modules(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}
}
