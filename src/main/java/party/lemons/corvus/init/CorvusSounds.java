package party.lemons.corvus.init;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.corvus.Corvus;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusSounds
{
	public static final SoundEvent CROW_YELL = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent CROW_CAW = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent WIND = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent SUMMON = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent CONCEAL = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent CONCEAL_END = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent RITUAL = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent SPELL_FAIL = SoundEvents.ENTITY_PARROT_EAT;
	public static final SoundEvent OPEN_GRIMOIRE = SoundEvents.ENTITY_PARROT_EAT;

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event)
	{
		event.getRegistry().registerAll(
				createSound("crow_yell"),
				createSound("crow_caw"),
				createSound("wind"),
				createSound("summon"),
				createSound("conceal"),
				createSound("conceal_end"),
				createSound("ritual"),
				createSound("spell_fail"),
				createSound("open_grimoire")
		);
	}

	private static SoundEvent createSound(String name)
	{
		ResourceLocation loc = new ResourceLocation(Corvus.MODID, name);
		SoundEvent event = new SoundEvent(loc);
		event.setRegistryName(loc);

		return event;
	}
}
