package IndustrialBreakout.reference;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import IndustrialBreakout.Main;
import IndustrialBreakout.Machines.recipes.CompressorRecipes;
import IndustrialBreakout.Machines.recipes.GrinderRecipes;
import IndustrialBreakout.Machines.recipes.RefinerRecipes;
import IndustrialBreakout.TileEntity.TileEntityBatBox;
import IndustrialBreakout.TileEntity.TileEntityCompressor;
import IndustrialBreakout.TileEntity.TileEntityGrinder;
import IndustrialBreakout.TileEntity.TileEntityHeater;
import IndustrialBreakout.TileEntity.TileEntityRefinery;
import IndustrialBreakout.TileEntity.TileEntityRelay;
import IndustrialBreakout.TileEntity.bases.TileEntityMachine;

public class Reference {
	public static final String MOD_ID = "industrialbreakout";
	public static final String MOD_NAME = "Industrial Breakout";
	public static final String MOD_V = "V1.3.5";
	
	public static void addToGrinder(ItemStack itemToBeSmelted, ItemStack result){
		GrinderRecipes.grinding().addGrinding(itemToBeSmelted, result);
	}
	public static void addToRefinery(ItemStack itemToBeSmelted, ItemStack result){
		RefinerRecipes.refining().addRefining(itemToBeSmelted, result);
	}
	public static void addToCompressor(ItemStack itemToBeSmelted, ItemStack result){
		CompressorRecipes.compressing().addCompressing(itemToBeSmelted, result);
	}
	
	public static void transferEnergy(TileEntityMachine source, TileEntityMachine dest, int amount){
		if(source.curEnergy>0 && dest.canReceiveEnergy()){
			if(amount>source.curEnergy){
				int given = dest.maxEnergy-dest.curEnergy<source.curEnergy ? dest.maxEnergy-dest.curEnergy : source.curEnergy;
				source.curEnergy-=given;
				dest.curEnergy+=given;
			}else{
				int given = dest.maxEnergy-dest.curEnergy<amount ? dest.maxEnergy-dest.curEnergy : amount;
				source.curEnergy-=given;
				dest.curEnergy+=given;
			}
		}
	}
	
	public static boolean notInRange(int y){
		return y<1 || y>256;
	}
	
	public static boolean isSelf(int x, int y, int z){
		return x==0 && y==0 && z==0;
	}
	
	public static void sendPlayerMessage(EntityPlayer player, String msg){
		player.addChatMessage(new ChatComponentTranslation(msg));
	}
	
}
