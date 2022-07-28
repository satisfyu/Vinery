package daniking.vinery.util;

import net.minecraft.util.StringIdentifiable;

public enum EnumBlockSide implements StringIdentifiable {
	CENTER("center"),
	TOP("top"),
	BOTTOM("bottom");
	
	private final String name;
	
	private EnumBlockSide(String name) {
		this.name = name;
	}
	
	@Override
	public String asString() {
		return this.name;
	}
}