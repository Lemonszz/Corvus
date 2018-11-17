package party.lemons.corvus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.init.CorvusNetwork;
import party.lemons.corvus.network.MessageSelectSpell;
import party.lemons.corvus.spell.Spell;

public class ButtonSpell extends GuiButton
{
	public final Spell spell;
	private static ResourceLocation SELECTION = new ResourceLocation(Corvus.MODID, "textures/gui/selection.png");

	public ButtonSpell(int xPos, int yPos, Spell spell)
	{
		super(0, xPos, yPos, 32, 32, "");

		this.spell = spell;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		enabled = SpiritUtil.hasSpellUnlocked(Minecraft.getMinecraft().player, spell);

		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();

		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		boolean active = SpiritUtil.isSpellActive(Minecraft.getMinecraft().player, spell);

		float bgAlpha = enabled ? 0.7F : 0.25F;
		mc.renderEngine.bindTexture(SELECTION);
		GlStateManager.color(191F / 255F, 170F / 255F, 140F / 255F, bgAlpha);
		drawModalRectWithCustomSizedTexture(x, y, 0, 0, 32, 32, 32, 32);

		float alpha = enabled ? 1F : 0.6F;
		mc.renderEngine.bindTexture(spell.getSpellIcon());

		if(!active)
		{
			if(!hovered || !enabled)
			{
				GlStateManager.color(104F / 255F, 91F / 255F, 82 / 255F, alpha);
			}else if(enabled)
			{
				GlStateManager.color(122F / 255F, 91F / 255F, 82 / 255F, alpha);
			}
		}
		else
		{
			if(!hovered || !enabled)
			{
				GlStateManager.color(58F / 255F, 88F / 255F, 135 / 255F, alpha);
			}
			else if(enabled)
			{
				GlStateManager.color(96 / 255F, 100F / 255F, 122 / 255F, alpha);
			}
		}
		drawModalRectWithCustomSizedTexture(x, y, 0, 0, 32, 32, 32, 32);

		GlStateManager.color(1,1,1, 1);

	}

	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		if(hovered) CorvusNetwork.INSTANCE.sendToServer(new MessageSelectSpell(spell));

		return super.mousePressed(mc, mouseX, mouseY);
	}
}
