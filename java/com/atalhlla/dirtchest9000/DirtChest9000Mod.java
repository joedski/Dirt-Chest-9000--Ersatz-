package com.atalhlla.dirtchest9000;

import com.atalhlla.dirtchest9000.block.DirtChest9000Blocks;
import com.atalhlla.dirtchest9000.common.network.DirtChest9000GuiHandler;
import com.atalhlla.dirtchest9000.proxy.CommonProxy;
import com.atalhlla.util.minecraft.ModUtils;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/*
 * A bad re-implementation of the Iron Chests' Dirt Chest 9000.
 */

@Mod( name = DirtChest9000Mod.MODNAME, modid = DirtChest9000Mod.MODID, version = DirtChest9000Mod.VERSION )
public class DirtChest9000Mod {
	public static final String MODID = "dirtchest9000";
	public static final String MODNAME = "Dirt Chest 9000!";
	public static final String VERSION = "a0.0";

	@Instance( MODID )
	public static DirtChest9000Mod instance;

	@SidedProxy( clientSide = "com.atalhlla.dirtchest9000.proxy.ClientProxy", serverSide = "com.atalhlla.dirtchest9000.proxy.CommonProxy" )
	public static CommonProxy proxy;

	public ModUtils utils = new ModUtils( this );

	@EventHandler
	public void preInit( FMLPreInitializationEvent event ) throws InstantiationException, IllegalAccessException {
		DirtChest9000Blocks.registerBlocks();
		DirtChest9000Blocks.registerTileEntities();
	}

	@EventHandler
	public void init( FMLInitializationEvent event ) {
		// Don't need to reference from here?  (It's already retained by the Forge NetworkRegistry.)
		new DirtChest9000GuiHandler();
	}

	@EventHandler
	public void postInit( FMLPostInitializationEvent event ) {
		// post-init...
	}
}
