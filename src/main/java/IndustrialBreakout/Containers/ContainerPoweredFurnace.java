package IndustrialBreakout.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import IndustrialBreakout.TileEntity.*;
import IndustrialBreakout.Slot.*;
import IndustrialBreakout.*;

public class ContainerPoweredFurnace extends Container
{
    private TileEntityPoweredFurnace poweredFurnace;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;

    public ContainerPoweredFurnace(InventoryPlayer par1InventoryPlayer, TileEntityPoweredFurnace par2TileEntityPoweredFurnace)
    {
        this.poweredFurnace = par2TileEntityPoweredFurnace;
        this.addSlotToContainer(new Slot(par2TileEntityPoweredFurnace, 0, 56, 35));
        this.addSlotToContainer(new SlotIB(par1InventoryPlayer.player, par2TileEntityPoweredFurnace, 1, 116, 35));
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
        par1ICrafting.sendProgressBarUpdate(this, 0, this.poweredFurnace.poweredFurnaceCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.poweredFurnace.heat);
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

            if (this.lastCookTime != this.poweredFurnace.poweredFurnaceCookTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.poweredFurnace.poweredFurnaceCookTime);
            }

            if (this.lastBurnTime != this.poweredFurnace.heat)
            {
                var2.sendProgressBarUpdate(this, 1, this.poweredFurnace.heat);
            }
        }

        this.lastCookTime = this.poweredFurnace.poweredFurnaceCookTime;
        this.lastBurnTime = this.poweredFurnace.heat;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.poweredFurnace.poweredFurnaceCookTime = par2;
        }

        if (par1 == 1)
        {
            this.poweredFurnace.heat = par2;
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.poweredFurnace.isUseableByPlayer(par1EntityPlayer);
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

            if (par2 == 1)
            {
                if (!this.mergeItemStack(var5, 2, 38, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (par2 != 0)
            {
                if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null)
                {
                    if (!this.mergeItemStack(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 2 && par2 < 29)
                {
                    if (!this.mergeItemStack(var5, 29, 38, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 29 && par2 < 38 && !this.mergeItemStack(var5, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 2, 38, false))
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
