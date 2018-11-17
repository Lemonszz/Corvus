package party.lemons.corvus.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.corvus.Corvus;

public class PotionCorvus extends Potion
{
	private static final ResourceLocation loc = new ResourceLocation(Corvus.MODID, "textures/gui/potion_icons.png");
	private int potionIndex;

	public PotionCorvus(boolean isBadEffectIn, int liquidColorIn, int iconX, int iconY)
	{
		super(isBadEffectIn, liquidColorIn);
		setIconIndex(iconX, iconY);
	}

	@Override
	protected Potion setIconIndex(int p_76399_1_, int p_76399_2_)
	{
		this.potionIndex = p_76399_1_ + p_76399_2_ * 8;
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		drawIcon(x,y, 8, 8, effect, mc, 1);
	}

	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha)
	{
		drawIcon(x,y, 4, 4, effect, mc, alpha);
	}

	@SideOnly(Side.CLIENT)
	public void drawIcon(int x, int y, int offsetX, int offsetY, PotionEffect effect, Minecraft mc, float alpha)
	{
		GlStateManager.pushMatrix();
		mc.getTextureManager().bindTexture(loc);

		GlStateManager.color(1,1, 1, alpha);
		int uX = potionIndex % 8 * 18;
		int uV = potionIndex / 8 * 18;

		GuiUtils.drawTexturedModalRect(x + offsetX, y + offsetY, uX, uV, 18, 18, 0);
		GlStateManager.popMatrix();
	}
}
