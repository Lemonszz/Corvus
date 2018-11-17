package party.lemons.corvus.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.lemonlib.item.IItemModel;

public class ItemSpiritFood extends ItemFood implements IItemModel
{
	private final int spirit;

	public ItemSpiritFood(int amount, float saturation, int spirit, boolean isWolfFood)
	{
		super(amount, saturation, isWolfFood);
		this.setAlwaysEdible();
		this.spirit = spirit;
	}

	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
	{
		super.onFoodEaten(stack, worldIn, player);

		if(!worldIn.isRemote)
		{
			SpiritUtil.changeSpirit(player, spirit);
		}
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
	}
}
