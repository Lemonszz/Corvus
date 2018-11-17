package party.lemons.corvus.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.CorvusBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class RitualRecipeCategory implements IRecipeCategory<RitualRecipeWrapper>
{
	public static final ResourceLocation RITUAL_BG = new ResourceLocation(Corvus.MODID, "textures/gui/ritual.png");
	public static final String ID = "corvus.ritual";
	private IDrawable background;
	private IDrawable icon;

	public RitualRecipeCategory(IGuiHelper guiHelper)
	{
		background = guiHelper.createDrawable(RITUAL_BG, 40, 3, 120, 25);
		icon = guiHelper.createDrawableIngredient(new ItemStack(CorvusBlocks.CANDLE));
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public String getTitle() {
		return I18n.format("corvus.jei.rituals");
	}

	@Override
	public String getModName() {
		return Corvus.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout layout, RitualRecipeWrapper wrapper, IIngredients ingredients) {
		if(!(wrapper instanceof RitualRecipeWrapper))
			return;

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);

		int in = 0;
		for(int i = 0; i  < inputs.size(); i++) {
			layout.getItemStacks().init(i, true,  3 + (18 * i), 6);
			layout.getItemStacks().set(i, inputs.get(i));

			in++;
		}
	}
}