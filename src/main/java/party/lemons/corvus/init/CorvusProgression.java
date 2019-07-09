package party.lemons.corvus.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.progression.Progression;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
@GameRegistry.ObjectHolder(Corvus.MODID)
public class CorvusProgression
{
	public static final Progression AWAKEN = null;
	public static final Progression TAME_CROW = null;
	public static final Progression KILL_WOLF = null;
	public static final Progression SPIRIT = null;

	@SubscribeEvent
	public static void onRegisterProgression(RegistryEvent.Register<Progression> event)
	{
		Progression awaken = new Progression()
				.setRegistryName(Corvus.MODID, "awaken")
				.setDisplayIcon(new ItemStack(Items.BED, 1, 3));

		event.getRegistry().register(
			awaken
		);

		event.getRegistry().register(
				new Progression()
						.setRegistryName(Corvus.MODID, "tame_crow")
						.setDisplayIcon(new ItemStack(CorvusItems.WORMWOOD))
						.setParents(awaken)
		);

		event.getRegistry().register(
				new Progression()
						.setRegistryName(Corvus.MODID, "kill_wolf")
						.setDisplayIcon(new ItemStack(Items.BONE))
						.setParents(awaken)
		);

		event.getRegistry().register(
				new Progression()
						.setRegistryName(Corvus.MODID, "spirit")
						.setDisplayIcon(new ItemStack(CorvusItems.OIL_PROTECTIVE))
						.setParents(awaken)
		);
	}
}
