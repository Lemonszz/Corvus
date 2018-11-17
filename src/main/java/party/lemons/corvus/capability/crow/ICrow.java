package party.lemons.corvus.capability.crow;

import net.minecraft.entity.player.EntityPlayer;

public interface ICrow
{
	boolean isCrow();
	void setCrow(EntityPlayer player, boolean crow);
}
