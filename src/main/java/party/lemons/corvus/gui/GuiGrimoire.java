package party.lemons.corvus.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

public class GuiGrimoire extends GuiContainer
{
	private static ResourceLocation BACKGROUND = new ResourceLocation(Corvus.MODID, "textures/gui/grimoire.png");

	public GuiGrimoire()
	{
		super(new ContainerDummy());

		this.xSize = 252;
		this.ySize = 130;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		ISpirit spirit = SpiritUtil.getSpirit(mc.player);
		//TODO: support pages
		int xPos = 20;
		int yPos = 20;

		for(Spell spell : SpellRegistry.REGISTRY.getValuesCollection())
		{
			buttonList.add(new ButtonSpell(guiLeft + xPos, guiTop + yPos, spell));
			xPos += 60;
			if(xPos > 80)
			{
				xPos =  20;
				yPos += 60;
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		drawDefaultBackground();
		mc.renderEngine.bindTexture(BACKGROUND);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		renderHoveredToolTip(mouseX, mouseY);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		for(GuiButton btn : buttonList)
		{
			if(btn instanceof ButtonSpell && btn.isMouseOver())
			{
				ScaledResolution resolution = new ScaledResolution(mc);
				GuiUtils.drawHoveringText(Lists.newArrayList(I18n.format("spell." + ((ButtonSpell) btn).spell.getRegistryName().getPath() + ".name")), mouseX, mouseY, resolution.getScaledWidth(), resolution.getScaledHeight(), 250, fontRenderer);
			}
		}
	}
}
