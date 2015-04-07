package IndustrialBreakout.items;

import IndustrialBreakout.Main;
import IndustrialBreakout.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemIB extends Item
{
	private int picid;
    public ItemIB(int picId)
    {
        super();
        this.picid=picId;
        setCreativeTab(Main.tabIndustrialBreakout);
    }
    
    public void registerIcons(IIconRegister iconRegister)
    {
    	this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+this.picid);
    }
}
