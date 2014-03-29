package com.atalhlla.dirtchest9000.inventory;

import com.atalhlla.dirtchest9000.tileentity.TileEntityDirtChest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerDirtChest extends Container {
	
	private TileEntityDirtChest tileEntity;
	
	public ContainerDirtChest( InventoryPlayer playerInventory, TileEntityDirtChest tileEntity ) {
		this.tileEntity = tileEntity;
	}

	@Override
	public boolean canInteractWith( EntityPlayer player ) {
		return tileEntity.isUseableByPlayer( player );
	}

}
