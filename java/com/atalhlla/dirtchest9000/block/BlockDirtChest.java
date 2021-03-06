package com.atalhlla.dirtchest9000.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.atalhlla.dirtchest9000.DirtChest9000Mod;
import com.atalhlla.dirtchest9000.tileentity.TileEntityDirtChest;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDirtChest extends BlockContainer {
	public static final String NAME = "dirt_chest_9000";

	public static IIcon iconTop;
	public static IIcon iconSides;
	public static IIcon iconFront;

	public BlockDirtChest() {
		super( Material.iron );

		setCreativeTab( CreativeTabs.tabRedstone );
		setHardness( 5F );
		setResistance( 10F );
	}

	// I don't actually know if the second param is meta.
	@Override
	public TileEntity createNewTileEntity( World world, int meta ) {
		return new TileEntityDirtChest();
	}

	@Override
	@SideOnly( Side.CLIENT )
	public void registerBlockIcons( IIconRegister iconRegister ) {
		iconTop = iconRegister.registerIcon( DirtChest9000Mod.MODID + ":" + NAME + "_top" );
		iconSides = iconRegister.registerIcon( DirtChest9000Mod.MODID + ":" + NAME + "_sides" );
		iconFront = iconRegister.registerIcon( DirtChest9000Mod.MODID + ":" + NAME + "_front" );
	}

	@Override
	public IIcon getIcon( int side, int meta ) {
		if( side == 0 || side == 1 ) {
			return iconTop;
		}
		else if( side == meta ) {
			return iconFront;
		}
		else {
			return iconSides;
		}
	}

	@Override
	public void onBlockPlacedBy( World world, int x, int y, int z, EntityLivingBase placer, ItemStack itemStackUsed ) {
		if( world.isRemote )
			return;

		int side = 0;
		int placerFacing = MathHelper.floor_double( (double) (placer.rotationYaw / 360F * 4F) + 0.5D ) & 3;

		// side 2 is north, so 0-facing is facing south.
		// I would suppose then that 1-facing is facing east, and so on.
		if( placerFacing == 0 )
			side = 2;
		if( placerFacing == 1 )
			side = 5;
		if( placerFacing == 2 )
			side = 3;
		if( placerFacing == 3 )
			side = 4;

		world.setBlockMetadataWithNotify( x, y, z, side, 2 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Set a default value so blocks are never just blank unfaced boxes.
	 * 
	 * @see
	 * net.minecraft.block.BlockContainer#onBlockAdded(net.minecraft.world.World
	 * , int, int, int)
	 */
	@Override
	public void onBlockAdded( World world, int x, int y, int z ) {
		// TODO Auto-generated method stub
		super.onBlockAdded( world, x, y, z );
		world.setBlockMetadataWithNotify( x, y, z, 2, 2 );
	}

	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ ) {
		if( world.isRemote )
			return true;

		FMLNetworkHandler.openGui( player, DirtChest9000Mod.instance, 0, world, x, y, z );
		
		return true;
	}
}
