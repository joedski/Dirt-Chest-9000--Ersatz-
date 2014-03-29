package com.atalhlla.dirtchest9000.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDirtChest extends Slot {
	public SlotDirtChest( IInventory targetInventory, int slotIndex, int x, int y ) {
		super( targetInventory, slotIndex, x, y );
	}
	
	@Override
	public boolean isItemValid( ItemStack itemStack ) {
		return this.inventory.isItemValidForSlot( this.slotNumber, itemStack );
	}
}
