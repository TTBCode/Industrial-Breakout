package IndustrialBreakout.Machines.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import IndustrialBreakout.Main;
import IndustrialBreakout.items.ItemsIB;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class RefinerRecipes
{
    private static final RefinerRecipes refiningBase = new RefinerRecipes();

    /** The list of refining results. */
    private HashMap<Item, ItemStack> refiningList = new HashMap<Item, ItemStack>();
    private HashMap<List<Integer>, ItemStack> metaRefiningList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addRefining and getRefiningResult.
     */
    public static final RefinerRecipes refining()
    {
        return refiningBase;
    }

    private RefinerRecipes()
    {
    	addRefining(new ItemStack(ItemsIB.ingotTitanium), new ItemStack(ItemsIB.refinedTitanium));
    }

    /**
     * Adds a refining recipe.
     */
    public void addRefining(ItemStack par1, ItemStack par2ItemStack)
    {
        this.refiningList.put(par1.getItem(), par2ItemStack);
    }

    public Map getRefiningList()
    {
        return this.refiningList;
    }
//    /**
//     * A metadata sensitive version of adding a furnace recipe.
//     */
    /*
    public void addRefining(int itemID, int metadata, ItemStack itemstack, float experience)
    {
        metaRefiningList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemID, metadata), experience);
    }
    */
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getRefiningResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaRefiningList.get(Arrays.asList(Item.getIdFromItem(item.getItem()), item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        return refiningList.get(item.getItem());
    }
}
