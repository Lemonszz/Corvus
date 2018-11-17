package party.lemons.corvus.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.lemonlib.event.InitEvent;
import party.lemons.lemonlib.gen.feature.FeatureBiome;
import party.lemons.lemonlib.gen.feature.FeatureChance;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusTreeGen
{
	private static final WorldGenerator FRANK_TREE_GEN = new FeatureChance(new FeatureBiome(new FeatureFrankincenseTree(false), BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA).toArray(new Biome[0])), 1000);

	@SubscribeEvent
	public static void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
	{
		int x = event.getRand().nextInt(16) + 8;
		int z = event.getRand().nextInt(16) + 8;

		BlockPos pos = new BlockPos(x, 0, z).add(event.getChunkPos().x * 16, 0, event.getChunkPos().z * 16);
		pos = event.getWorld().getTopSolidOrLiquidBlock(pos);
		FRANK_TREE_GEN.generate(event.getWorld(), event.getRand(), pos);
	}

	@SubscribeEvent
	public static void onPreInit(InitEvent.Pre event)
	{
		MinecraftForge.TERRAIN_GEN_BUS.register(CorvusTreeGen.class);
	}
}

