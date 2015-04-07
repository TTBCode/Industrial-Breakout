package IndustrialBreakout.Containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import IndustrialBreakout.Machines.recipes.CompressorRecipes;
import IndustrialBreakout.Slot.SlotIB;
import IndustrialBreakout.TileEntity.TileEntityBatBox;
import IndustrialBreakout.TileEntity.TileEntityCompressor;

public class ContainerBatBox extends Container{

	private TileEntityBatBox batBox;
	private int lastCurEnergy;
	
	
    public ContainerBatBox(InventoryPlayer par1InventoryPlayer, TileEntityBatBox par2)
    {
        this.batBox = par2;
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.batBox.isUseableByPlayer(player);
	}

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.batBox.curEnergy);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.lastCurEnergy != this.batBox.curEnergy)
            {
                var2.sendProgressBarUpdate(this, 0, this.batBox.curEnergy);
            }
        }

        this.lastCurEnergy = this.batBox.curEnergy;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.batBox.curEnergy = par2;
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    
    
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 >= 0 && par2 < 27)
            {
            	if (!this.mergeItemStack(var5, 27, 36, false))
                {
                    return null;
                }
            }
            else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 0, 27, false))
            {
                return null;
            }
            else if (!this.mergeItemStack(var5, 0, 36, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }

}
