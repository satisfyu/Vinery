package satisfyu.vinery.util;

import net.minecraft.util.StringRepresentable;

public enum VineryLineConnectingType implements StringRepresentable {
	NONE("none"), MIDDLE("middle"), LEFT("left"), RIGHT("right");

	private final String name;

	private VineryLineConnectingType(String type) {
		this.name = type;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
