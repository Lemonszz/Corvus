package party.lemons.corvus.capability.gaiabreath;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritCapability;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.network.MessageSyncGaiaBreath;

public class GaiaBreathUtil
{
	public static void syncGaiaBreath(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		IGaiaBreath spirit = getGaiaBreath(player);
		CorvusNetwork.INSTANCE.sendTo(new MessageSyncGaiaBreath(spirit.getBreath()), (EntityPlayerMP) player);
	}

	public static IGaiaBreath getGaiaBreath(EntityPlayer player)
	{
		return player.getCapability(GaiaBreathCapability.CAPABILITY, null);
	}
}
