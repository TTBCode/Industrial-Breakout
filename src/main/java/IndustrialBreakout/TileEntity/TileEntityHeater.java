package IndustrialBreakout.TileEntity;

import IndustrialBreakout.Main;
import IndustrialBreakout.Machines.BlockIBMachineHeater;
import IndustrialBreakout.TileEntity.bases.TileEntityMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class TileEntityHeater extends TileEntityMachine {
	public void updateEntity(){
		if(maxEnergy!=100)maxEnergy=100;
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
}
