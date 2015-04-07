package IndustrialBreakout.Machines.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import IndustrialBreakout.items.ItemsIB;

public class EnergyExtractorRecipes
{
    private static final EnergyExtractorRecipes extractingBase = new EnergyExtractorRecipes();

    /** The list of extracting results. */
    private HashMap<Item, ItemStack> extractingList = new HashMap<Item, ItemStack>();
    private HashMap<List<Integer>, ItemStack> metaExtractingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addExtracting and getExtractingResult.
     */
    public static final EnergyExtractorRecipes extracting()
    {
        return extractingBase;
    }

    private EnergyExtractorRecipes()
    {
    	addExtracting(new ItemStack(ItemsIB.chargedCoal), new ItemStack(Items.coal));
    	addExtracting(new ItemStack(Items.redstone), new ItemStack(ItemsIB.dullDust));
    	addExtracting(new ItemStack(Items.glowstone_dust), new ItemStack(ItemsIB.dullDust));
    }

    /**
     * Adds a extracting recipe.
     */
    public void addExtracting(ItemStack par1, ItemStack par2ItemStack)
    {
        this.extractingList.put(par1.getItem(), par2ItemStack);
    }

    /**
     * Returns the extracting result of an item.
     * Deprecated in favor of a metadata sensitive version
     */
    @Deprecated
    public ItemStack getExtractingResult(int par1)
    {
        return (ItemStack)this.extractingList.get(Integer.valueOf(par1));
    }

    public Map getExtractingList()
    {
        return this.extractingList;
    }
//    /**
//     * A metadata sensitive version of adding a furnace recipe.
//     */
    /*
    public void addExtracting(int itemID, int metadata, ItemStack itemstack, float experience)
    {
        metaExtractingList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemID, metadata), experience);
    }
    */
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getExtractingResult(ItemStack item) 
    {
        if (item == null)
        {
            return null;
        }
        ItemStack ret = (ItemStack)metaExtractingList.get(Arrays.asList(Item.getIdFromItem(item.getItem()), item.getItemDamage()));
        if (ret != null) 
        {
            return ret;
        }
        
        return extractingList.get(item.getItem());
    }
}
