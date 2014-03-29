package com.atalhlla.dirtchest9000.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDirtChest extends TileEntity implements IInventory {
	private ItemStack[] inventory;

	public TileEntityDirtChest() {
		inventory = new ItemStack[ 1 ];
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot( int slotIndex ) {
		return inventory[ slotIndex ];
	}

	@Override
	public ItemStack decrStackSize( int slotIndex, int count ) {
		ItemStack itemStack = getStackInSlot( slotIndex );

		if( itemStack != null ) {
			if( count >= itemStack.stackSize ) {
				setInventorySlotContents( slotIndex, null );
			}
			else {
				itemStack = itemStack.splitStack( count );
				// onInventoryChange();
			}

			this.markDirty();
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int slotIndex ) {
		ItemStack itemStack = getStackInSlot( slotIndex );
		setInventorySlotContents( slotIndex, null );
		return itemStack;
	}

	@Override
	public void setInventorySlotContents( int slotIndex, ItemStack itemStack ) {
		// Is this fine to do before checking what's already there?
		inventory[ slotIndex ] = itemStack;

		if( itemStack != null && itemStack.stackSize > getInventoryStackLimit() ) {
			itemStack.stackSize = getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Dirt Chest 9000!";
	}

	// TODO: Dirt Chest tile entity localization...?
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player ) {
		// must be within 8 blocks.
		return player.getDistanceSq( xCoord, yCoord, zCoord ) <= 64;
	}

	// This implementation intentionally left blank.
	@Override
	public void openInventory() {}

	// This implementation intentionally left blank.
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot( int var1, ItemStack var2 ) {
		return true;
	}

}
