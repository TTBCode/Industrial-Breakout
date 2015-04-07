package IndustrialBreakout.Machines;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import IndustrialBreakout.TileEntity.TileEntityHeater;
import IndustrialBreakout.reference.Reference;

public class BlockIBMachineHeater extends BlockContainer {

	public static boolean isActive = false;
	
	private int picid;
	public BlockIBMachineHeater(int picId) {
		super(Material.iron);
		this.picid=picId;
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int i) {
		return new TileEntityHeater();
	}
	
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+this.picid);
    }

}
