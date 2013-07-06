// Coded by Flann

package mods.instant.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

public class Flann_ItemCombTool extends ItemTool {

	public String t, name;
	public boolean remBed;
	/** an array of the blocks this spade is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator};
	
	public Flann_ItemCombTool(int par1, String displayName, String tex, boolean delBed, EnumToolMaterial enumtoolmaterial) {
		super(par1, 0, enumtoolmaterial, blocksEffectiveAgainst);
		setCreativeTab(CreativeTabs.tabTools);
		t = tex;
		this.remBed = delBed;
		name = displayName;
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		return name;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(t);
	}
	
	/**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
	@Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return 5000.0F;
    }
    
    /**
     * Returns the damage against a given entity.
     */
	@Override
	public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
    {
        return 1;
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
