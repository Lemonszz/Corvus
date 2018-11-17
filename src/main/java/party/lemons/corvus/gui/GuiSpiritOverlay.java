package party.lemons.corvus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritUtil;

@Mod.EventBusSubscriber(modid = Corvus.MODID, value = Side.CLIENT)
public class GuiSpiritOverlay
{
	private static ResourceLocation LOC = new ResourceLocation(Corvus.MODID, "textures/gui/hud.png");
	static double displayValue = 100;


	@SubscribeEvent
	public static void onGuiRender(RenderGameOverlayEvent.Pre event)
	{
		if(event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
			return;

		if(!SpiritUtil.hasGrimoire(Minecraft.getMinecraft().player))
			return;

		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		Minecraft.getMinecraft().renderEngine.bindTexture(LOC);
		ISpirit spirit = SpiritUtil.getSpirit(Minecraft.getMinecraft().player);
		double perc = displayValue / (float)spirit.getMaxSprit();
		double drawHeight = 64F - (64F * perc);

		displayValue = MathHelper.clampedLerp(displayValue, spirit.getSpirit(), event.getPartialTicks() / 4);

		GuiUtils.drawTexturedModalRect(8, 5, 0, 0, 10, 64, 0);
		GuiUtils.drawTexturedModalRect(10, (int) (6 + drawHeight), 10, 0, 6, 60 - (int)drawHeight, 0);

		String s = String.valueOf(Math.round(displayValue));
		Minecraft.getMinecraft().fontRenderer.drawString(s, (float)((5 + 8) - Minecraft.getMinecraft().fontRenderer.getStringWidth(s) / 2), 70, 0x8c4343, false);

		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.ICONS);
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
	}
}
