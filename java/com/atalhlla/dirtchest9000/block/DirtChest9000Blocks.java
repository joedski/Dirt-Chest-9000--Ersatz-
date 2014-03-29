package com.atalhlla.dirtchest9000.block;

import net.minecraft.block.Block;

import com.atalhlla.dirtchest9000.DirtChest9000Mod;
import com.atalhlla.dirtchest9000.tileentity.TileEntityDirtChest;

import cpw.mods.fml.common.registry.GameRegistry;

public class DirtChest9000Blocks {
	public static Block dirtChest9000;
	
	public static void registerBlocks() throws InstantiationException, IllegalAccessException {
		dirtChest9000 = DirtChest9000Mod.instance.utils.registerBlock( BlockDirtChest.class );
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity( TileEntityDirtChest.class, DirtChest9000Mod.MODID + "." + BlockDirtChest.NAME + "_tile_entity" );
	}
}
