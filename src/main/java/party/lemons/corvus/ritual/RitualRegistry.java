package party.lemons.corvus.ritual;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import party.lemons.corvus.Corvus;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class RitualRegistry
{
	public static IForgeRegistry<Ritual> REGISTRY;

	public static final Ritual EMPTY = new Ritual().setRegistryName(Corvus.MODID, "empty");

	@SubscribeEvent
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY =  new RegistryBuilder<Ritual>()
				.setType(Ritual.class)
				.setDefaultKey(new ResourceLocation(Corvus.MODID, "empty"))
				.setName(new ResourceLocation(Corvus.MODID, "rituals")).allowModification().create();
	}
}
