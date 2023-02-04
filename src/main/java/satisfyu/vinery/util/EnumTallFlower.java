package satisfyu.vinery.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.StringIdentifiable;

public enum EnumTallFlower implements StringIdentifiable {
	NONE("none", Blocks.AIR),
	LILAC("lilac", Blocks.LILAC),
	PEONY("peony", Blocks.PEONY),
	ROSE_BUSH("rose_bush", Blocks.ROSE_BUSH),
	SUNFLOWER("sunflower", Blocks.SUNFLOWER);
	
	private final String name;
	private final Block flower;
	
	private EnumTallFlower(String name, Block flower) {
		this.name = name;
		this.flower = flower;
	}
	
	@Override
	public String asString() {
		return this.name;
	}
	
	public Block getFlower() {
		return this.flower;
	}
}
