package party.lemons.corvus.gen.nargul;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.gen.FeatureFrankincenseTree;
import party.lemons.lemonlib.event.InitEvent;
import party.lemons.lemonlib.gen.feature.FeatureBiome;
import party.lemons.lemonlib.gen.feature.FeatureChance;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class NagualVillageGenerator
{
	private static final WorldGenerator VILLAGE_GEN = new FeatureChance(new NargualVillageGen(), 500);

	@SubscribeEvent
	public static void onBiomeDecorate(PopulateChunkEvent.Pre event)
	{
		int x = event.getRand().nextInt(16) + 8;
		int z = event.getRand().nextInt(16) + 8;

		BlockPos pos = new BlockPos(x, 0, z).add(event.getChunkX() * 16, 0, event.getChunkZ() * 16);
		pos = event.getWorld().getTopSolidOrLiquidBlock(pos);

		VILLAGE_GEN.generate(event.getWorld(), event.getRand(), pos);
	}

	@SubscribeEvent
	public static void onPreInit(InitEvent.Pre event)
	{
		MinecraftForge.TERRAIN_GEN_BUS.register(NagualVillageGenerator.class);
	}
}

