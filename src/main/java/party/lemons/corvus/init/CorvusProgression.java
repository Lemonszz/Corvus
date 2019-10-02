package party.lemons.corvus.init;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.progression.Progression;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusProgression
{
	public static List<Progression> PROGRESSION_ORDER = new ArrayList<>();

	public static final Progression AWAKEN = null;
	public static final Progression TAME_CROW = null;
	public static final Progression KILL_WOLF = null;
	public static final Progression SPIRIT = null;

	@SubscribeEvent
	public static void onRegisterProgression(RegistryEvent.Register<Progression> event)
	{
		Progression awaken = new Progression()
				.setRegistryName(Corvus.MODID, "awaken")
				.setDisplayIcon(new ItemStack(Items.BED, 1, 8));

		registerProgression(event.getRegistry(),
			awaken
		);

		registerProgression(event.getRegistry(),
				new Progression()
						.setRegistryName(Corvus.MODID, "tame_crow")
						.setDisplayIcon(new ItemStack(CorvusItems.WORMWOOD))
						.setParents(awaken)
		);

		registerProgression(event.getRegistry(),
				new Progression()
						.setRegistryName(Corvus.MODID, "kill_wolf")
						.setDisplayIcon(new ItemStack(Items.BONE))
						.setParents(awaken)
		);

		registerProgression(event.getRegistry(), new Progression()
				.setRegistryName(Corvus.MODID, "spirit")
				.setDisplayIcon(new ItemStack(CorvusItems.OIL_PROTECTIVE))
				.setParents(awaken)
		);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END && !event.player.world.isRemote)
		{
			if(event.player.getActivePotionEffect(MobEffects.INVISIBILITY) != null && event.player.getActivePotionEffect(CorvusPotions.SLOW_FALL) != null)
			{
				ProgressionUtil.tryUnlockProgression(event.player, CorvusProgression.SPIRIT);
			}
		}
	}

	public static Progression registerProgression(IForgeRegistry<Progression> r, Progression progression)
	{
		PROGRESSION_ORDER.add(progression);
		r.register(progression);
		return progression;
	}
}
