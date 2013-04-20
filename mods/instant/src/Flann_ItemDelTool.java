// Coded by Flann

package mods.instant.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

public class Flann_ItemDelTool extends Item {

	public String tex;
	public boolean remBed;
	
	public Flann_ItemDelTool(int par1, String t, boolean delBed) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMaterials);
		tex = t;
		this.remBed = delBed;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void updateIcons(IconRegister IR){
		this.iconIndex = IR.registerIcon(InstantToolCore.modid + ":" + tex);
	}
	
	/**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
	@Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
		if(remBed){
			if(world.getBlockId(par4, par5, par6) != 0){
				world.setBlockToAir(par4, par5, par6);
				return true;
			}
			else
				return false;
		}
		else{
			if(world.getBlockId(par4, par5, par6) != 0 && world.getBlockId(par4, par5, par6) != Block.bedrock.blockID ){
				world.setBlockToAir(par4, par5, par6);
				return true;
			}
			else
				return false;
		}
    }
}
