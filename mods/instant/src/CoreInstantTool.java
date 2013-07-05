// Coded by Flann

package mods.instant.src;

import static net.minecraftforge.common.Property.Type.BOOLEAN;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@NetworkMod(clientSideRequired=true,serverSideRequired=false)
@Mod(modid="InstantMining",name="Instant Mining Mod",version="#3")
public class CoreInstantTool {
	public static final String texLoc = "instant:";
	
	public static int ItemID = 14020; // 3 items
	
	public static boolean enableCoreSteel			= false;
	public static boolean enableCoreStickSteel		= false;
	public static boolean enableCoreIngotRedstone	= false;
	
	public static int mineID, delID, combID;
	public static boolean delBedrock, combBedrock;
	
	public static EnumToolMaterial MINING = EnumHelper.addToolMaterial("MINING", 3, 0, 5000F, 1, 0);
	public static EnumToolMaterial COMBI = EnumHelper.addToolMaterial("COMBI", 3, 0, 5000F, 1, 0);
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
			mineID = config.get("item", "mineToolID", ItemID+0).getInt()-256;
			delID = config.get("item", "deleteToolID", ItemID+1).getInt()-256;
			combID = config.get("item", "combiToolID", ItemID+2).getInt()-256;
			
			delBedrock = config.get("general", "Enable Del-Tool To Remove Bedrock", false).getBoolean(false);
			combBedrock = config.get("general", "Enable Combi-Tool To Remove Bedrock", false).getBoolean(false);
		config.save();
		
		Configuration coreConfig = new Configuration(new File(event.getSuggestedConfigurationFile().getPath().replace("InstantMining", "FlannCore")));
		coreConfig.load();
			if(enableCoreSteel)
				set(coreConfig, "general", "enableSteel", true);
			if(enableCoreStickSteel)
				set(coreConfig, "general", "enableStickSteel", true);
			if(enableCoreIngotRedstone)
				set(coreConfig, "general", "enableIngotRedstone", true);
		coreConfig.save();
	}
	
	public static Item mineTool, deleteTool, combiTool;
	
	@Init
	public void load(FMLInitializationEvent event){
		// Initialise
		// setToolClass
		// addName
		// addRecipe
		
		mineTool = new Flann_ItemMineTool(mineID, "Mining Tool", texLoc+"mineTool", MINING).setUnlocalizedName("mineTool");
		MinecraftForge.setToolClass(mineTool, "pickaxe", 3);
		//LanguageRegistry.addName(mineTool, "Mining Tool");
		OreDictionary.registerOre("instantMining", mineTool);
		GameRegistry.addRecipe(new ItemStack(mineTool), "  d"," s ","s  ", Character.valueOf('d'), Item.diamond, Character.valueOf('s'), Item.stick);
		
		deleteTool = new Flann_ItemDelTool(delID, "Delete Tool", texLoc+"delTool", delBedrock).setUnlocalizedName("delTool");
		// setToolClass do not have any function here since this doesn't work as a pickaxe/axe/shovel... 
		//LanguageRegistry.addName(deleteTool, "Delete Tool");
		OreDictionary.registerOre("instantDelete", deleteTool);
		// No recipe since this is a admin tool, atleast for now
		
		combiTool = new Flann_ItemCombTool(combID, "Combi Tool", texLoc+"combTool", combBedrock, COMBI).setUnlocalizedName("combTool");
		MinecraftForge.setToolClass(combiTool, "pickaxe", 3);
		//LanguageRegistry.addName(combiTool, "Combi Tool");
		OreDictionary.registerOre("instantCombi", combiTool);
		// No recipe since this is a admin tool, atleast for now
	}
	
	public Property set(Configuration config, String category, String key, boolean defaultValue)
    {
		return set(config, category, key, defaultValue, null);
    }
	public Property set(Configuration config, String category, String key, boolean defaultValue, String comment)
    {
        Property prop = config.get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        prop.set(defaultValue);
        return prop;
    }
}
