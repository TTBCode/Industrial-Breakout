package IndustrialBreakout.items;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import IndustrialBreakout.Main;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsIB {

	public static void init(){
		registerItems();
	}
	
	public static void registerItems(){
    	GameRegistry.registerItem(ingotTin, "Tin Ingot");
    	GameRegistry.registerItem(ingotCopper, "Copper Ingot");
    	GameRegistry.registerItem(ingotBronze, "Bronze Ingot");
    	GameRegistry.registerItem(ingotTitanium, "Titanium Ingot");
    	GameRegistry.registerItem(refinedTitanium, "Refined Titanium");
    	GameRegistry.registerItem(chargedCoal, "Charged Coal");
    	GameRegistry.registerItem(silicon, "Silicon");
    	GameRegistry.registerItem(solarPanel, "ISolarPanel");
    	GameRegistry.registerItem(dullDust, "IBDullDust");
    	GameRegistry.registerItem(heatingCoil, "IBHeatCoil");
    	GameRegistry.registerItem(pickaxeTitanium, "Titanium Pickaxe");
    	GameRegistry.registerItem(adminPickaxe, "Pickaxe of the Admin");
    	GameRegistry.registerItem(pickaxeBronze, "Bronze Pickaxe");
    	GameRegistry.registerItem(axeBronze, "Bronze Axe");
    	GameRegistry.registerItem(swordBronze, "Bronze Sword");
    	GameRegistry.registerItem(shovelBronze, "Bronze Shovel");
    	GameRegistry.registerItem(hoeBronze, "Bronze Hoe");
    	
    	GameRegistry.registerItem(debugTool, "Debuger");
    	GameRegistry.registerItem(wirelessConnector, "IBWirelessConnector");
	}
	
    //Tool Materials
    public static ToolMaterial Titanium = EnumHelper.addToolMaterial("titanium", 2, 2000, 7.0F, 3, 16);
    public static ToolMaterial Bronze = EnumHelper.addToolMaterial("bronze", 2, 512, 6.0F, 3, 10);
	
	//Items
    public static final Item ingotTitanium = (new ItemIB(0)).setUnlocalizedName("ingotTitanium");
    public static final Item chargedCoal = (new ItemIB(1)).setUnlocalizedName("chargedCoal");
    public static final Item pickaxeTitanium = (new ItemIBPickaxe(2, Titanium)).setUnlocalizedName("pickaxeTitanium");
    public static final Item adminPickaxe = (new ItemIBPickaxe(3, EnumHelper.addToolMaterial("adminPick", 4, -1, 100.0F, 20, 100))).setUnlocalizedName("adminPickaxe");
    public static final Item ingotTin = (new ItemIB(4)).setUnlocalizedName("ingotTin");
    public static final Item ingotCopper = (new ItemIB(5)).setUnlocalizedName("ingotCopper");
    public static final Item ingotBronze = (new ItemIB(6)).setUnlocalizedName("ingotBronze");
    public static final Item pickaxeBronze = (new ItemIBPickaxe(7, Bronze)).setUnlocalizedName("pickaxeBronze");
    public static final Item axeBronze = (new ItemIBAxe(8, Bronze)).setUnlocalizedName("axeBronze");
    public static final Item swordBronze = (new ItemIBSword(9, Bronze)).setUnlocalizedName("swordBronze");
    public static final Item shovelBronze = (new ItemIBShovel(10, Bronze)).setUnlocalizedName("shovelBronze");
    public static final Item hoeBronze = (new ItemIBHoe(11, Bronze)).setUnlocalizedName("hoeBronze");
    public static final Item refinedTitanium = (new ItemIB(12)).setUnlocalizedName("refinedTitanium");
    public static final Item silicon = (new ItemIB(13)).setUnlocalizedName("silicon");
    public static final Item solarPanel = (new ItemIB(14)).setUnlocalizedName("solarPanel");
    public static final Item dullDust = (new ItemIB(15)).setUnlocalizedName("dullDust");
    public static final Item heatingCoil = (new ItemIB(16)).setUnlocalizedName("heatingCoil");
    
    public static final Item debugTool = new DebugTool().setUnlocalizedName("debugTool").setCreativeTab(Main.tabIndustrialBreakout);
    public static final Item wirelessConnector = new WirelessConnector(17).setUnlocalizedName("wirelessConnector").setCreativeTab(Main.tabIndustrialBreakout);
}
