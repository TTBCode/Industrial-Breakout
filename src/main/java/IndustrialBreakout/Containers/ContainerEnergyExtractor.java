package IndustrialBreakout.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import IndustrialBreakout.TileEntity.*;
import IndustrialBreakout.Machines.recipes.EnergyExtractorRecipes;
import IndustrialBreakout.Slot.*;
import IndustrialBreakout.*;

public class ContainerEnergyExtractor extends Container
{
    private TileEntityEnergyExtractor energyExtractor;
    private int currentEnergyStorage = 0;
    private int lastGenTime = 0;

    public ContainerEnergyExtractor(InventoryPlayer par1InventoryPlayer, TileEntityEnergyExtractor par2TileEntityEnergyExtractor)
    {
        this.energyExtractor = par2TileEntityEnergyExtractor;
        this.addSlotToContainer(new Slot(par2TileEntityEnergyExtractor, 0, 81, 17));
        this.addSlotToContainer(new SlotIB(par1InventoryPlayer.player, par2TileEntityEnergyExtractor, 1, 81, 53));
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

    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.energyExtractor.curEnergy);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.energyExtractor.remainingEnergyToGenerate);
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

            if (this.currentEnergyStorage != this.energyExtractor.curEnergy)
            {
                var2.sendProgressBarUpdate(this, 0, this.energyExtractor.curEnergy);
            }

            if (this.lastGenTime != this.energyExtractor.remainingEnergyToGenerate)
            {
                var2.sendProgressBarUpdate(this, 1, this.energyExtractor.remainingEnergyToGenerate);
            }
        }

        this.currentEnergyStorage = this.energyExtractor.curEnergy;
        this.lastGenTime = this.energyExtractor.remainingEnergyToGenerate;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.energyExtractor.curEnergy = par2;
        }

        if (par1 == 1)
        {
            this.energyExtractor.remainingEnergyToGenerate = par2;
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.energyExtractor.isUseableByPlayer(par1EntityPlayer);
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
            
            //If is output slot
            if (par2 == 1)
            {
                if (!this.mergeItemStack(var5, 2, 38, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            //Checking is not generator slot
            else if (par2 != 0)
            {
            	//If the shiftclicked item can be used for generation
                if (EnergyExtractorRecipes.extracting().getExtractingResult(var5) != null)
                {
                    if (!this.mergeItemStack(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                //Checking Inventory
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(var5, 29, 38, false))
                    {
                        return null;
                    }
                }
                //Checking HotBar
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 2, 38, false))
            {
                return null;
            }
            //has nothing in said slot(par2)
            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else//if there is >.>
            {
                var4.onSlotChanged();
            }
            //no idea
            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }
            //"     "
            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }
}
