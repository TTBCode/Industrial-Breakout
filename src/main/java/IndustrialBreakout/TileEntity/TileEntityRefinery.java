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
import IndustrialBreakout.Machines.recipes.RefinerRecipes;
import IndustrialBreakout.TileEntity.bases.TileEntityMachine;
import IndustrialBreakout.items.ItemsIB;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityRefinery extends TileEntityMachine implements IInventory, ISidedInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the refiner
     */
    private ItemStack[] refinerItemStacks = new ItemStack[3];

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the refiner burning for
     */
    public int currentItemBurnTime = 0;

    /** The number of ticks that the current item has been cooking for */
    public int progress = 0;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.refinerItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.refinerItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.refinerItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.refinerItemStacks[par1].stackSize <= par2)
            {
                var3 = this.refinerItemStacks[par1];
                this.refinerItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.refinerItemStacks[par1].splitStack(par2);

                if (this.refinerItemStacks[par1].stackSize == 0)
                {
                    this.refinerItemStacks[par1] = null;
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
        if (this.refinerItemStacks[par1] != null)
        {
            ItemStack var2 = this.refinerItemStacks[par1];
            this.refinerItemStacks[par1] = null;
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
        this.refinerItemStacks[par1] = par2ItemStack;

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
        return "container.refinery";
    }

    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items",10);
        this.refinerItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.refinerItemStacks.length)
            {
                this.refinerItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.curEnergy = par1NBTTagCompound.getShort("BurnTime");
        this.progress = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.refinerItemStacks[1]);
    }
    
    
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.curEnergy);
        par1NBTTagCompound.setShort("CookTime", (short)this.progress);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.refinerItemStacks.length; ++var3)
        {
            if (this.refinerItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.refinerItemStacks[var3].writeToNBT(var4);
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

        ItemStack var700 = RefinerRecipes.refining().getRefiningResult(this.refinerItemStacks[0]);
        
        if (this.curEnergy > 0 && var700 != null)
        {
            --this.curEnergy;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.curEnergy == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.curEnergy = getItemBurnTime(this.refinerItemStacks[1]);

                if (this.curEnergy > 0)
                {
                    var2 = true;

                    if (this.refinerItemStacks[1] != null)
                    {
                        --this.refinerItemStacks[1].stackSize;

                        if (this.refinerItemStacks[1].stackSize == 0)
                        {
                            this.refinerItemStacks[1] = this.refinerItemStacks[1].getItem().getContainerItem(refinerItemStacks[1]);
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
	                    this.refineItem();
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
     * Returns true if the refiner can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.refinerItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = RefinerRecipes.refining().getRefiningResult(this.refinerItemStacks[0]);
            if (var1 == null) return false;
            if (this.refinerItemStacks[2] == null) return true;
            if (!this.refinerItemStacks[2].isItemEqual(var1)) return false;
            int result = refinerItemStacks[2].stackSize + var1.stackSize;
            return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
        }
    }

    /**
     * Turn one item from the refiner source stack into the appropriate smelted item in the refiner result stack
     */
    public void refineItem()
    {
        if (this.canSmelt())
        {
            ItemStack var1 = RefinerRecipes.refining().getRefiningResult(this.refinerItemStacks[0]);

            if (this.refinerItemStacks[2] == null)
            {
                this.refinerItemStacks[2] = var1.copy();
            }
            else if (this.refinerItemStacks[2].isItemEqual(var1))
            {
                refinerItemStacks[2].stackSize += var1.stackSize;
            }

            --this.refinerItemStacks[0].stackSize;

            if (this.refinerItemStacks[0].stackSize <= 0)
            {
                this.refinerItemStacks[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the refiner burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack par0ItemStack)
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
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
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
	public boolean isItemValidForSlot(int i, ItemStack p_94041_2_) {
		return i==0;
	}
}
