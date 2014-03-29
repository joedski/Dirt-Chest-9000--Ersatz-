package com.atalhlla.dirtchest9000.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.atalhlla.dirtchest9000.tileentity.TileEntityDirtChest;

public class ContainerDirtChest extends Container {

	private TileEntityDirtChest tileEntity;

	public ContainerDirtChest( InventoryPlayer playerInventory, TileEntityDirtChest tileEntity ) {
		this.tileEntity = tileEntity;

		// Player's hot bar
		for( int x = 0; x < 9; ++x ) {
			this.addSlotToContainer( new Slot(
				playerInventory,
				x,
				8 + x * 18,
				203 ) );
		}

		// Player's inventory
		for( int y = 0; y < 3; ++y ) {
			for( int x = 0; x < 9; ++x ) {
				this.addSlotToContainer( new Slot(
					playerInventory,
					9 + x + y * 9,
					8 + x * 18,
					145 + y * 18 ) );
			}
		}

		// Dirt Chest 9000
		this.addSlotToContainer( new SlotDirtChest( tileEntity, 0, 80, 58 ) );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * You know, you could make a tileentity which only lets someone interact
	 * with it if they're a minimum distance away...
	 * 
	 * @see
	 * net.minecraft.inventory.Container#canInteractWith(net.minecraft.entity
	 * .player.EntityPlayer)
	 */
	@Override
	public boolean canInteractWith( EntityPlayer player ) {
		return tileEntity.isUseableByPlayer( player );
	}

	/**
	 * Tries a transfer of an item stack out of a specified slot by the
	 * specified player. Deals with all inventory slots on this container
	 * screen, including the target container's and the player's.
	 * 
	 * @param player
	 *            The player interacting with this Container.
	 * @param slotIndex
	 *            the index of the inventory slot being transferred from.
	 * @return The ItemStack being transferred, or null if such a transfer is
	 *         not possible.
	 */
	@Override
	public ItemStack transferStackInSlot( EntityPlayer player, int slotIndex ) {

		// Begin by just getting the slot
		Slot slot = getSlot( slotIndex );

		// Obviously, if we have no slot, or the slot has no stack, there's
		// nothing to return, so return null.
		if( slot == null || !slot.getHasStack() )
			return null;

		// If we pass that first condition, then we (might) have something, so
		// grab it.
		// I'm not sure why we make a copy right off or what value doing that
		// has right now. I shall have to meditate on that further.
		ItemStack itemStack = slot.getStack();
		ItemStack result = itemStack.copy();

		// 36 is the size of the Player's inventory (bag + hot bar)
		// So, any slot indices 36 or greater refer to the Container's inventory
		// rather than the player's.

		// So, if the slot index is outside of the player's inventory range,
		// then try to merge the ItemStack into the player's inventory.
		if( slotIndex >= 36 ) {
			// And if that fails, return null.
			if( !mergeItemStack( itemStack, 0, 36, false ) ) {
				return null;
			}
		}
		// Otherwise the slot index is within the player's inventory range, and
		// so we're trying to move something /into/ the Container's inventory.
		else if( !mergeItemStack( itemStack, 36, 36 + tileEntity.getSizeInventory(), false ) ) {
			// And if that fails, return null.
			return null;
		}

		// If we get to this point, we can assume the stack was successfully
		// merged into whatever inventory it went to. So we now check if the
		// items remaining in the itemStack are 0.
		if( itemStack.stackSize == 0 ) {
			// And if so, clear out the slot they came from because it's now
			// empty.
			slot.putStack( null );
		}
		else {
			// Otherwise, we still have items left and need to update all the
			// things.
			slot.onSlotChanged();
		}

		// Then we let the slot perform any actions it needs to when the player
		// picks things up from it.
		slot.onPickupFromSlot( player, itemStack );

		// And return the resulting items.
		return result;
	}

	@Override
	protected boolean mergeItemStack( ItemStack incomingItemStack, int rangeStart, int rangeEnd, boolean startAtEnd ) {
		boolean flag1 = false;
		int ci = rangeStart;

		if( startAtEnd ) {
			ci = rangeEnd - 1;
		}

		Slot slot;
		ItemStack slotItemStack;

		// First, try to distribute our incoming ItemStack to any slots that
		// contain the same type of item.
		if( incomingItemStack.isStackable() ) {
			while( incomingItemStack.stackSize > 0 && (!startAtEnd && ci < rangeEnd || startAtEnd && ci >= rangeStart) ) {
				slot = (Slot) this.inventorySlots.get( ci );
				slotItemStack = slot.getStack();

				if( slotItemStack != null && slotItemStack.getItem() == incomingItemStack.getItem() && (!incomingItemStack.getHasSubtypes() || incomingItemStack.getItemDamage() == slotItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual( incomingItemStack, slotItemStack ) ) {
					int l = slotItemStack.stackSize + incomingItemStack.stackSize;

					if( l <= incomingItemStack.getMaxStackSize() ) {
						incomingItemStack.stackSize = 0;
						slotItemStack.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					}
					else if( slotItemStack.stackSize < incomingItemStack.getMaxStackSize() ) {
						incomingItemStack.stackSize -= incomingItemStack.getMaxStackSize() - slotItemStack.stackSize;
						slotItemStack.stackSize = incomingItemStack.getMaxStackSize();
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if( startAtEnd ) {
					--ci;
				}
				else {
					++ci;
				}
			}
		}

		// If we still have items left, or the item wasn't stackable, try to put
		// it anywhere.
		if( incomingItemStack.stackSize > 0 ) {
			if( startAtEnd ) {
				ci = rangeEnd - 1;
			}
			else {
				ci = rangeStart;
			}

			while( !startAtEnd && ci < rangeEnd || startAtEnd && ci >= rangeStart ) {
				slot = (Slot) this.inventorySlots.get( ci );
				slotItemStack = slot.getStack();

				// Here's the only change I made to this function:
				// We only check if the item is valid here because we're
				// assuming that in the previous section, if the slot /already/
				// contained that type of item, the item is valid.
				if( slotItemStack == null && slot.isItemValid( incomingItemStack ) ) {
					slot.putStack( incomingItemStack.copy() );
					slot.onSlotChanged();
					incomingItemStack.stackSize = 0;
					flag1 = true;
					break;
				}

				if( startAtEnd ) {
					--ci;
				}
				else {
					++ci;
				}
			}
		}

		return flag1;
	}
}
