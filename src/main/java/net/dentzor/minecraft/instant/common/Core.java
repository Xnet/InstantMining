package net.dentzor.minecraft.instant.common;

import static net.minecraftforge.common.config.Property.Type.BOOLEAN;

import java.io.File;

import net.dentzor.minecraft.instant.item.ItemCombTool;
import net.dentzor.minecraft.instant.item.ItemDelTool;
import net.dentzor.minecraft.instant.item.ItemGemInstant;
import net.dentzor.minecraft.instant.item.ItemMineTool;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = net.dentzor.minecraft.instant.common.Core.modid, name = net.dentzor.minecraft.instant.common.Core.modname, version = net.dentzor.minecraft.instant.common.Core.modversion)
public class Core {
    @SidedProxy(clientSide = "net.dentzor.minecraft.instant.common.ClientProxy", serverSide = "net.dentzor.minecraft.instant.common.CommonProxy")
    public static CommonProxy  proxy;

    // Set mod-specific names etc.
    public static final String modid           = "instant";
    public static final String modname         = "Instant Mining Mod";
    public static final String modversion      = "#7";
    public static final String textureLocation = Core.modid + ":";

    public static boolean enableCoreSteel           = false;
    public static boolean enableCoreStickSteel      = false;
    public static boolean enableCoreIngotRedstone   = false;
    public static boolean commonConfig;

    public static boolean delBedrock, combBedrock;

    public static Item.ToolMaterial MINING, COMBI;
    
    // Create all items in the instant tool mod (NOT initialize)
    public static Item mineTool, deleteTool, combiTool, gemInstant;

    // Booleans to enable/disable stuff
    public static boolean enableMineTool, enableDeleteTool, enableCombiTool, enableMineRecipe, enableDelRecipe, enableCombRecipe, enableGemRecipe;
    
    // Integers for Tools durability
    public static int toolMiningDurability, toolCombiDurability;
    
    
 // Constructor
    public Core() {

    }

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        Core.loadConfig(event);
        Core.init_materials();
        Core.init_items();
        Core.registerItems();
    }

    @EventHandler
    public static void atInit(FMLInitializationEvent event) {
        Core.registerOreDictionary();
        Core.init_crafting();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }

    // Register all items in forge GameRegistry
    public static void registerItems() {
        if(Core.enableMineTool){
            GameRegistry.registerItem(mineTool, "toolMining");
        }
        if(Core.enableDeleteTool){
            GameRegistry.registerItem(deleteTool, "toolDelete");
        }
        if(Core.enableCombiTool){
            GameRegistry.registerItem(combiTool, "toolCombi");
        }
        if(Core.enableMineTool || Core.enableDeleteTool || Core.enableCombiTool){
            GameRegistry.registerItem(gemInstant, "gemInstant");
        }
    }

    // Initialize materials
    public static void init_materials(){
        MINING = EnumHelper.addToolMaterial("MINING", 12, toolMiningDurability , 5000F, 0, 0);
        COMBI = EnumHelper.addToolMaterial("COMBI", 12, toolCombiDurability, 5000F, 0, 0);
    }
    
    // Load the config-values
    public static void loadConfig(FMLPreInitializationEvent event) {
        String categoryEnable, categoryDurability, categoryGeneralCore;

        Configuration coreConfig = new Configuration(new File(event.getSuggestedConfigurationFile().getPath().replace(Core.modid, "FlannCore")));
        coreConfig.load();
        Core.commonConfig = coreConfig.get("core", "combineConfigs", false).getBoolean(false);
        if (Core.commonConfig) {
            categoryEnable = modid + "_Enable";
            categoryDurability = modid + "_Durability";
            categoryGeneralCore = "core_General";
        } else {
            categoryEnable = "enable";
            categoryDurability = "durability";
            categoryGeneralCore = "general";
        }
        if (Core.enableCoreSteel) {
            Core.set(coreConfig, categoryGeneralCore, "enableSteel", true);
        }
        if (Core.enableCoreStickSteel) {
            Core.set(coreConfig, categoryGeneralCore, "enableStickSteel", true);
        }
        if (Core.enableCoreIngotRedstone) {
            Core.set(coreConfig, categoryGeneralCore, "enableIngotRedstone", true);
        }
        coreConfig.save();

        Configuration config;
        if (Core.commonConfig == false) {
            config = new Configuration(event.getSuggestedConfigurationFile());
        } else {
            config = coreConfig;
        }
        
        config.load();
            toolMiningDurability = config.get(categoryDurability, "MiningToolDurability", 0, "Durability for the Mining Tool. 0 = Infinite. Default: 0").getInt();
            toolCombiDurability = config.get(categoryDurability, "CombiToolDurability", 0, "Durability for the Combi Tool. 0 = Infinite. Default: 0").getInt();
        
            enableMineTool = config.get(categoryEnable, "enableMineTool", true).getBoolean(true);
            enableDeleteTool = config.get(categoryEnable, "enableDeleteTool", true).getBoolean(true);
            enableCombiTool = config.get(categoryEnable, "enableCombiTool", true).getBoolean(true);
            
            enableMineRecipe = config.get(categoryEnable, "enableMiningToolRecipe", true).getBoolean(true);
            enableDelRecipe = config.get(categoryEnable, "enableDeleteToolRecipe", true).getBoolean(true);
            enableCombRecipe = config.get(categoryEnable, "enableCombiToolRecipe", true).getBoolean(true);

            enableGemRecipe = config.get(categoryEnable, "enableGemRecipe", true, "Setting this to false will disable the making of gems, but not the tools, this can be useful for adventure/quest maps").getBoolean(true);
            
            delBedrock = config.get(categoryEnable, "Enable Del-Tool To Remove Bedrock", false).getBoolean(false);
            combBedrock = config.get(categoryEnable, "Enable Combi-Tool To Remove Bedrock", false).getBoolean(false);
        config.save();
    }
    
    // Initialize items
    public static void init_items(){
        mineTool = new ItemMineTool("Mining Tool", textureLocation+"mineTool", MINING).setUnlocalizedName("toolMining");
        //LanguageRegistry.addName(mineTool, "Mining Tool");

        deleteTool = new ItemDelTool("Delete Tool", textureLocation+"delTool", delBedrock).setUnlocalizedName("toolDelete");
        //LanguageRegistry.addName(deleteTool, "Delete Tool");

        combiTool = new ItemCombTool("Combi Tool", textureLocation+"combTool", combBedrock, COMBI).setUnlocalizedName("toolCombi");
        //LanguageRegistry.addName(combiTool, "Combi Tool");

        gemInstant = new ItemGemInstant(textureLocation, new String[] {"gemUniversal", "gemMining", "gemDelete", "gemCombi"}, new String[] {"Instant-Tool Gem", "Instant-Mining Gem", "Instant-Deletion Gem", "Instant-Combi Gem"}).setUnlocalizedName("gemInstant");
    }
    
    // Add to oreDictionary
    public static void registerOreDictionary(){
        OreDictionary.registerOre("instantMining", mineTool);
        OreDictionary.registerOre("instantDelete", deleteTool);
        OreDictionary.registerOre("instantCombi", combiTool);
        
        OreDictionary.registerOre("gemInstantUniversal", new ItemStack(gemInstant, 1, 0));
        OreDictionary.registerOre("gemInstantMining", new ItemStack(gemInstant, 1, 1));
        OreDictionary.registerOre("gemInstantDelete", new ItemStack(gemInstant, 1, 2));
        OreDictionary.registerOre("gemInstantCombi", new ItemStack(gemInstant, 1, 3));
        
    }
    
    // Initialize Crafting
    public static void init_crafting(){
        if(enableMineRecipe && !enableMineTool)
            GameRegistry.addRecipe(new ItemStack(mineTool), "  d"," s ","s  ", 'd', new ItemStack(gemInstant, 1, 1), 's', Items.stick);

        if(enableDelRecipe && !enableDeleteTool)
            GameRegistry.addRecipe(new ItemStack(deleteTool), "  d"," s ","s  ", 'd', new ItemStack(gemInstant, 1, 2), 's', Items.stick);

        if(enableCombRecipe && !enableCombiTool)
            GameRegistry.addRecipe(new ItemStack(combiTool), "  d"," s ","s  ", 'd', new ItemStack(gemInstant, 1, 3), 's', Items.stick);

        // Gems
        if((enableMineRecipe || enableDelRecipe || enableCombRecipe) && enableGemRecipe){
            GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 0), "$#$", "&@&", "$#$", '$', Blocks.obsidian, '#', Blocks.diamond_block, '&', Blocks.emerald_block, '@', Items.nether_star);
            GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 0), "$&$", "#@#", "$&$", '$', Blocks.obsidian, '#', Blocks.diamond_block, '&', Blocks.emerald_block, '@', Items.nether_star);
            
            GameRegistry.addShapelessRecipe(new ItemStack(gemInstant, 1, 1), new ItemStack(gemInstant, 1, 0), new ItemStack(Items.dye, 1, 1));
    
            GameRegistry.addShapelessRecipe(new ItemStack(gemInstant, 1, 2), new ItemStack(gemInstant, 1, 0), new ItemStack(Items.dye, 1, 15));
    
            GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 3), "###", "M@D", "$$$", '#', Items.diamond, 'M', mineTool, '@', Items.nether_star, 'D', deleteTool, '$', Items.emerald);
            GameRegistry.addRecipe(new ItemStack(gemInstant, 1, 3), "###", "M@D", "$$$", '#', Blocks.diamond_block, 'M', mineTool, '@', Items.nether_star, 'D', deleteTool, '$', Blocks.emerald_block);
        }

    }
    
    public static Property set(Configuration config, String category, String key, boolean defaultValue) {
        return Core.set(config, category, key, defaultValue, null);
    }

    public static Property set(Configuration config, String category, String key, boolean defaultValue, String comment) {
        Property prop = config.get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        prop.set(defaultValue);
        return prop;
    }
    
}
