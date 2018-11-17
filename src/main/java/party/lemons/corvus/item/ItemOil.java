package party.lemons.corvus.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import party.lemons.corvus.capability.spirit.SpiritUtil;
import party.lemons.corvus.init.CorvusSpells;
import party.lemons.corvus.spell.SpellLeap;
import party.lemons.lemonlib.item.IItemModel;

import java.util.function.Supplier;

public class ItemOil extends Item implements IItemModel
{
	private final Supplier<PotionEffect[]> potionEffect;

	public ItemOil(Supplier<PotionEffect[]> potionEffect)
	{
		this.potionEffect = potionEffect;
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
		{
			stack.shrink(1);
		}

		if (!worldIn.isRemote)
		{
			for(PotionEffect effect : potionEffect.get())
			{
				if(effect.getPotion().isInstant())
				{
					effect.getPotion().affectEntity(entityplayer, entityplayer, entityLiving, effect.getAmplifier(), 1.0D);
				}else
				{
					entityLiving.addPotionEffect(new PotionEffect(effect));
				}
			}
		}
		return stack;
	}

	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}

	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}
