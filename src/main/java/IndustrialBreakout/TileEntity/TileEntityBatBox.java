package IndustrialBreakout.TileEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import IndustrialBreakout.TileEntity.bases.TileEntityGenerator;
import IndustrialBreakout.reference.Reference;

public class TileEntityBatBox extends TileEntityGenerator implements IInventory, ISidedInventory{
	
	private int previousEnergy;
	public boolean isCharging = false;
	
	@Override
	public void updateEntity(){
		if(maxEnergy!=50000)maxEnergy=50000;
		if(!hasLoaded) {
			updateLists(this.xCoord, this.yCoord, this.zCoord);
			hasLoaded = true;
		}
		isCharging = previousEnergy!=curEnergy;
		previousEnergy = curEnergy;

		
		
		for(int i=0;i<heaters.size();i++){
			Reference.transferEnergy(this, heaters.get(i), 1);
		}
		
		for(int i=0;i<compressors.size();i++){
			Reference.transferEnergy(this, compressors.get(i), 1);
		}
		
		for(int i=0;i<grinders.size();i++){
			Reference.transferEnergy(this, grinders.get(i), 1);
		}
		
		for(int i=0;i<refineries.size();i++){
			Reference.transferEnergy(this, refineries.get(i), 1);
		}
	}
	
    public void readFromNBT(NBTTagCompound par1)
    {
        super.readFromNBT(par1);
        this.curEnergy = par1.getShort("Energy");
        
    }
    
    
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Energy", (short)this.curEnergy);
    }

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		
	}

	@Override
	public String getInventoryName() {
		return "container.batbox";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}
	
	
}
