package party.lemons.corvus.capability.progression;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.network.MessageSyncProgression;

public class ProgressionUtil
{
	public static void syncProgression(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		IPlayerProgression spirit = getProgression(player);
		CorvusNetwork.INSTANCE.sendTo(new MessageSyncProgression(spirit.getUnlockedProgression()), (EntityPlayerMP) player);
	}

	public static IPlayerProgression getProgression(EntityPlayer player)
	{
		return player.getCapability(PlayerProgressionCapability.CAPABILITY, null);
	}
}
