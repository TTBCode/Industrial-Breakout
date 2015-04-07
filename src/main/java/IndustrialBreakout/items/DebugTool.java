package IndustrialBreakout.items;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import IndustrialBreakout.TileEntity.bases.TileEntityMachine;
import IndustrialBreakout.reference.Reference;

public class DebugTool extends Item{
	
	public DebugTool(){
		super();
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
		    TileEntity lookedAt = world.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
		    if(lookedAt!=null) Reference.sendPlayerMessage(player, ((TileEntityMachine)lookedAt).toString());
		}
		return itemStack;
    }
	
}
