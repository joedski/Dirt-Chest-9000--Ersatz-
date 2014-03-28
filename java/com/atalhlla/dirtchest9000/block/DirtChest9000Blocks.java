package com.atalhlla.dirtchest9000.block;

import net.minecraft.block.Block;

import com.atalhlla.dirtchest9000.DirtChest9000Mod;

public class DirtChest9000Blocks {
	public static Block dirtChest9000;
	
	public static void registerBlocks() throws InstantiationException, IllegalAccessException {
		dirtChest9000 = DirtChest9000Mod.instance.utils.registerBlock( BlockDirtChest.class );
	}
}
