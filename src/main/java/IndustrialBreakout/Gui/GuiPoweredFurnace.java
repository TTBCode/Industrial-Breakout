package IndustrialBreakout.Gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import IndustrialBreakout.Containers.ContainerPoweredFurnace;
import IndustrialBreakout.TileEntity.TileEntityPoweredFurnace;
import IndustrialBreakout.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPoweredFurnace extends GuiContainer
{
    private TileEntityPoweredFurnace poweredFurnaceInventory;
    private static final ResourceLocation rl = new ResourceLocation(Reference.MOD_ID, "textures/gui/PoweredFurnace.png");

    public GuiPoweredFurnace(InventoryPlayer par1InventoryPlayer, TileEntityPoweredFurnace par2TileEntityPoweredFurnace)
    {
        super(new ContainerPoweredFurnace(par1InventoryPlayer, par2TileEntityPoweredFurnace));
        this.poweredFurnaceInventory = par2TileEntityPoweredFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString("Powered Furnace", 6, 6, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(rl);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.poweredFurnaceInventory.hasHeat())
        {
            var7 = this.poweredFurnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 54 + 12 - var7, 176, var7, 14, var7 + 2);
        }

        var7 = this.poweredFurnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }
}
