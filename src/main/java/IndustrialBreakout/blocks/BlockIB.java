package IndustrialBreakout.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import IndustrialBreakout.items.ItemsIB;
import IndustrialBreakout.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIB extends Block
{	
	private int picid;
	
	@SideOnly(Side.CLIENT)
	private IIcon texture;
	
    public BlockIB(int picId, Material material)
    {
        super(material);
        this.picid = picId;
    }
    
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return this == BlocksIB.oreChargedCoal ? ItemsIB.chargedCoal : (this == BlocksIB.compressedCobble || this == BlocksIB.compressedStone ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(this));
    }
    
    public int quantityDropped(Random rand){
	    if(this == BlocksIB.compressedCobble){
			return 5 + rand.nextInt(4);
		}else if(this == BlocksIB.compressedStone){
			return 5 + rand.nextInt(4);
		}else if(this == BlocksIB.oreChargedCoal){
			return 1 + rand.nextInt(2);
		}
	    
    	return 1;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir){
    	texture = ir.registerIcon(Reference.MOD_ID+":"+this.picid);
    }
    
    @Override
    public IIcon getIcon(int side, int meta){
    	return texture;
    }
}
