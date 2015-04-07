package IndustrialBreakout.Machines.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import IndustrialBreakout.Main;
import IndustrialBreakout.items.ItemsIB;

public class GrinderRecipes
{
    private static final GrinderRecipes grindingBase = new GrinderRecipes();

    /** The list of smelting results. */
    private HashMap<Item, ItemStack> grindingList = new HashMap<Item, ItemStack>();
    private HashMap<List<Integer>, ItemStack> metaGrindingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addGrinding and getGrindingResult.
     */
    public static final GrinderRecipes grinding()
    {
        return grindingBase;
    }

    private GrinderRecipes()
    {
    	addGrinding(new ItemStack(ItemsIB.chargedCoal), new ItemStack(Items.redstone, 2));
    }

    /**
     * Adds a smelting recipe.
     */
    public void addGrinding(ItemStack par1, ItemStack par2ItemStack)
    {
        this.grindingList.put(par1.getItem(), par2ItemStack);
    }

    /**
     * Returns the smelting result of an item.
     * Deprecated in favor of a metadata sensitive version
     */
    @Deprecated
    public ItemStack getGrindingResult(int par1)
    {
        return (ItemStack)this.grindingList.get(Integer.valueOf(par1));
    }

    public Map getGrindingList()
    {
        return this.grindingList;
    }
//    /**
//     * A metadata sensitive version of adding a furnace recipe.
//     */
    /*
    public void addGrinding(int itemID, int metadata, ItemStack itemstack, float experience)
    {
        metaGrindingList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemID, metadata), experience);
    }
    */
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getGrindingResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaGrindingList.get(Arrays.asList(Item.getIdFromItem(item.getItem()), item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        return grindingList.get(item.getItem());
    }
}
