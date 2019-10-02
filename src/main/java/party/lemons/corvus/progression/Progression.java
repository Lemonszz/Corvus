package party.lemons.corvus.progression;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Progression extends IForgeRegistryEntry.Impl<Progression>
{
	private ItemStack displayIcon;
	private Progression[] parents;

	public Progression()
	{
		this.displayIcon = new ItemStack(Items.EMERALD);
		this.parents = new Progression[0];
	}

	public Progression setDisplayIcon(ItemStack stack)
	{
		this.displayIcon = stack;
		return this;
	}

	public Progression setParents(Progression... parents)
	{
		this.parents = parents;
		return this;
	}

	public Progression[] getParents()
	{
		return parents;
	}

	public ItemStack getDisplayIcon()
	{
		return displayIcon;
	}

	public String getNameTranslationKey()
	{
		return "progression." + getRegistryName().getNamespace() + "." + getRegistryName().getPath() + ".name";
	}

	public String getDescriptionTranslationKey()
	{
		return "progression." + getRegistryName().getNamespace() + "." + getRegistryName().getPath() + ".desc";
	}
}
