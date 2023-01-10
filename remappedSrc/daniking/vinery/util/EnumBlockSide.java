package daniking.vinery.util;

import net.minecraft.util.StringRepresentable;

public enum EnumBlockSide implements StringRepresentable {
	CENTER("center"),
	TOP("top"),
	BOTTOM("bottom");
	
	private final String name;
	
	private EnumBlockSide(String name) {
		this.name = name;
	}
	
	@Override
	public String getSerializedName() {
		return this.name;
	}
}