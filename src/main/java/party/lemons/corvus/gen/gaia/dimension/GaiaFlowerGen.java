package party.lemons.corvus.gen.gaia.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.gen.gaia.FeatureFlower;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.lemonlib.gen.feature.FeatureChance;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class GaiaFlowerGen
{



	@SubscribeEvent
	public static void onPopulateChunk(PopulateChunkEvent.Pre event)
	{
		World world = event.getWorld();
		if(world.provider.getDimension() != GaiaDimension.GAIA_ID)
			return;

		Random rand = event.getRand();
		BlockPos pos = new ChunkPos(event.getChunkX(), event.getChunkZ()).getBlock(8, 0, 8);


	}
}
