package IndustrialBreakout.items;

import IndustrialBreakout.Main;
import IndustrialBreakout.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ItemIBSword extends ItemSword
{
	private int picid;
    public ItemIBSword(int picId, ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        this.picid=picId;
        setCreativeTab(Main.tabIndustrialBreakout);
    }
    
    public void registerIcons(IIconRegister iconRegister)
    {
    	this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+this.picid);
    }
}
