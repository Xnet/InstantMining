package net.dentzor.minecraft.instant.item;

import java.util.Set;

import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemMineTool extends ItemTool {

    public String t, name;

    /** an array of the blocks this tool is effective against */
    private static final Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] { Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow,
            Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium, Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone,
            Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block,
            Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail,
            Blocks.golden_rail, Blocks.activator_rail, Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin });

    public ItemMineTool(String displayName, String tex, Item.ToolMaterial toolmaterial) {
        super(0, toolmaterial, blocksEffectiveAgainst);
        this.maxStackSize = 1;
        setCreativeTab(CreativeTabs.tabTools);
        setHarvestLevel("pickaxe", 5);
        setHarvestLevel("shovel", 5);
        setHarvestLevel("axe", 5);
        t = tex;
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
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    @Override
    public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
    {
        return 5000.0F;
    }
    
    /**
     * Returns the damage against a given entity.
     *//*
    @Override
    public float getDamageVsEntity(Entity par1Entity, ItemStack itemStack)
    {
        return 1;
    }
    /**/

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    @Override
    public boolean func_150897_b(Block par1Block)
    {
        return true;
    }
}