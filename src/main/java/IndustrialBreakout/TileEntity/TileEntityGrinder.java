package IndustrialBreakout.TileEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import IndustrialBreakout.Machines.recipes.EnergyExtractorRecipes;
import IndustrialBreakout.Machines.recipes.GrinderRecipes;
import IndustrialBreakout.TileEntity.bases.TileEntityMachine;
import IndustrialBreakout.items.ItemsIB;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGrinder extends TileEntityMachine implements IInventory, ISidedInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the grinder
     */
    private ItemStack[] grinderItemStacks = new ItemStack[3];

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the grinder burning for
     */
    public int currentItemBurnTime = 0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.grinderItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.grinderItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.grinderItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.grinderItemStacks[par1].stackSize <= par2)
            {
                var3 = this.grinderItemStacks[par1];
                this.grinderItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.grinderItemStacks[par1].splitStack(par2);

                if (this.grinderItemStacks[par1].stackSize == 0)
                {
                    this.grinderItemStacks[par1] = null;
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
        if (this.grinderItemStacks[par1] != null)
        {
            ItemStack var2 = this.grinderItemStacks[par1];
            this.grinderItemStacks[par1] = null;
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
        this.grinderItemStacks[par1] = par2ItemStack;

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
        return "container.grinder";
    }

    
    public void readFromNBT(NBTTagCompound par1)
    {
        super.readFromNBT(par1);
        NBTTagList var2 = par1.getTagList("Items", 10);
        this.grinderItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < var2.tagCount(); ++i)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(i);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.grinderItemStacks.length)
            {
                this.grinderItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.curEnergy = par1.getShort("BurnTime");
        this.progress = par1.getShort("CookTime");
        this.currentItemBurnTime = getItemEnergy(this.grinderItemStacks[1]);
    }
    
    
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.curEnergy);
        par1NBTTagCompound.setShort("CookTime", (short)this.progress);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.grinderItemStacks.length; ++var3)
        {
            if (this.grinderItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.grinderItemStacks[var3].writeToNBT(var4);
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
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.progress * par1 / 200;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.curEnergy * par1 / this.currentItemBurnTime;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean var1 = this.curEnergy > 0;
        boolean var2 = false;

        ItemStack var700 = GrinderRecipes.grinding().getGrindingResult(this.grinderItemStacks[0]);
        
        if (this.curEnergy > 0 && var700 != null)
        {
            --this.curEnergy;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.curEnergy == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.curEnergy = getItemEnergy(this.grinderItemStacks[1]);

                if (this.curEnergy > 0)
                {
                    var2 = true;

                    if (this.grinderItemStacks[1] != null)
                    {
                        --this.grinderItemStacks[1].stackSize;

                        if (this.grinderItemStacks[1].stackSize == 0)
                        {
                            this.grinderItemStacks[1] = this.grinderItemStacks[1].getItem().getContainerItem(grinderItemStacks[1]);
                        }
                    }
                }
            }

            if (this.canSmelt())
            {
            	if(hasEnergy())
            	{
	                ++this.progress;
	
	                if (this.progress == 200)
	                {
	                    this.progress = 0;
	                    this.smeltItem();
	                    var2 = true;
	                }
            	}
            }
            else
            {
                this.progress = 0;
            }

            if (var1 != this.curEnergy > 0)
            {
                var2 = true;
            }
        }

        if (var2)
        {
            this.markDirty();
        }
    }

    /**
     * Returns true if the grinder can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.grinderItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = GrinderRecipes.grinding().getGrindingResult(this.grinderItemStacks[0]);
            if (var1 == null) return false;
            if (this.grinderItemStacks[2] == null) return true;
            if (!this.grinderItemStacks[2].isItemEqual(var1)) return false;
            int result = grinderItemStacks[2].stackSize + var1.stackSize;
            return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
        }
    }

    /**
     * Turn one item from the grinder source stack into the appropriate smelted item in the grinder result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack var1 = GrinderRecipes.grinding().getGrindingResult(this.grinderItemStacks[0]);

            if (this.grinderItemStacks[2] == null)
            {
                this.grinderItemStacks[2] = var1.copy();
            }
            else if (this.grinderItemStacks[2].isItemEqual(var1))
            {
                grinderItemStacks[2].stackSize += var1.stackSize;
            }

            --this.grinderItemStacks[0].stackSize;

            if (this.grinderItemStacks[0].stackSize <= 0)
            {
                this.grinderItemStacks[0] = null;
            }
        }
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return (slot==1  && isItemFuel(item)) || (slot==0 && GrinderRecipes.grinding().getGrindingResult(item)!=null);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return slot == 2;
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 0 || (i==1 && isItemFuel(itemstack));
	}
}
