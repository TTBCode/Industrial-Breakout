package IndustrialBreakout.TileEntity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import IndustrialBreakout.Machines.recipes.EnergyExtractorRecipes;
import IndustrialBreakout.TileEntity.bases.TileEntityGenerator;
import IndustrialBreakout.items.ItemsIB;
import IndustrialBreakout.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityEnergyExtractor extends TileEntityGenerator implements IInventory, ISidedInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the energyExtractor
     */
    private ItemStack[] energyExtractorItemStacks = new ItemStack[2];
    
    /** The amount of energy that can be produced from the given item */
    public int remainingEnergyToGenerate = 0;
    
    public ItemStack itemToGiveOnceFinished;
    
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the energyExtractor burning for
     */
    public int currentItemGenTime = 0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.energyExtractorItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.energyExtractorItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.energyExtractorItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.energyExtractorItemStacks[par1].stackSize <= par2)
            {
                var3 = this.energyExtractorItemStacks[par1];
                this.energyExtractorItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.energyExtractorItemStacks[par1].splitStack(par2);

                if (this.energyExtractorItemStacks[par1].stackSize == 0)
                {
                    this.energyExtractorItemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.energyExtractorItemStacks[par1] != null)
        {
            ItemStack var2 = this.energyExtractorItemStacks[par1];
            this.energyExtractorItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.energyExtractorItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInventoryName()
    {
        return "container.energyExtractor";
    }

    
    public void readFromNBT(NBTTagCompound par1)
    {
        super.readFromNBT(par1);
        NBTTagList var2 = par1.getTagList("Items", 10);
        this.energyExtractorItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i=0;i<var2.tagCount();++i)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(i);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.energyExtractorItemStacks.length)
            {
                this.energyExtractorItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.remainingEnergyToGenerate = par1.getShort("GenTime");
        this.curEnergy = par1.getShort("EnergyStored");
        
        updateLists(this.xCoord, this.yCoord, this.zCoord);
    }
    
    
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("GenTime", (short)this.remainingEnergyToGenerate);
        par1NBTTagCompound.setShort("EnergyStored", (short)this.curEnergy);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.energyExtractorItemStacks.length; ++var3)
        {
            if (this.energyExtractorItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.energyExtractorItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }
	
    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getGenTimeRemainingScaled(int par1)
    {
        return this.curEnergy * par1 / maxEnergy;
    }
    
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
    	if(maxEnergy!=10000) maxEnergy = 10000;
		if(!hasLoaded) {
			updateLists(this.xCoord, this.yCoord, this.zCoord);
			hasLoaded = true;
		}
        boolean isRunning = this.remainingEnergyToGenerate > 0;
        boolean var2 = false;
        int range = 4;	
        
        if (this.remainingEnergyToGenerate > 0)
        {
            int addBy = 10;
            if(curEnergy > 5000){
            	addBy = 10-((curEnergy-5000)/500);
            }
            
            if(remainingEnergyToGenerate-addBy<0){
            	curEnergy-=remainingEnergyToGenerate;
            	remainingEnergyToGenerate = 0;
            }else{
                remainingEnergyToGenerate-=addBy;
                curEnergy += addBy;
            }
            if(remainingEnergyToGenerate <= 0){
            	if(itemToGiveOnceFinished!=null){
            		if(this.energyExtractorItemStacks[1]!=null){
            			this.energyExtractorItemStacks[1].stackSize++;
            		}else{
            			this.energyExtractorItemStacks[1] = itemToGiveOnceFinished;
            		}
            		itemToGiveOnceFinished = null;
            	}
            }
            
        }

        if (!this.worldObj.isRemote)
        {
        	if(energyExtractorItemStacks[0] != null){
        		if(!hasEnergy()){
        			ItemStack var3 = EnergyExtractorRecipes.extracting().getExtractingResult(this.energyExtractorItemStacks[0]);
        			if(var3 != null && canExtract()){
	        			this.itemToGiveOnceFinished = var3;
	        			remainingEnergyToGenerate = getItemEnergy(this.energyExtractorItemStacks[0]);
	        			decrStackSize(0, 1);
	        			var2 = true;
        			}
        		}
        	}
        }
        
        //Put TileEntity stuff here
		for(int i=0;i<batboxes.size();i++){
			Reference.transferEnergy(this, batboxes.get(i), 20);
		}
		
		for(int i=0;i<heaters.size();i++){
			Reference.transferEnergy(this, heaters.get(i), 20);
		}
		
		for(int i=0;i<compressors.size();i++){
			Reference.transferEnergy(this, compressors.get(i), 20);
		}
		
		for(int i=0;i<grinders.size();i++){
			Reference.transferEnergy(this, grinders.get(i), 20);
		}
		
		for(int i=0;i<refineries.size();i++){
			Reference.transferEnergy(this, refineries.get(i), 20);
		}

		for(int i=0;i<relays.size();i++){
			Reference.transferEnergy(this, relays.get(i), 20);
		}
			
        
        if (var2)
        {
            this.markDirty();
        }
    }

    /**
     * Returns true if the energyExtractor can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canExtract()
    {
        if (this.energyExtractorItemStacks[0] == null)
        {
            return false;
        }
        else
        {
        	if(curEnergy >= maxEnergy) return false;
            ItemStack var1 = EnergyExtractorRecipes.extracting().getExtractingResult(this.energyExtractorItemStacks[0]);
            if (var1 == null) return false;
            if (this.energyExtractorItemStacks[1] == null) return true;
            if (!this.energyExtractorItemStacks[1].isItemEqual(var1)) return false;
            int result = energyExtractorItemStacks[1].stackSize + var1.stackSize;
            return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
        }
    }
    
    @Override
    public int getItemEnergy(ItemStack item){
    	ItemStack var1 = EnergyExtractorRecipes.extracting().getExtractingResult(item);
    	if(var1!=null){
    		Item item1 = item.getItem();
    		if(item1 == ItemsIB.chargedCoal) return 2000;
    		else if(item1 == Items.redstone) return 1000;
    		else if(item1 == Items.glowstone_dust) return 500;
  
    	}
    	return 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack i2) {
		
		return i == 0 || (i==1 && isItemFuel(i2));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side!=0 ? new int[]{0} : new int[]{1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		
		return (slot==0 && EnergyExtractorRecipes.extracting().getExtractingResult(item)!=null); 
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return slot==1;
	}
}
