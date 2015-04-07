package IndustrialBreakout.items;

import IndustrialBreakout.Main;
import IndustrialBreakout.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemSpade;

public class ItemIBShovel extends ItemSpade
{
    private int picid;
    public ItemIBShovel(int picId, ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        setCreativeTab(Main.tabIndustrialBreakout);
        this.picid=picId;
    }
    public void registerIcons(IIconRegister iconRegister)
    {
    	this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+this.picid);
    }
}
