package IndustrialBreakout;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Logger;

import IndustrialBreakout.Gui.IBGuiHandler;
import IndustrialBreakout.TileEntity.TileEntityBatBox;
import IndustrialBreakout.TileEntity.TileEntityCompressor;
import IndustrialBreakout.TileEntity.TileEntityEnergyExtractor;
import IndustrialBreakout.TileEntity.TileEntityGrinder;
import IndustrialBreakout.TileEntity.TileEntityHeater;
import IndustrialBreakout.TileEntity.TileEntityPoweredFurnace;
import IndustrialBreakout.TileEntity.TileEntityRefinery;
import IndustrialBreakout.TileEntity.TileEntityRelay;
import IndustrialBreakout.TileEntity.TileEntitySolarPanel;
import IndustrialBreakout.blocks.BlocksIB;
import IndustrialBreakout.items.ItemsIB;
import IndustrialBreakout.reference.Reference;
import IndustrialBreakout.world.WorldGeneratorIB;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, version = Reference.MOD_V, name = Reference.MOD_NAME)
public class Main {

		public static WorldGeneratorIB worldGen = new WorldGeneratorIB();
	
		public static CreativeTabs tabIndustrialBreakout = new CreativeTabs("IB_Tab"){
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem(){
				return ItemsIB.refinedTitanium;
			}
		};
		
        @Instance(Reference.MOD_ID)
        public static Main instance;
        
        public static final Logger logger = FMLLog.getLogger();
        
        
        @EventHandler
        public void preInit(FMLPreInitializationEvent preevt) {
        }
        
        @EventHandler
        public void init(FMLInitializationEvent event) {
                NetworkRegistry.INSTANCE.registerGuiHandler(this, new IBGuiHandler());
                GameRegistry.registerWorldGenerator(worldGen, 0);
                BlocksIB.init();
                ItemsIB.init();
                craft();
                smelt();
                register();
                refine();
                grind();
                compress();
                
        }
        
        @EventHandler
        public void postInit(FMLPostInitializationEvent e){
        	
        }
        
        public void craft(){
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.compressedCobble, 1), new Object[] {"###", "###", "###", '#', Blocks.cobblestone});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.compressedStone, 1), new Object[] {"###", "###", "###", '#', Blocks.stone});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.pickaxeTitanium), new Object[] {"TTT", " S ", " S ", 'T', ItemsIB.ingotTitanium, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.ingotBronze, 4), new Object[] {"##", "#$", '#', ItemsIB.ingotCopper, '$', ItemsIB.ingotTin});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.condencedStone, 2), new Object[] {"XYX", "Y Y", "XYX", 'X', BlocksIB.compressedStone, 'Y', BlocksIB.compressedCobble});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.condencedStone, 2), new Object[] {"XYX", "Y Y", "XYX", 'Y', BlocksIB.compressedStone, 'X', BlocksIB.compressedCobble});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.blockTin), new Object[] {"TTT", "TTT", "TTT", 'T', ItemsIB.ingotTin});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.blockCopper), new Object[] {"CCC", "CCC", "CCC", 'C', ItemsIB.ingotCopper});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineCompressor, 1), new Object[] {"TCT", "PIP", "TCT", 'T', ItemsIB.refinedTitanium, 'C', ItemsIB.chargedCoal, 'P', Blocks.piston, 'I', Blocks.iron_block});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineEnergyExtractor), new Object[] {"TCT", "cCc", "TCT", 'T', ItemsIB.refinedTitanium, 'C', ItemsIB.chargedCoal, 'c', Items.coal});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineGrinder), new Object[] {"TCT", "FOF", "TCT", 'T', ItemsIB.refinedTitanium, 'C', ItemsIB.chargedCoal, 'F', Items.flint});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineRefiner), new Object[] {"TCT", "BCB", "TCT", 'T', ItemsIB.ingotTitanium, 'C', ItemsIB.chargedCoal, 'B', ItemsIB.ingotBronze});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.solarPanel), new Object[] {"TST", "CSC", "TST", 'T', ItemsIB.ingotTitanium, 'C', ItemsIB.chargedCoal, 'S', ItemsIB.silicon});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.solarPanel), new Object[] {"TCT", "SSS", "TCT", 'T', ItemsIB.ingotTitanium, 'C', ItemsIB.chargedCoal, 'S', ItemsIB.silicon});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineGenSolar), new Object[] {"SSS", "CCC", "TTT", 'S', ItemsIB.solarPanel, 'C', ItemsIB.chargedCoal, 'T', ItemsIB.refinedTitanium});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineRelay), new Object[] {"TRT", "CSC", "TTT", 'T', ItemsIB.refinedTitanium, 'R', Blocks.redstone_torch, 'C', ItemsIB.chargedCoal, 'S', ItemsIB.silicon});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.heatingCoil), new Object[] {"CLC", 'C', ItemsIB.ingotCopper, 'L', Items.lava_bucket});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machineHeater), new Object[] {"THT", "CLC", "THT", 'T', ItemsIB.refinedTitanium, 'H', ItemsIB.heatingCoil, 'C', ItemsIB.chargedCoal, 'L', Items.lava_bucket});
        	GameRegistry.addRecipe(new ItemStack(BlocksIB.machinePoweredFurnace), new Object[] {"TST", "HLH", "TST", 'T', ItemsIB.refinedTitanium, 'S', ItemsIB.silicon, 'H', ItemsIB.heatingCoil, 'L', Items.lava_bucket});
        	
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.pickaxeBronze), new Object[] {"BBB", " S ", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.swordBronze), new Object[] {" B ", " B ", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.axeBronze), new Object[] {"BB ", "BS ", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.axeBronze), new Object[] {" BB", " SB", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.hoeBronze), new Object[] {"BB ", " S ", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.hoeBronze), new Object[] {" BB", " S ", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        	GameRegistry.addRecipe(new ItemStack(ItemsIB.shovelBronze), new Object[] {" B ", " S ", " S ", 'B', ItemsIB.ingotBronze, 'S', Items.stick});
        }
        
        public void smelt(){
        	GameRegistry.addSmelting(BlocksIB.oreTitanium, new ItemStack(ItemsIB.ingotTitanium, 1), 1.0F);
        	GameRegistry.addSmelting(BlocksIB.oreTin, new ItemStack(ItemsIB.ingotTin, 1), 1.0F);
        	GameRegistry.addSmelting(BlocksIB.oreCopper, new ItemStack(ItemsIB.ingotCopper, 1), 1.0F);
        	GameRegistry.addSmelting(BlocksIB.compressedCobble, new ItemStack(BlocksIB.compressedStone, 1), 0.0F);
        	GameRegistry.addSmelting(ItemsIB.dullDust, new ItemStack(Blocks.cobblestone), 0F);
        }
        
        
        public void refine(){
        	Reference.addToRefinery(new ItemStack(BlocksIB.condencedStone), new ItemStack(ItemsIB.silicon));
        }
        
        public void grind(){
        	Reference.addToGrinder(new ItemStack(BlocksIB.compressedCobble), new ItemStack(ItemsIB.dullDust, 5));
        	Reference.addToGrinder(new ItemStack(BlocksIB.compressedStone), new ItemStack(ItemsIB.dullDust, 6));
        	Reference.addToGrinder(new ItemStack(Blocks.cobblestone), new ItemStack(ItemsIB.dullDust));
        	Reference.addToGrinder(new ItemStack(Blocks.stone), new ItemStack(ItemsIB.dullDust));
        }
        
        public void compress(){
        	Reference.addToCompressor(new ItemStack(ItemsIB.dullDust), new ItemStack(Blocks.stone));
        }
        
		public void register(){
        	
        	//Tile Entities
        	GameRegistry.registerTileEntity(TileEntityCompressor.class, "IBCompressor");
        	GameRegistry.registerTileEntity(TileEntityEnergyExtractor.class, "IBGenMK1");
        	GameRegistry.registerTileEntity(TileEntityGrinder.class, "IBGrinder");
        	GameRegistry.registerTileEntity(TileEntityRefinery.class, "IBRefiner");
        	GameRegistry.registerTileEntity(TileEntitySolarPanel.class, "IBSolarPanel1");
        	GameRegistry.registerTileEntity(TileEntityRelay.class, "IBEnergyRelay");
        	GameRegistry.registerTileEntity(TileEntityHeater.class, "IBHeater");
        	GameRegistry.registerTileEntity(TileEntityPoweredFurnace.class, "IBPoweredFurnace");
        	GameRegistry.registerTileEntity(TileEntityBatBox.class, "IBBatBox");
        }
}