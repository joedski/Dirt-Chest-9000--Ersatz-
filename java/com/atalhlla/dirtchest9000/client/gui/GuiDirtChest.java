package com.atalhlla.dirtchest9000.client.gui;

import org.lwjgl.opengl.GL11;

import com.atalhlla.dirtchest9000.DirtChest9000Mod;
import com.atalhlla.dirtchest9000.inventory.ContainerDirtChest;
import com.atalhlla.dirtchest9000.tileentity.TileEntityDirtChest;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiDirtChest extends GuiContainer {
	
	public static final ResourceLocation texture = new ResourceLocation( DirtChest9000Mod.MODID, "textures/gui/dirt_chest.png" );

	public GuiDirtChest( InventoryPlayer playerInventory, TileEntityDirtChest dirtChestEntity ) {
		super( new ContainerDirtChest( playerInventory, dirtChestEntity ) );
		
		xSize = 176;
		ySize = 133;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float var1, int var2, int var3 ) {
		GL11.glColor4f( 1F, 1F, 1F, 1F );
		this.mc.getTextureManager().bindTexture( texture );
		
		int x = (this.width - this.xSize) / 2;
		int y = (this.width - this.ySize) / 2;
		
		this.drawTexturedModalRect( x, y, 0, 0, this.xSize, this.ySize );
	}

}
