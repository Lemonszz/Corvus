package party.lemons.corvus.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.corvus.init.CorvusItems;
import party.lemons.corvus.ritual.Ritual;
import party.lemons.corvus.ritual.RitualRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class CorvusJEI implements IModPlugin
{
	private static final List<String> showCategories = new ArrayList<>();
	private static IRecipesGui gui;

	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RitualRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	public void register(IModRegistry registry) {
		registry.handleRecipes(Ritual.class, r -> new RitualRecipeWrapper(registry.getJeiHelpers(), r), RitualRecipeCategory.ID);
		registry.addRecipes(RitualRegistry.REGISTRY.getValuesCollection().stream().filter(r -> !r.isEmpty()).collect(Collectors.toList()), RitualRecipeCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(CorvusBlocks.CANDLE), RitualRecipeCategory.ID);

		registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(CorvusItems.STAR));
		registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(CorvusItems.SPELLBOOK));
	}

	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{
		showCategories.add(RitualRecipeCategory.ID);
		gui = jeiRuntime.getRecipesGui();
	}

	public static void showRitualCategory()
	{
		gui.showCategories(showCategories);
	}
}
