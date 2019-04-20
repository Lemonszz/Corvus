package party.lemons.corvus.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.gen.gaia.dimension.GaiaDimension;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.lemonlib.gen.feature.FeatureDimension;
import party.lemons.lemonlib.gen.feature.FeatureRange;
import party.lemons.lemonlib.gen.feature.FeatureVein;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusOreGen
{
	private static final WorldGenerator CRYSTAL_QUARTZ_GENERATOR =
			new FeatureRange(new FeatureVein(b ->CorvusBlocks.CRYSTAL_QUARTZ_ORE.getDefaultState(), 10), 1, 0, 16);

	private static final WorldGenerator CRYSTAL_QUARTZ_GENERATOR_GAIA =
			new FeatureDimension(new FeatureRange(new FeatureVein(b ->CorvusBlocks.CRYSTAL_QUARTZ_ORE.getDefaultState(), 10), 2, 0, 48), GaiaDimension.GAIA_ID);

	private static final WorldGenerator WISER_GEM_GENERATOR_GAIA =
			new FeatureDimension(new FeatureRange(new FeatureVein(b ->CorvusBlocks.WISER_GEM_ORE.getDefaultState(), 10), 2, 0, 48), GaiaDimension.GAIA_ID);

	private static final WorldGenerator DIAMOND_GENERATOR_GAIA =
			new FeatureDimension(new FeatureRange(new FeatureVein(b ->Blocks.DIAMOND_ORE.getDefaultState(), 4), 2, 0, 48), GaiaDimension.GAIA_ID);


	@SubscribeEvent
	public static void onPopulateChunk(PopulateChunkEvent.Pre event)
	{
		World world = event.getWorld();
		Random rand = event.getRand();
		BlockPos pos = new ChunkPos(event.getChunkX(), event.getChunkZ()).getBlock(8, 0, 8);

		CRYSTAL_QUARTZ_GENERATOR.generate(world, rand, pos);
		CRYSTAL_QUARTZ_GENERATOR_GAIA.generate(world, rand, pos);
		WISER_GEM_GENERATOR_GAIA.generate(world, rand, pos);
		DIAMOND_GENERATOR_GAIA.generate(world, rand, pos);
	}
}
