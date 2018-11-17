package party.lemons.corvus.proxy;

import net.minecraft.client.Minecraft;
import party.lemons.corvus.gui.GuiGrimoire;

public class ClientProxy implements IProxy
{
	@Override
	public void openGrimoire()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiGrimoire());
	}
}
