package party.lemons.corvus.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import party.lemons.corvus.Corvus;

public class GuiTab extends GuiButtonExt
{
	private static final ResourceLocation BG = new ResourceLocation(Corvus.MODID, "textures/gui/grimoire.png");
	private final ItemStack DISPLAY_STACK;
	private final String TRANSLATION_KEY;

	private final GuiGrimoire BOOK_GUI;

	public boolean selected = false;
	private boolean hoverPrev = false;

	private float _targetScale = 1;
	private float _scale = 1;
	private float _time = 40;

	public GuiTab(int id, int xPos, int yPos, String translationKey, ItemStack display, GuiGrimoire bookGui)
	{
		super(id, xPos, yPos, 17, 21, "");

		this.DISPLAY_STACK = display;
		this.TRANSLATION_KEY = translationKey;
		this.BOOK_GUI = bookGui;
	}

	public void drawButtonUnder(Minecraft mc, int mouseX, int mouseY , float partialTicks)
	{
		if (this.visible)
		{
			mc.renderEngine.bindTexture(BG);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			if(hovered != hoverPrev)
				_time = 0;

			int drawX = x;
			if(selected)
				drawX = x - 10;
			else if(hovered)
				drawX = x - 4;

			this.drawTexturedModalRect(drawX, this.y, 0, 135, 28, this.height);
			GlStateManager.color(1F, 1F, 1F, 1F);

			float currentScale = updateScale(partialTicks);

			GlStateManager.pushMatrix();
			GlStateManager.translate(drawX + 8, y + 4 + 8, 0);
			GlStateManager.scale(currentScale, currentScale, 0);
			GlStateManager.translate( -8, -12, 0);

			mc.getRenderItem().renderItemAndEffectIntoGUI(DISPLAY_STACK, 2, 2);
			GlStateManager.translate(0, 0, 0);
			GlStateManager.scale(1,1,1);

			GlStateManager.popMatrix();
			hoverPrev = hovered;
		}
	}

	public void drawButtonOver(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if(isMouseOver())
		{
			ScaledResolution res = new ScaledResolution(mc);
			GuiUtils.drawHoveringText(Lists.newArrayList(I18n.format(TRANSLATION_KEY)), mouseX, mouseY, res.getScaledWidth(), res.getScaledHeight(), 500, mc.fontRenderer);
		}
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if(this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height)
		{
			BOOK_GUI.clickTab(id);
			selected = true;
			return true;
		}

		return false;
	}

	protected float updateScale(float delta)
	{
		if(hovered)
		{
			_targetScale = 0.2F;
			_scale = 1;
		}
		else
		{
			_targetScale = -0.2F;
			_scale = 1.2F;
		}

		float currentScale = Elastic.easeOut(_time, _scale, _targetScale,  40);
		if(_time < 40)
			_time += delta;

		return currentScale;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{ //NOFU
	}

	private static class Elastic {

		public static float  easeOut(float t,float b , float c, float d) {
			if (t==0) return b;  if ((t/=d)==1) return b+c;
			float p=d*.3f;
			float a=c;
			float s=p/4;
			return (a*(float)Math.pow(2,-10*t) * (float)Math.sin( (t*d-s)*(2*(float)Math.PI)/p ) + c + b);
		}
	}
}
