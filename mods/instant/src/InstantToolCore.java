// Coded by Flann

package mods.instant.src;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired=true,serverSideRequired=false)
@Mod(modid=InstantToolCore.modid,name="Instant Mining Mod",version="#1")
public class InstantToolCore {
	public static final String modid = "instant";
	
	public static int BlockID = 1200;
	public static int ItemID = 14544;
	
	public static int mineID, delID, combID;
	public static boolean delBedrock, combBedrock;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		mineID = config.get("items", "mineToolID", ItemID+0).getInt();
		delID = config.get("items", "deleteToolID", ItemID+1).getInt();
		combID = config.get("items", "combiToolID", ItemID+2).getInt();
		
		delBedrock = config.get("general", "Enable Del-Tool To Remove Bedrock", false).getBoolean(false);
		combBedrock = config.get("general", "Enable Combi-Tool To Remove Bedrock", false).getBoolean(false);
		
		config.save();
	}
	
	public static Item mineTool, deleteTool, combiTool;
	
	@Init
	public void load(FMLInitializationEvent event){
		// Initialise
		// setToolClass
		// addName
		// addRecipe
		
		mineTool = new Flann_ItemMineTool(mineID, "mineTool").setUnlocalizedName("mineTool");
		MinecraftForge.setToolClass(mineTool, "pickaxe", 3);
		LanguageRegistry.addName(mineTool, "Mining Tool");
		GameRegistry.addRecipe(new ItemStack(mineTool), "  d"," s ","s  ", Character.valueOf('d'), Item.diamond, Character.valueOf('s'), Item.stick);
		
		deleteTool = new Flann_ItemDelTool(delID, "delTool", delBedrock).setUnlocalizedName("delTool");
		// setToolClass do not have any function here since this doesn't work as a pickaxe/axe/shovel... 
		LanguageRegistry.addName(deleteTool, "Delete Tool");
		// No recipe since this is a admin tool, atleast for now
		
		combiTool = new Flann_ItemCombTool(combID, "combTool", combBedrock).setUnlocalizedName("combTool");
		MinecraftForge.setToolClass(combiTool, "pickaxe", 3);
		LanguageRegistry.addName(combiTool, "Combi Tool");
		// No recipe since this is a admin tool, atleast for now
	}
}
