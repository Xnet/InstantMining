package net.dentzor.minecraft.instant.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDelTool extends Item {

    public String t, name;
    public boolean remBed;

    public ItemDelTool(String displayName, String tex, boolean delBed) {
        super();
        this.maxStackSize = 1;
        setCreativeTab(CreativeTabs.tabTools);
        t = tex;
        this.remBed = delBed;
        name = displayName;
    }

    @Override
    public String getItemStackDisplayName(ItemStack is){
        return name;
    }

    @Override
    @SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
    public void registerIcons(IIconRegister IR){
        this.itemIcon = IR.registerIcon(t);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if(remBed){
            if(world.getBlock(par4, par5, par6) != Blocks.air){
                world.setBlockToAir(par4, par5, par6);
                return true;
            }
            else
                return false;
        }
        else{
            if(world.getBlock(par4, par5, par6) != Blocks.air && world.getBlock(par4, par5, par6) != Blocks.bedrock ){
                world.setBlockToAir(par4, par5, par6);
                return true;
            }
            else
                return false;
        }
    }
}