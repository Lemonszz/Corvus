package party.lemons.corvus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.progression.IPlayerProgression;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.progression.Progression;

public class GuiProgressionButton extends GuiButtonExt
{
	private final Progression progression;
	private static ResourceLocation BACKGROUND = new ResourceLocation(Corvus.MODID, "textures/gui/grimoire.png");
	private final GuiGrimoire bookGui;

	public GuiProgressionButton(int id, int xPos, int yPos, Progression progression, GuiGrimoire bookGui)
	{
		super(id, xPos, yPos, "");
		this.width = 102;

		this.progression = progression;
		this.bookGui = bookGui;
	}

	protected int getHoverState(boolean mouseOver) {
		int i = 1;
		if (!this.enabled) {
			i = 0;
		} else if (mouseOver) {
			i = 2;
		}

		IPlayerProgression progress = ProgressionUtil.getProgression(Minecraft.getMinecraft().player);

		if(progress.hasProgression(progression))
			i = 3;
		else if(!progress.canUnlockProgression(progression))
			i = 0;

		return i;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial)
	{
		if(!this.visible)
			return;

		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int k = this.getHoverState(this.hovered);
		GuiUtils.drawContinuousTexturedBox(BACKGROUND, this.x, this.y, 0, 160 + k * 21, this.width, this.height, 200, 21, 3, 3, 3, 3, this.zLevel);
		this.mouseDragged(mc, mouseX, mouseY);
		RenderHelper.enableGUIStandardItemLighting();

		ItemStack drawStack = progression.getDisplayIcon();
		mc.getRenderItem().renderItemAndEffectIntoGUI(drawStack, x + 2, y + 2);

		GlStateManager.color(1F,1F,1F, 1F);

		IPlayerProgression progress = ProgressionUtil.getProgression(Minecraft.getMinecraft().player);
		FontRenderer fnt = mc.fontRenderer;
		int color = 0x4a4a4a;

		if(!progress.canUnlockProgression(progression))
		{
			fnt = mc.standardGalacticFontRenderer;
			color = 0x858585;
		}

		fnt.drawString(I18n.format(progression.getNameTranslationKey()),  x + 18, y + 6, color);
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
	{
		if(!hovered)
			return false;

		bookGui.onProgressClick(progression);
		return true;
	}
}
