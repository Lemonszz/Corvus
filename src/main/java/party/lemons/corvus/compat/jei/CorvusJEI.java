package party.lemons.corvus.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import party.lemons.corvus.init.CorvusBlocks;
import party.lemons.corvus.ritual.Ritual;
import party.lemons.corvus.ritual.RitualRegistry;

import java.util.stream.Collectors;

@JEIPlugin
public class CorvusJEI implements IModPlugin
{
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new RitualRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	public void register(IModRegistry registry) {
		registry.handleRecipes(Ritual.class, r -> new RitualRecipeWrapper(registry.getJeiHelpers(), r), RitualRecipeCategory.ID);
		registry.addRecipes(RitualRegistry.REGISTRY.getValuesCollection().stream().filter(r -> !r.isEmpty()).collect(Collectors.toList()), RitualRecipeCategory.ID);
		registry.addRecipeCatalyst(new ItemStack(CorvusBlocks.CANDLE), RitualRecipeCategory.ID);
	}
}
