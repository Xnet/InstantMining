// Coded by Flann

package mods.instant.src;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class Flann_ItemGemInstant extends Item {
	
	public Icon[] iconArray;
	public String[] texArray, nameArray;
	public String texLoc;
	
	public Flann_ItemGemInstant(int id, String texLocation, String[] textureNameArray, String[] displayNameArray) {
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabMisc);
		this.texLoc = texLocation;
		this.texArray = textureNameArray;
		this.nameArray = displayNameArray;
	}
	
	@Override
	@SideOnly(Side.CLIENT) //Makes sure that only the client side can call this method
	public void registerIcons(IconRegister IR){
		this.itemIcon = IR.registerIcon(texLoc+this.texArray[0]);
		this.iconArray = new Icon[texArray.length];
		for(int i = 0; i < texArray.length; i++){
			this.iconArray[i] = IR.registerIcon(texLoc+this.texArray[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i){
		if(i <= iconArray.length){
			return this.iconArray[i];
		}else{
			return super.getIconFromDamage(i);
		}
	}
	
	@Override
	public String getItemDisplayName(ItemStack is){
		int i = is.getItemDamage();
		if(i <= nameArray.length){
			return nameArray[i];
		}else{
			return super.getItemDisplayName(is);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tab,List itemList){
		for(int i=0;i<nameArray.length;i++){
			itemList.add(new ItemStack(itemID,1,i));
		}
	}
}
