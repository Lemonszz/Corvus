package party.lemons.corvus.gen.gaia.dimension;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.inventoryswitch.IInventorySwitch;
import party.lemons.corvus.capability.inventoryswitch.InventorySwitchCapability;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class GaiaDimensionTransferHandler
{
	@SubscribeEvent
	public static void onPlayerJoinDimension(PlayerEvent.PlayerChangedDimensionEvent event)
	{
		if(event.player.world.isRemote)
			return;

		if((event.toDim == GaiaDimension.GAIA_ID && event.fromDim != GaiaDimension.GAIA_ID) || event.toDim == 0 && event.fromDim == GaiaDimension.GAIA_ID)
		{
			System.out.println("Do switch");
			switchInventory(event.player);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerDeath(LivingDeathEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if(player.world.provider.getDimension() == GaiaDimension.GAIA_ID)
			{
				event.setCanceled(true);

				player.isDead = false;
				player.setHealth(6.0F);
				player.extinguish();
				player.fallDistance = 0;

				if(!player.world.isRemote)
					player.changeDimension(0, new TeleporterGaia((WorldServer) player.world, true));
			}
		}
	}

	public static void switchInventory(EntityPlayer player)
	{
		IInventorySwitch cap = player.getCapability(InventorySwitchCapability.CAPABILITY, null);
		cap.swapInventory(player);
	}
}
