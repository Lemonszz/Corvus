package party.lemons.corvus.capability.progression;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.network.MessageSyncProgression;
import party.lemons.corvus.progression.Progression;

public class ProgressionUtil
{
	public static void syncProgression(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		IPlayerProgression spirit = getProgression(player);
		CorvusNetwork.INSTANCE.sendTo(new MessageSyncProgression(spirit.getUnlockedProgression()), (EntityPlayerMP) player);
	}

	public static boolean tryUnlockProgression(EntityPlayer player, Progression progression)
	{
		IPlayerProgression playerProgression = getProgression(player);
		if(!playerProgression.hasProgression(progression) && playerProgression.canUnlockProgression(progression))
		{
			playerProgression.unlockProgression(progression);
			syncProgression(player);
			return true;
		}
		return false;
	}

	public static IPlayerProgression getProgression(EntityPlayer player)
	{
		return player.getCapability(PlayerProgressionCapability.CAPABILITY, null);
	}

	public static boolean hasProgression(EntityPlayer player, Progression progression)
	{
		IPlayerProgression playerProgression = getProgression(player);
		return playerProgression.hasProgression(progression);
	}
}
