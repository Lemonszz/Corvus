package party.lemons.corvus.item;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class ItemBreathingTulip extends ItemBlock
{
	public ItemBreathingTulip(Block block)
	{
		super(block);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
			return EnumActionResult.SUCCESS;

		IBlockState hitState = world.getBlockState(pos);
		ItemStack stack = player.getHeldItem(hand);

		if(facing == EnumFacing.UP && hitState.getBlock().canSustainPlant(hitState, world, pos.up(), facing, (IPlantable) getBlock()))
		{
			super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}
		else if(hitState.getBlock() instanceof IGrowable && ((IGrowable)hitState.getBlock()).canUseBonemeal(world, world.rand, pos, hitState))
		{
			applyBonemeal(stack, world, pos, player, hand);
			stack.damageItem(1, player);
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.heal(10F);
		stack.damageItem(1, playerIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		playerIn.swingArm(hand);
		if(target instanceof IMob)
		{
			target.knockBack(playerIn, 2F, (double) MathHelper.sin(playerIn.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(playerIn.rotationYaw * 0.017453292F)));
			stack.damageItem(1, playerIn);
			return true;
		}
		else
		{
			target.heal(10);
			stack.damageItem(1, playerIn);
			return true;
		}
	}

	public static boolean applyBonemeal(ItemStack stack, World world, BlockPos target, EntityPlayer player, @Nullable EnumHand hand)
	{
		IBlockState iblockstate = world.getBlockState(target);

		int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, world, target, iblockstate, stack, hand);
		if (hook != 0) return hook > 0;

		if (iblockstate.getBlock() instanceof IGrowable)
		{
			IGrowable igrowable = (IGrowable)iblockstate.getBlock();

			if (igrowable.canGrow(world, target, iblockstate, world.isRemote))
			{
				if (!world.isRemote)
				{
					if (igrowable.canUseBonemeal(world, world.rand, target, iblockstate))
					{
						igrowable.grow(world, world.rand, target, iblockstate);
					}

					stack.damageItem(1, player);
				}

				return true;
			}
		}

		return false;
	}
}
