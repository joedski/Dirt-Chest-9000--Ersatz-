package com.atalhlla.dirtchest9000.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.atalhlla.dirtchest9000.DirtChest9000Mod;
import com.atalhlla.dirtchest9000.client.gui.GuiDirtChest;
import com.atalhlla.dirtchest9000.inventory.ContainerDirtChest;
import com.atalhlla.dirtchest9000.tileentity.TileEntityDirtChest;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class DirtChest9000GuiHandler implements IGuiHandler {

	public DirtChest9000GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler( DirtChest9000Mod.instance, this );
	}

	@Override
	public Object getServerGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z ) {
		TileEntity tileEntity = world.getTileEntity( x, y, z );

		switch( ID ) {
		case 0:
			if( tileEntity != null || (tileEntity instanceof TileEntityDirtChest) )
				return new ContainerDirtChest(
					player.inventory,
					(TileEntityDirtChest) tileEntity );
			else
				break;

		default:
			break;
		}

		return null;
	}

	@Override
	public Object getClientGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z ) {
		TileEntity tileEntity = world.getTileEntity( x, y, z );

		switch( ID ) {
		case 0:
			if( tileEntity != null || (tileEntity instanceof TileEntityDirtChest) )
				return new GuiDirtChest(
					player.inventory,
					(TileEntityDirtChest) tileEntity );
			else
				break;

		default:
			break;
		}

		return null;
	}

}
