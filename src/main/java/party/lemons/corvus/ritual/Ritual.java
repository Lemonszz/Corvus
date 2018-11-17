package party.lemons.corvus.ritual;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.registries.IForgeRegistryEntry;
import party.lemons.corvus.capability.crow.CrowCapability;
import party.lemons.corvus.init.CorvusSounds;
import party.lemons.corvus.spell.SpellLeap;

import java.util.ArrayList;
import java.util.List;

public class Ritual extends IForgeRegistryEntry.Impl<Ritual>
{
	private final Ingredient[] ingredients;

	public Ritual(Ingredient... ingredients)
	{
		this.ingredients = ingredients;
	}

	public boolean doRitual(World world, BlockPos pos, EntityPlayer player)
	{
		if(world.isRemote)
			return false;

		if(removeItems(world, pos))
		{
			perform(world, pos, player);
			world.playSound(null, pos, CorvusSounds.RITUAL, SoundCategory.BLOCKS, 2F, 1F);
			return true;
		}

		return false;
	}

	public void perform(World world, BlockPos pos, EntityPlayer player)
	{

	}

	public boolean isEmpty()
	{
		return ingredients.length == 0;
	}

	public boolean removeItems(World world, BlockPos pos)
	{
		if(isEmpty())
			return false;

		AxisAlignedBB bb = new AxisAlignedBB(pos).grow(4D);
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, bb);

		List<EntityItem> matches = new ArrayList<>();

		boolean found = false;
		for(Ingredient ingredient : ingredients)
		{
			found = false;
			for(EntityItem item : items)
			{
				if(ingredient.apply(item.getItem()))
				{
					matches.add(item);
					found = true;
					break;
				}
			}

			if(!found)
			{
				break;
			}
		}

		if(found)
		{
			for(EntityItem item : matches)
			{
				item.getItem().shrink(1);
				CrowCapability.spawnParticles((WorldServer) world, item, 5, 0.3F);
			}

			return true;
		}

		return false;
	}

	public Ingredient[] getIngredients()
	{
		return ingredients;
	}

	public String getTranslationKey()
	{
		return getRegistryName() + ".name";
	}
}
