package IndustrialBreakout.TileEntity.bases;

import IndustrialBreakout.items.ItemsIB;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public  class TileEntityMachine extends TileEntity {
	public int maxEnergy = 2000, curEnergy = 0, progress = 0, tick = 0;
	
	public void updateEntity(){
		if(curEnergy>maxEnergy) curEnergy = maxEnergy;
	}
	
	public boolean hasEnergy(){
		return curEnergy>0;
	}
	
	public boolean canReceiveEnergy(){
		return curEnergy<maxEnergy;
	}
	
    /**
     * Returns the number of ticks that the supplied fuel item will keep the compressor burning, or 0 if the item isn't
     * fuel
     */
    public int getItemEnergy(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int var1 = Item.getIdFromItem(par0ItemStack.getItem());
            Item var2 = par0ItemStack.getItem();

            if (par0ItemStack.getItem() instanceof ItemBlock && Block.getBlockById(var1) != null)
            {
                Block var3 = Block.getBlockById(var1);
            }
            
            if (var1 == Item.getIdFromItem(ItemsIB.chargedCoal)) return 1000;
            return 0;
        }
    }


    /**
     * Return true if item is a fuel source (getItemEnergy() > 0).
     */
    public boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemEnergy(par0ItemStack) > 0;
    }
    
    public String toString(){
    	return "Current Energy: "+curEnergy+", Max Energy: "+maxEnergy;
    }
}
