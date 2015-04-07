package IndustrialBreakout.Machines.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import IndustrialBreakout.Main;
import IndustrialBreakout.blocks.BlocksIB;

public class CompressorRecipes
{
    private static final CompressorRecipes compressingBase = new CompressorRecipes();

    /** The list of compressing results. */
    private HashMap<Item, ItemStack> compressingList = new HashMap<Item, ItemStack>();
    private HashMap<List<Integer>, ItemStack> metaCompressingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addCompressing and getCompressingResult.
     */
    public static final CompressorRecipes compressing()
    {
        return compressingBase;
    }

    private CompressorRecipes()
    {
    	addCompressing(new ItemStack(Blocks.cobblestone), new ItemStack(BlocksIB.compressedCobble));
    	addCompressing(new ItemStack(Blocks.stone), new ItemStack(BlocksIB.compressedStone));
    }

    /**
     * Adds a compressing recipe.
     */
    public void addCompressing(ItemStack par1, ItemStack par2ItemStack)
    {
        this.compressingList.put(par1.getItem(), par2ItemStack);
    }

    public Map getCompressingList()
    {
        return this.compressingList;
    }
//    /**
//     * A metadata sensitive version of adding a furnace recipe.
//     */
    /*
    public void addCompressing(int itemID, int metadata, ItemStack itemstack, float experience)
    {
        metaCompressingList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemID, metadata), experience);
    }
    */
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getCompressingResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaCompressingList.get(Arrays.asList(Item.getIdFromItem(item.getItem()), item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        return compressingList.get(item.getItem());
    }
}
