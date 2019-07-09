package party.lemons.corvus.progression;

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
public class ProgressionRegistry
{
	public static IForgeRegistry<Progression> REGISTRY;
	public static final Progression EMPTY = null;

	@SubscribeEvent
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = new RegistryBuilder<Progression>()
				.setType(Progression.class)
				.setDefaultKey(new ResourceLocation(Corvus.MODID, "empty"))
				.setName(new ResourceLocation(Corvus.MODID, "progression")).allowModification()
				.create();
	}
}
