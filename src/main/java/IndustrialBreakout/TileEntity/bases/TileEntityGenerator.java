package IndustrialBreakout.TileEntity.bases;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import IndustrialBreakout.Main;
import IndustrialBreakout.TileEntity.TileEntityBatBox;
import IndustrialBreakout.TileEntity.TileEntityCompressor;
import IndustrialBreakout.TileEntity.TileEntityGrinder;
import IndustrialBreakout.TileEntity.TileEntityHeater;
import IndustrialBreakout.TileEntity.TileEntityRefinery;
import IndustrialBreakout.TileEntity.TileEntityRelay;
import IndustrialBreakout.reference.Reference;

public class TileEntityGenerator extends TileEntityMachine{
	
	public int range = 4;
	
	public boolean hasLoaded = false;
	
	public List<TileEntityBatBox> batboxes = new ArrayList<TileEntityBatBox>();
	public List<TileEntityHeater> heaters = new ArrayList<TileEntityHeater>();
	public List<TileEntityCompressor> compressors = new ArrayList<TileEntityCompressor>();
	public List<TileEntityGrinder> grinders = new ArrayList<TileEntityGrinder>();
	public List<TileEntityRefinery> refineries = new ArrayList<TileEntityRefinery>();
	public List<TileEntityRelay> relays = new ArrayList<TileEntityRelay>();
	
	public void updateLists(int x, int y, int z){
		
		this.batboxes.clear();
		this.heaters.clear();
		this.compressors.clear();
		this.grinders.clear();
		this.refineries.clear();
		this.relays.clear();
		
		for(int xi=-range; xi <= range; xi++){
        	for(int yi=-range; yi <= range; yi++){
        		for(int zi=-range; zi<= range;zi++){
        			if(Reference.isSelf(xi,yi,zi))zi++;
        			TileEntity te = worldObj.getTileEntity(x+xi, y+yi, z+zi);
        			if(te != null){
        				if(te instanceof TileEntityCompressor){
        					TileEntityCompressor tec = (TileEntityCompressor)te;
        					compressors.add(tec);
        				}else if(te instanceof TileEntityGrinder){
        					TileEntityGrinder tec = (TileEntityGrinder)te;
        					grinders.add(tec);
        				}else if(te instanceof TileEntityRefinery){
        					TileEntityRefinery tec = (TileEntityRefinery)te;
        					refineries.add(tec);
        				}else if(te instanceof TileEntityHeater){
        					TileEntityHeater tec = (TileEntityHeater)te;
        					heaters.add(tec);
        				}else if(te instanceof TileEntityRelay){
        					TileEntityRelay tec = (TileEntityRelay)te;
        					relays.add(tec);
        				}else if(te instanceof TileEntityBatBox){
        					TileEntityBatBox tec = (TileEntityBatBox)te;
        					batboxes.add(tec);
        				}
        			}
        		}
        	}
        }
	}
}
