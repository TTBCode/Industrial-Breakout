package IndustrialBreakout.Machines;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import IndustrialBreakout.TileEntity.TileEntityRelay;
import IndustrialBreakout.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIBMachineEnergyRelay extends BlockContainer
{
    /**
     * Is the random generator used by relay to drop the inventory contents in random directions.
     */
    private Random relayRand = new Random();

    /** True if this is an active relay, false if idle */
    private final boolean isActive;

    /**
     * This flag is used to prevent the relay inventory to be dropped upon block removal, is used internally when the
     * relay block changes from idle to active and vice-versa.
     */
    private static boolean keepRelayInventory = false;

    public BlockIBMachineEnergyRelay(boolean par2)
    {
        super(Material.rock);
        this.isActive = par2;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityRelay();
    }
    
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir){
    	icons = new IIcon[2];
    	
    	icons[0] = ir.registerIcon(Reference.MOD_ID+":10");
    	icons[1] = ir.registerIcon(Reference.MOD_ID+":14");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
    	switch(side)
    	{
    		case 1:
    			return icons[1];
    		default:
    			return icons[0];
    	}
    }
}
