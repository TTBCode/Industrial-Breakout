package IndustrialBreakout.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import IndustrialBreakout.Main;
import IndustrialBreakout.Machines.BlockIBMachineBatBox;
import IndustrialBreakout.Machines.BlockIBMachineCompressor;
import IndustrialBreakout.Machines.BlockIBMachineEnergyRelay;
import IndustrialBreakout.Machines.BlockIBMachineGenMK1;
import IndustrialBreakout.Machines.BlockIBMachineGrinder;
import IndustrialBreakout.Machines.BlockIBMachineHeater;
import IndustrialBreakout.Machines.BlockIBMachinePoweredFurnace;
import IndustrialBreakout.Machines.BlockIBMachineRefiner;
import IndustrialBreakout.Machines.BlockIBMachineSolarGen;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlocksIB {
	
	public static void init(){
		registerBlocks();
	}
	
	public static void registerBlocks(){
		//Blocks
		
    	GameRegistry.registerBlock(compressedCobble, "Compressed Cobble");
    	GameRegistry.registerBlock(compressedStone, "Compressed Stone");
    	GameRegistry.registerBlock(oreTitanium, "Titanium Ore");
    	GameRegistry.registerBlock(oreChargedCoal, "Charged Coal Ore");
    	GameRegistry.registerBlock(oreTin, "Tin Ore");
    	GameRegistry.registerBlock(oreCopper, "Copper Ore");
    	GameRegistry.registerBlock(blockCopper, "Copper Block");
    	GameRegistry.registerBlock(blockTin, "Tin Block");
    	GameRegistry.registerBlock(machineCompressor, "Idle Compressor");
    	GameRegistry.registerBlock(machineEnergyExtractor, "Idle Generator MK1");
    	GameRegistry.registerBlock(condencedStone, "Condenced Stone");
    	GameRegistry.registerBlock(machineGrinder, "Grinder");
    	GameRegistry.registerBlock(machineRefiner, "Refiner");
    	GameRegistry.registerBlock(machineGenSolar, "Solar Panel");
    	GameRegistry.registerBlock(machineRelay, "IBEnergyRelay");
    	GameRegistry.registerBlock(machineHeater, "IBHeater");
    	GameRegistry.registerBlock(machinePoweredFurnace, "PoweredFurnaceIB");
    	GameRegistry.registerBlock(machineBatBox, "BatBoxIB");
	}
	
	//Blocks
    public static final Block compressedCobble = new BlockIB(0, Material.rock).setHardness(4.0F).setResistance(6.0F).setBlockName("compressedCobble").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block compressedStone = new BlockIB(1, Material.rock).setHardness(4.0F).setResistance(6.0F).setBlockName("compressedStone").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block oreTitanium = new BlockIB(2, Material.iron).setHardness(5.0F).setResistance(7.0F).setBlockName("oreTitanium").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block oreChargedCoal = new BlockIB(3, Material.iron).setHardness(5.0F).setResistance(7.0F).setBlockName("oreChargedCoal").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block oreTin = new BlockIB(4, Material.iron).setHardness(6.0F).setResistance(10.0F).setBlockName("oreTin").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block oreCopper = new BlockIB(5, Material.iron).setHardness(6.0F).setResistance(9.0F).setBlockName("oreCopper").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block blockCopper = new BlockIB(6, Material.iron).setHardness(7.0F).setResistance(10.0F).setBlockName("blockCopper").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block blockTin = new BlockIB(7, Material.iron).setHardness(8.0F).setResistance(11.0F).setBlockName("blockTin").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block condencedStone = new BlockIB(8, Material.iron).setHardness(8.0F).setResistance(12.0F).setBlockName("condencedStone").setCreativeTab(Main.tabIndustrialBreakout);
    
    //Machines
    public static final Block machineCompressor = (new BlockIBMachineCompressor(false)).setHardness(10.0F).setResistance(7.0F).setBlockName("machineCompressor").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineEnergyExtractor = (new BlockIBMachineGenMK1(false)).setHardness(11.0F).setResistance(7.0F).setBlockName("machineEnergyExtractor").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineGrinder = (new BlockIBMachineGrinder(false)).setHardness(11.0F).setResistance(7.0F).setBlockName("machineGrinder").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineRefiner = (new BlockIBMachineRefiner(false)).setHardness(11.0F).setResistance(7.0F).setBlockName("machineRefiner").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineGenSolar = (new BlockIBMachineSolarGen(Material.iron)).setHardness(12.0F).setResistance(6.0F).setBlockName("machineGenSolar").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineRelay = (new BlockIBMachineEnergyRelay(false)).setHardness(12F).setResistance(6F).setBlockName("machineRelay").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineHeater = (new BlockIBMachineHeater(15)).setHardness(15F).setResistance(13F).setBlockName("machineHeater").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machinePoweredFurnace = (new BlockIBMachinePoweredFurnace()).setHardness(12F).setResistance(7F).setBlockName("machinePoweredFurnace").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Block machineBatBox = new BlockIBMachineBatBox(16).setHardness(10F).setResistance(5F).setBlockName("machineBatBox").setCreativeTab(Main.tabIndustrialBreakout);
}
