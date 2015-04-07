package IndustrialBreakout.items;

import IndustrialBreakout.Main;
import IndustrialBreakout.reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

public class ItemIBPickaxe extends ItemPickaxe{

	private int picid;

	public ItemIBPickaxe(int picId, ToolMaterial tM) {
		super(tM);
        setCreativeTab(Main.tabIndustrialBreakout);
		this.picid=picId;
	}
    
    public void registerIcons(IIconRegister iconRegister)
    {
    	this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID+":"+this.picid);
    }

}
