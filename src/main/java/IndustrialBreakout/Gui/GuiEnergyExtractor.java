package IndustrialBreakout.Gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import IndustrialBreakout.Containers.ContainerEnergyExtractor;
import IndustrialBreakout.TileEntity.TileEntityEnergyExtractor;
import IndustrialBreakout.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnergyExtractor extends GuiContainer
{
    private TileEntityEnergyExtractor energyExtractorInventory;
    private static final ResourceLocation rl = new ResourceLocation(Reference.MOD_ID, "textures/gui/EnergyExtractor.png");

    public GuiEnergyExtractor(InventoryPlayer par1InventoryPlayer, TileEntityEnergyExtractor par2TileEntityEnergyExtractor)
    {
        super(new ContainerEnergyExtractor(par1InventoryPlayer, par2TileEntityEnergyExtractor));
        this.energyExtractorInventory = par2TileEntityEnergyExtractor;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString("Energy Extractor", 6, 6, 4210752);
        this.fontRendererObj.drawString("Energy Stored: "+energyExtractorInventory.curEnergy, 6, 72, 4210752);
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

        if (this.energyExtractorInventory.hasEnergy())
        {
            var7 = this.energyExtractorInventory.remainingEnergyToGenerate/(2000/12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }
    }
}
