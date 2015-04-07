package IndustrialBreakout.Machines;

import IndustrialBreakout.reference.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIBMachineSolarGen extends BlockContainer{

	
	
	public BlockIBMachineSolarGen(Material mat) {
		super(mat);
	}
	
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir){
    	icons = new IIcon[2];
    	
    	icons[0] = ir.registerIcon(Reference.MOD_ID+":10");
    	icons[1] = ir.registerIcon(Reference.MOD_ID+":13");
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
	
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new IndustrialBreakout.TileEntity.TileEntitySolarPanel();
    }
}
