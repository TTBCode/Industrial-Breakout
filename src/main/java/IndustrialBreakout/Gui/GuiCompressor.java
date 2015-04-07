package IndustrialBreakout.Gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import IndustrialBreakout.Containers.ContainerCompressor;
import IndustrialBreakout.TileEntity.TileEntityCompressor;
import IndustrialBreakout.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCompressor extends GuiContainer
{
    private TileEntityCompressor compressorInventory;

    public GuiCompressor(InventoryPlayer par1InventoryPlayer, TileEntityCompressor par2TileEntityCompressor)
    {
        super(new ContainerCompressor(par1InventoryPlayer, par2TileEntityCompressor));
        this.compressorInventory = par2TileEntityCompressor;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString("Compressor", 60, 6, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/Compressor.png"));
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.compressorInventory.hasEnergy())
        {
            var7 = this.compressorInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.compressorInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }
}
