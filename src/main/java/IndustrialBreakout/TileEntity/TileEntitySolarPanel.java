package IndustrialBreakout.TileEntity;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import IndustrialBreakout.TileEntity.bases.TileEntityGenerator;
import IndustrialBreakout.reference.Reference;

public class TileEntitySolarPanel extends TileEntityGenerator{
	
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
		if(maxEnergy!=1000)maxEnergy=1000;
		if(!hasLoaded) {
			updateLists(this.xCoord, this.yCoord, this.zCoord);
			hasLoaded = true;
		}
		World w = this.worldObj;
		boolean canGen;
		int range = 4;
		
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		if(isClearAbove(w,x,y,z) && w.isDaytime()){
			canGen = curEnergy < maxEnergy;
		}else{
			canGen = false;
		}
		
		if(canGen){
			++curEnergy;
		}
		
		//TileEntityStuff
		
		for(int i=0;i<batboxes.size();i++){
			Reference.transferEnergy(this, batboxes.get(i), 1);
		}
		
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

		for(int i=0;i<relays.size();i++){
			Reference.transferEnergy(this, relays.get(i), 1);
		}
	}
	
	public boolean isClearAbove(World w, int x, int y, int z){
		for(int i=1;i<=256-y;i++){
			if(w.getBlock(x, y+i, z).isOpaqueCube()) return false;
		}
		return true;
	}

}
