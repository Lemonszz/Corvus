package party.lemons.corvus.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.progression.IPlayerProgression;
import party.lemons.corvus.capability.progression.ProgressionUtil;
import party.lemons.corvus.capability.spirit.ISpirit;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.init.CorvusItems;
import party.lemons.corvus.init.CorvusProgression;
import party.lemons.corvus.progression.Progression;
import party.lemons.corvus.progression.ProgressionRegistry;
import party.lemons.corvus.spell.Spell;
import party.lemons.corvus.spell.SpellRegistry;

public class GuiGrimoire extends GuiContainer
{
	private static ResourceLocation BACKGROUND = new ResourceLocation(Corvus.MODID, "textures/gui/grimoire.png");
	private static int tabID = 0;
	private static Progression selectedProgression = null;

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
		buttonList.clear();

		buttonList.add(new GuiTab(0, guiLeft - 14, 5 + guiTop, "grimoire.tab.progress", new ItemStack(CorvusItems.STAR), this));
		buttonList.add(new GuiTab(1, guiLeft - 14, 5 + guiTop + 22, "grimoire.tab.spells", new ItemStack(CorvusItems.SPELLBOOK), this));

		if(tabID == 0)
		{
			int yy = 12;
			for(Progression progression : CorvusProgression.PROGRESSION_ORDER)
			{
				buttonList.add(new GuiProgressionButton(buttonList.size(), guiLeft + 13, guiTop + yy, progression, this));
				yy += 21;
			}
		}
		else if(tabID == 1)
		{
			int xPos = 20;
			int yPos = 20;

			for (Spell spell : SpellRegistry.REGISTRY.getValuesCollection())
			{
				buttonList.add(new ButtonSpell(guiLeft + xPos, guiTop + yPos, spell));
				xPos += 60;
				if (xPos > 80)
				{
					xPos = 20;
					yPos += 60;
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		drawDefaultBackground();
		buttonList.stream().filter(b->b instanceof GuiTab).forEach(b->((GuiTab) b).drawButtonUnder(mc, mouseX, mouseY, partialTicks));
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

		if(tabID == 0 && selectedProgression != null)
		{
			drawCenteredString(fontRenderer, I18n.format(selectedProgression.getNameTranslationKey()), guiLeft + 127 + 63, guiTop + 10, 0xFFFFFF);

			fontRenderer.drawSplitString(I18n.format(selectedProgression.getDescriptionTranslationKey()), guiLeft + 139, guiTop + 30, 100, 0x666565);
		}

		buttonList.stream().filter(b->b instanceof GuiTab).forEach(b->
		{
			((GuiTab) b).drawButtonOver(mc, mouseX, mouseY, partialTicks);
			if(b.id == tabID)
				((GuiTab) b).selected = true;
			else
				((GuiTab) b).selected = false;
		});
	}

	public void clickTab(int id)
	{
		tabID = id;
		initGui();
	}

	public void onProgressClick(Progression progression)
	{
		IPlayerProgression playerProgression = ProgressionUtil.getProgression(mc.player);
		if(playerProgression.canUnlockProgression(progression))
			selectedProgression = progression;
	}
}
