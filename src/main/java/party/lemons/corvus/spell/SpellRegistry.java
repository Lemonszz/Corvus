package party.lemons.corvus.spell;

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
public class SpellRegistry
{
	public static IForgeRegistry<Spell> REGISTRY;
	public static final Spell EMPTY = null;

	@SubscribeEvent
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = new RegistryBuilder<Spell>()
				.setType(Spell.class)
				.setDefaultKey(new ResourceLocation(Corvus.MODID, "empty"))
				.setName(new ResourceLocation(Corvus.MODID, "spells")).allowModification()
				.create();
	}
}
