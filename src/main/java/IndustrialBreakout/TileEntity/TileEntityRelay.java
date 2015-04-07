package IndustrialBreakout.TileEntity;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import IndustrialBreakout.TileEntity.bases.TileEntityGenerator;
import IndustrialBreakout.reference.Reference;

public class TileEntityRelay extends TileEntityGenerator{
	
    public void readFromNBT(NBTTagCompound par1)
    {
        super.readFromNBT(par1);
        this.curEnergy = par1.getShort("Energy");
        
        updateLists(this.xCoord, this.yCoord, this.zCoord);
    }
    
    
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Energy", (short)this.curEnergy);
    }
	
	public void updateEntity(){
	    if(range!=20)range = 20;
	    if(maxEnergy!=2000)maxEnergy = 2000;
		if(!hasLoaded) {
			updateLists(this.xCoord, this.yCoord, this.zCoord);
			hasLoaded = true;
		}
		
		for(int i=0;i<batboxes.size();i++){
			Reference.transferEnergy(this, batboxes.get(i), 50);
		}
		
		for(int i=0;i<heaters.size();i++){
			Reference.transferEnergy(this, heaters.get(i), 50);
		}
		
		for(int i=0;i<compressors.size();i++){
			Reference.transferEnergy(this, compressors.get(i), 50);
		}
		
		for(int i=0;i<grinders.size();i++){
			Reference.transferEnergy(this, grinders.get(i), 50);
		}
		
		for(int i=0;i<refineries.size();i++){
			Reference.transferEnergy(this, refineries.get(i), 50);
		}
		
	}

}
