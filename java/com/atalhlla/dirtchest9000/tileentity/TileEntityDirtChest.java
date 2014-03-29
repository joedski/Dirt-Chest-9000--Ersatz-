package com.atalhlla.dirtchest9000.tileentity;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
	public boolean isItemValidForSlot( int slotIndex, ItemStack itemStack ) {
		FMLLog.info( "Now checking if we're allowed to store an item in slot %d... is it dirt?  %b", slotIndex, itemStack.getItem() == Item.getItemFromBlock( Blocks.dirt ) );
		return itemStack.getItem() == Item.getItemFromBlock( Blocks.dirt );
	}

	@Override
	public void writeToNBT( NBTTagCompound nbtTag ) {
		super.writeToNBT( nbtTag );

		NBTTagList list = new NBTTagList();

		for( int i = 0; i < getSizeInventory(); ++i ) {
			ItemStack itemStack = getStackInSlot( i );

			if( itemStack == null )
				continue;

			NBTTagCompound item = new NBTTagCompound();
			item.setByte( "Dirt Chest Slot", (byte) i );
			itemStack.writeToNBT( item );

			// Note, NBTTagLists must contain items of all the same type (like
			// vectors). Trying to write tags with different types to the same
			// list will result in an error message and failure to write the
			// second tag.
			list.appendTag( item );
		}

		nbtTag.setTag( "Dirt Chest Items", list );
	}

	@Override
	public void readFromNBT( NBTTagCompound nbtTag ) {
		super.readFromNBT( nbtTag );

		byte compoundTagType = (new NBTTagCompound()).getId();

		// 1.7 added an tag type for lists. If you specify the wrong tag type,
		// you get an empty (new) list.
		NBTTagList list = nbtTag.getTagList( "Dirt Chest Items", compoundTagType );

		for( int i = 0, c = list.tagCount(); i < c; ++i ) {
			NBTTagCompound item = list.getCompoundTagAt( i );
			int slotIndex = item.getByte( "Dirt Chest Slot" );

			if( slotIndex >= 0 && slotIndex < getSizeInventory() ) {
				setInventorySlotContents( slotIndex, ItemStack.loadItemStackFromNBT( item ) );
			}
		}
	}
}
