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
@Mod(modid="InstantMining",name="Instant Mining Mod",version="#6")
public class CoreInstantTool {
	public static final String texLoc = "instant:";
	
	public static int ItemID = 14020; // 4 items
	
	public static boolean enableCoreSteel			= false;
	public static boolean enableCoreStickSteel		= false;
	public static boolean enableCoreIngotRedstone	= false;
	public static boolean commonConfig;
	
	public static int mineID, delID, combID, gemID;
	public static boolean delBedrock, combBedrock;
	
	public static EnumToolMaterial MINING = EnumHelper.addToolMaterial("MINING", 3, 0, 5000F, 1, 0);
	public static EnumToolMaterial COMBI = EnumHelper.addToolMaterial("COMBI", 3, 0, 5000F, 1, 0);
	
	public static Item mineTool, deleteTool, combiTool, gemInstant;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		String categoryBlock, categoryItem, categoryGeneral, categoryEnable, categoryGeneralCore;
		
		Configuration coreConfig = new Configuration(new File(event.getSuggestedConfigurationFile().getPath().replace("InstantMining", "FlannCore")));
		coreConfig.load();
			commonConfig = coreConfig.get("core", "combineConfigs", false).getBoolean(false);
			if(commonConfig){
				String modname = "instant";
				categoryBlock = modname+"_Block";
				categoryItem = modname+"_Item";
				categoryGeneral = modname+"_General";
				categoryEnable = modname+"_Enable";
				categoryGeneralCore = "core_General";
			}else{
				categoryBlock = "block";
				categoryItem = "item";
				categoryGeneral = "general";
				categoryEnable = "enable";
				categoryGeneralCore = "general";
			}
			if(enableCoreSteel)
				set(coreConfig, categoryGeneralCore, "enableSteel", true);
			if(enableCoreStickSteel)
				set(coreConfig, categoryGeneralCore, "enableStickSteel", true);
			if(enableCoreIngotRedstone)
				set(coreConfig, categoryGeneralCore, "enableIngotRedstone", true);
		coreConfig.save();
		
		Configuration config;
		if(commonConfig == false){
			config = new Configuration(event.getSuggestedConfigurationFile());
		}else{
			config = coreConfig;
		}
		
		config.load();
			mineID = config.get(categoryItem, "mineToolID", ItemID+0).getInt()-256;
			delID = config.get(categoryItem, "deleteToolID", ItemID+1).getInt()-256;
			combID = config.get(categoryItem, "combiToolID", ItemID+2).getInt()-256;
			gemID = config.get(categoryItem, "instantGems", ItemID+3, "The gems used to craft the tools").getInt()-256;
			
			delBedrock = config.get(categoryGeneral, "Enable Del-Tool To Remove Bedrock", false).getBoolean(false);
			combBedrock = config.get(categoryGeneral, "Enable Combi-Tool To Remove Bedrock", false).getBoolean(false);
		config.save();
		
		init_pre();
	}
	
	@Init
	public void load(FMLInitializationEvent event){
		init();
	}
	
	public void init_pre(){
		mineTool = new Flann_ItemMineTool(mineID, "Mining Tool", texLoc+"mineTool", MINING).setUnlocalizedName("mineTool");
		//LanguageRegistry.addName(mineTool, "Mining Tool");
		
		deleteTool = new Flann_ItemDelTool(delID, "Delete Tool", texLoc+"delTool", delBedrock).setUnlocalizedName("delTool");
		//LanguageRegistry.addName(deleteTool, "Delete Tool");
		
		combiTool = new Flann_ItemCombTool(combID, "Combi Tool", texLoc+"combTool", combBedrock, COMBI).setUnlocalizedName("combTool");
		//LanguageRegistry.addName(combiTool, "Combi Tool");
		
		gemInstant = new Flann_ItemGemInstant(gemID, texLoc, new String[] {"gemUniversal", "gemMining", "gemDelete", "gemCombi"}, new String[] {"Instant-Tool Gem", "Instant-Mining Gem", "Instant-Deletion Gem", "Instant-Combi Gem"}).setUnlocalizedName("gemInstant");
	}
	public void init(){
		MinecraftForge.setToolClass(mineTool, "pickaxe", 3);
		OreDictionary.registerOre("instantMining", mineTool);
		GameRegistry.addRecipe(new ItemStack(mineTool), "  d"," s ","s  ", 'd', new ItemStack(gemInstant, 1, 1), 's', Item.stick);
		
		// Not a tool-class
		OreDictionary.registerOre("instantDelete", deleteTool);
		GameRegistry.addRecipe(new ItemStack(deleteTool), "  d"," s ","s  ", 'd', new ItemStack(gemInstant, 1, 2), 's', Item.stick);
		
		MinecraftForge.setToolClass(combiTool, "pickaxe", 3);
		OreDictionary.registerOre("instantCombi", combiTool);
		GameRegistry.addRecipe(new ItemStack(combiTool), "  d"," s ","s  ", 'd', new ItemStack(gemInstant, 1, 3), 's', Item.stick);
		
		
		OreDictionary.registerOre("gemInstantUniversal", new ItemStack(gemInstant, 1, 0));
		GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 0), "$#$", "&@&", "$#$", '$', Block.obsidian, '#', Block.blockDiamond, '&', Block.blockEmerald, '@', Item.netherStar);
		GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 0), "$&$", "#@#", "$&$", '$', Block.obsidian, '#', Block.blockDiamond, '&', Block.blockEmerald, '@', Item.netherStar);
		
		OreDictionary.registerOre("gemInstantMining", new ItemStack(gemInstant, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(gemInstant, 1, 1), new ItemStack(gemInstant, 1, 0), new ItemStack(Item.dyePowder, 1, 1));
		
		OreDictionary.registerOre("gemInstantDelete", new ItemStack(gemInstant, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(gemInstant, 1, 2), new ItemStack(gemInstant, 1, 0), new ItemStack(Item.dyePowder, 1, 15));
		
		OreDictionary.registerOre("gemInstantCombi", new ItemStack(gemInstant, 1, 3));
		GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 3), "###", "M@D", "$$$", '#', Item.diamond, 'M', mineTool, '@', Item.netherStar, 'D', deleteTool, '$', Item.emerald);
		GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 3), "###", "M@D", "$$$", '#', Block.blockDiamond, 'M', mineTool, '@', Item.netherStar, 'D', deleteTool, '$', Block.blockEmerald);
		
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
