package IndustrialBreakout.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import IndustrialBreakout.Containers.ContainerBatBox;
import IndustrialBreakout.TileEntity.TileEntityBatBox;
import IndustrialBreakout.reference.Reference;

public class GuiBatBox extends GuiContainer{

	private TileEntityBatBox batBoxInventory;
	
	public GuiBatBox(InventoryPlayer playerInv, TileEntityBatBox batbox) {
		super(new ContainerBatBox(playerInv, batbox));
		this.batBoxInventory = batbox;
	}
	
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString("Battery Box", 6, 6, 4210752);
        this.fontRendererObj.drawString("Charge : "+batBoxInventory.curEnergy, 15, 72, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/BatteryBox.png"));
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.batBoxInventory.hasEnergy())
        {
            var7 = this.batBoxInventory.curEnergy / (this.batBoxInventory.maxEnergy/90);
            this.drawTexturedModalRect(var5+44, var6+25, 0, 166, var7, 20);
        }
    }

}
