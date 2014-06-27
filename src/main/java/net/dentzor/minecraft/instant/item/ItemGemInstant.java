package net.dentzor.minecraft.instant.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGemInstant extends Item {

    public IIcon[] iconArray;
    public String[] texArray, nameArray;
    public String texLoc;

    public ItemGemInstant(String texLocation, String[] textureNameArray, String[] displayNameArray) {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabMaterials);
        this.texLoc = texLocation;
        this.texArray = textureNameArray;
        this.nameArray = displayNameArray;
    }

    @Override
    @SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
    public void registerIcons(IIconRegister IR){
        this.itemIcon = IR.registerIcon(texLoc+this.texArray[0]);
        this.iconArray = new IIcon[texArray.length];
        for(int i = 0; i < texArray.length; i++){
            this.iconArray[i] = IR.registerIcon(texLoc+this.texArray[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i){
        if(i <= iconArray.length){
            return this.iconArray[i];
        }else{
            return super.getIconFromDamage(i);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack is){
        int i = is.getItemDamage();
        if(i <= nameArray.length){
            return nameArray[i];
        }else{
            return super.getItemStackDisplayName(is);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList){
        for(int i=0;i<nameArray.length;i++){
            itemList.add(new ItemStack(item, 1, i));
        }
    }
}