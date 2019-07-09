package party.lemons.corvus.init;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.gen.biome.BiomeLightJungle;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusBiomes
{
	public static final Biome LIGHT_JUNGLE = null;

	@SubscribeEvent
	public static void onRegisterBiome(RegistryEvent.Register<Biome> event)
	{
		BiomeLightJungle lj = new BiomeLightJungle();
		lj.setRegistryName(Corvus.MODID, "light_jungle");
		event.getRegistry().register(lj);
		BiomeProvider.allowedBiomes.add(lj);

		BiomeDictionary.addTypes(lj, BiomeDictionary.Type.JUNGLE);
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(lj, 15));
	}
}
