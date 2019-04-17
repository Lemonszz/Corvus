package party.lemons.corvus.gen.gaia.dimension;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.corvus.Corvus;
import party.lemons.lemonlib.event.InitEvent;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class GaiaDimension
{
	public static final int GAIA_ID = 939;  //TODO: Config

	public static DimensionType TYPE = DimensionType.register("gaia", "gaia", GAIA_ID, GaiaDimensionProvider.class, false);

	@SubscribeEvent
	public static void onInit(InitEvent.Init event)
	{
		DimensionManager.registerDimension(GAIA_ID, TYPE);
	}

	@SubscribeEvent
	public static void onSelectEntityToSpawn(LivingSpawnEvent.CheckSpawn event)
	{
		if(isInDim(event.getWorld()) && !(event.getEntity() instanceof EntityWitch) && event.getEntity() instanceof IMob && event.getSpawner() == null && event.getWorld().rand.nextInt(20) == 0)
		{
			event.setResult(Event.Result.DENY);
			EntityWitch witch = new EntityWitch(event.getWorld());
			witch.setPositionAndUpdate(event.getX(), event.getY(), event.getZ());

			event.getWorld().spawnEntity(witch);
		}
	}

	private static boolean isInDim(World world)
	{
		return world.provider.getDimension() == GAIA_ID;
	}
}
