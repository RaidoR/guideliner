package ee.ttu.usability.domain.page;

public enum LayoutType {
	FLUID;
	
	public static LayoutType convertToLayoutType(String layout) {
		if (layout.equalsIgnoreCase(FLUID.name())) {
			return LayoutType.FLUID;
		}
		return null;
	}
}
