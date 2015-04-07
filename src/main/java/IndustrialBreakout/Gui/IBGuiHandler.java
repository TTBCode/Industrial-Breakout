package IndustrialBreakout.Gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import IndustrialBreakout.TileEntity.*;
import IndustrialBreakout.Containers.*;

public class IBGuiHandler implements IGuiHandler {
        //returns an instance of the Container you made earlier
        @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityCompressor){
                        return new ContainerCompressor(player.inventory, (TileEntityCompressor) tileEntity);
                }
                else if(tileEntity instanceof TileEntityEnergyExtractor){
                	return new ContainerEnergyExtractor(player.inventory, (TileEntityEnergyExtractor)tileEntity);
                }
                else if(tileEntity instanceof TileEntityGrinder){
                	return new ContainerGrinder(player.inventory, (TileEntityGrinder)tileEntity);
                }
                else if(tileEntity instanceof TileEntityRefinery){
                	return new ContainerRefiner(player.inventory, (TileEntityRefinery)tileEntity);
                }
                else if(tileEntity instanceof TileEntityPoweredFurnace){
                	return new ContainerPoweredFurnace(player.inventory, (TileEntityPoweredFurnace)tileEntity);
                }
                else if(tileEntity instanceof TileEntityBatBox){
                	return new ContainerBatBox(player.inventory, (TileEntityBatBox)tileEntity);
                }
                return null;
        }

        //returns an instance of the Gui you made earlier
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityCompressor){
                        return new GuiCompressor(player.inventory, (TileEntityCompressor) tileEntity);
                }
                else if(tileEntity instanceof TileEntityEnergyExtractor){
                	return new GuiEnergyExtractor(player.inventory, (TileEntityEnergyExtractor)tileEntity);
                }
                else if(tileEntity instanceof TileEntityGrinder){
                	return new GuiGrinder(player.inventory, (TileEntityGrinder)tileEntity);
                }
                else if(tileEntity instanceof TileEntityRefinery){
                	return new GuiRefiner(player.inventory, (TileEntityRefinery)tileEntity);
                }
                else if(tileEntity instanceof TileEntityPoweredFurnace){
                	return new GuiPoweredFurnace(player.inventory, (TileEntityPoweredFurnace)tileEntity);
                }
                else if(tileEntity instanceof TileEntityBatBox){
                	return new GuiBatBox(player.inventory, (TileEntityBatBox)tileEntity);
                }
                return null;

        }
}
