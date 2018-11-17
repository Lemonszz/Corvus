package party.lemons.corvus.capability.crow;

import net.minecraft.entity.player.EntityPlayer;
import party.lemons.lemonlib.handler.cooldown.CooldownUtil;

public class CrowContainer implements ICrow
{
	boolean isCrow;

	@Override
	public boolean isCrow()
	{
		return isCrow;
	}

	@Override
	public void setCrow(EntityPlayer player, boolean crow)
	{
		this.isCrow = crow;

		if(isCrow)
		{
			CooldownUtil.setCooldown(player, "crow_cd", 30);
		}
	}
}
