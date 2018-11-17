package party.lemons.corvus.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.SpiritUtil;

public class ItemGrimoire extends ItemModel
{
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack  = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote)
		{
			if(SpiritUtil.getSpirit(playerIn).getUnlockedSpells().size() > 0)
				Corvus.PROXY.openGrimoire();
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
