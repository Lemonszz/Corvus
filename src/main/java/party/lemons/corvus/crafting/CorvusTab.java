package party.lemons.corvus.crafting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.CorvusItems;

public class CorvusTab extends CreativeTabs
{
	public static final CorvusTab INSTANCE = new CorvusTab();

	public CorvusTab()
	{
		super(Corvus.MODID);
		setBackgroundImageName("item_search.png");
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items)
	{
		super.displayAllRelevantItems(items);

		ItemStack itemstack = new ItemStack(Items.SPAWN_EGG, 1);
		ItemMonsterPlacer.applyEntityIdToItemStack(itemstack, EntityList.ENTITY_EGGS.get(new ResourceLocation(Corvus.MODID, "crow")).spawnedID);
		items.add(itemstack);
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(CorvusItems.GRIMOIRE);
	}

	public int getLabelColor()
	{
		return 0x684848;
	}
}
