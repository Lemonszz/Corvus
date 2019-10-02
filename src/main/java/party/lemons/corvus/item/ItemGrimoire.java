package party.lemons.corvus.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.compat.jei.CorvusJEI;

public class ItemGrimoire extends ItemModel
{
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack  = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote)
		{
			if(Loader.isModLoaded("jei"))
			{
				if(playerIn.isSneaking())
				{
					CorvusJEI.showRitualCategory();
					return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
				}
			}

			//if(SpiritUtil.getSpirit(playerIn).getUnlockedSpells().size() > 0)
				Corvus.PROXY.openGrimoire();
		//	else
			//	playerIn.sendStatusMessage(new TextComponentTranslation("corvus.message.grimoire.fail"), true);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
