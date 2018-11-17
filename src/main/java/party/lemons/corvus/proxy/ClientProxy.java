package party.lemons.corvus.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import party.lemons.corvus.gui.GuiGrimoire;
import party.lemons.corvus.init.CorvusSounds;

public class ClientProxy implements IProxy
{
	@Override
	public void openGrimoire()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiGrimoire());
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(CorvusSounds.OPEN_GRIMOIRE, 1.0F));
	}
}
