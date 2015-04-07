package IndustrialBreakout.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import IndustrialBreakout.blocks.BlocksIB;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorIB implements IWorldGenerator {
        @Override
        public void generate(Random r, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        	switch(world.provider.dimensionId) {
				case 0:
		       		addOreGen(r, chunkX, chunkZ, world, BlocksIB.oreChargedCoal, 3+r.nextInt(2), 16, 25);
		       		addOreGen(r, chunkX, chunkZ, world, BlocksIB.oreTin, 2 + r.nextInt(4), 32, 15);
		       		addOreGen(r, chunkX, chunkZ, world, BlocksIB.oreCopper, 4 + r.nextInt(2), 64, 30);
		      		addOreGen(r, chunkX, chunkZ, world, BlocksIB.oreTitanium, 2 + r.nextInt(2), 16, 20);
				break;
        	}
        }
        
        private void addOreGen(Random r, int cX, int cZ, World w, Block id, int numberOfGen, int y, int numOfVeins){		
        	for(int i = 0; i < numOfVeins; i++){
        		int x = cX*16 + r.nextInt(16);
        		int z = cZ*16 + r.nextInt(16);
        		(new WorldGenMinable(id, numberOfGen)).generate(w, r, x, y-r.nextInt(y-5), z);
        	}
        }
        
}