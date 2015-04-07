package IndustrialBreakout.items;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import IndustrialBreakout.TileEntity.bases.TileEntityGenerator;
import IndustrialBreakout.reference.Reference;

public class WirelessConnector extends Item{
	
	private int picid;
	
	public WirelessConnector(int picid){
		super();
		this.picid=picid;
	}
	
    public void registerIcons(IIconRegister iconRegister)
    {
    	this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+this.picid);
    }
	
	@Override
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
		if(world.isRemote){
			return itemStack;
		}
		MovingObjectPosition mop = Minecraft.getMinecraft().renderViewEntity.rayTrace(200, 1.0F);
		if(mop != null)
		{
		    int blockHitSide = mop.sideHit;
		    Block blockLookingAt = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
		    TileEntity entityAt = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
		    if(entityAt!=null){
		    	if(entityAt instanceof TileEntityGenerator){
		    		TileEntityGenerator tegg = (TileEntityGenerator)entityAt;
		    		tegg.updateLists(mop.blockX, mop.blockY, mop.blockZ);
		    		Reference.sendPlayerMessage(player, blockLookingAt.getLocalizedName()+" Updated!");

		    		for(int i=0;i<tegg.batboxes.size();i++){
		    			Reference.sendPlayerMessage(player, "Found Battery Box at "+tegg.batboxes.get(i).xCoord+", "+tegg.batboxes.get(i).yCoord+", "+tegg.batboxes.get(i).zCoord);
		    		}
		    		
		    		for(int i=0;i<tegg.heaters.size();i++){
		    			Reference.sendPlayerMessage(player, "Found Heater at "+tegg.heaters.get(i).xCoord+", "+tegg.heaters.get(i).yCoord+", "+tegg.heaters.get(i).zCoord);
		    		}
		    		
		    		for(int i=0;i<tegg.compressors.size();i++){
		    			Reference.sendPlayerMessage(player, "Found Compressor at "+tegg.compressors.get(i).xCoord+", "+tegg.compressors.get(i).yCoord+", "+tegg.compressors.get(i).zCoord);
		    		}
		    		
		    		for(int i=0;i<tegg.grinders.size();i++){
		    			Reference.sendPlayerMessage(player, "Found Grinder at "+tegg.grinders.get(i).xCoord+", "+tegg.grinders.get(i).yCoord+", "+tegg.grinders.get(i).zCoord);
		    		}
		    		
		    		for(int i=0;i<tegg.refineries.size();i++){
		    			Reference.sendPlayerMessage(player, "Found Refinery at "+tegg.refineries.get(i).xCoord+", "+tegg.refineries.get(i).yCoord+", "+tegg.refineries.get(i).zCoord);
		    		}

		    		for(int i=0;i<tegg.relays.size();i++){
		    			Reference.sendPlayerMessage(player, "Found Energy Relay at "+tegg.relays.get(i).xCoord+", "+tegg.relays.get(i).yCoord+", "+tegg.relays.get(i).zCoord);
		    		}
		    	}
		    }
		}
		return itemStack;
    }
}
