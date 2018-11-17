package party.lemons.corvus.init;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.spell.*;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusSpells
{
	public static final Spell LEAP = SpellRegistry.EMPTY;
	public static final Spell CANIS_FAMILIAR = SpellRegistry.EMPTY;
	public static final Spell CONCEAL = SpellRegistry.EMPTY;

	@SubscribeEvent
	public static void onRegisterSpell(RegistryEvent.Register<Spell> event)
	{
		event.getRegistry().registerAll(
				new SpellLeap(10, 60).setRegistryName(Corvus.MODID, "leap"),
				new SpellSummonFamiliar(50, 1800).setRegistryName(Corvus.MODID, "canis_familiar"),
				new SpellConceal(30, 700).setRegistryName(Corvus.MODID, "conceal")
		);
	}
}
