package party.lemons.corvus.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import party.lemons.corvus.handler.EffectHandler;
import party.lemons.corvus.init.CorvusSounds;

import java.util.Random;

public class BlockStunningDahlia extends BlockCorvusFlower
{
	public BlockStunningDahlia()
	{
		setBlockUnbreakable();
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack held = playerIn.getHeldItem(hand);

		if(!held.isEmpty() && held.getItem() == Items.DIAMOND)
		{
			if(!playerIn.isCreative())
				held.shrink(1);

			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_BREAK, SoundCategory.BLOCKS, 1, 1);
			playerIn.swingArm(hand);
			if(!world.isRemote)
			{
				float pitchOffset = (world.rand.nextFloat() / 5) * (world.rand.nextBoolean() ? 1 : -1);
				world.playSound(null, pos, CorvusSounds.ITEM_SUMMON, SoundCategory.BLOCKS, 1F, 1F + pitchOffset);


				EffectHandler.performEffect(EffectHandler.STUNNING_DAHLIA, pos, world);
				world.setBlockToAir(pos);
				ItemStack dropStack = new ItemStack(this);
				EntityItem entityitem = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.1F, pos.getZ() + 0.5F, dropStack);
				entityitem.motionX = 0;
				entityitem.motionY = 0.25F;
				entityitem.motionZ = 0;
				entityitem.velocityChanged = true;
				entityitem.setDefaultPickupDelay();
				world.spawnEntity(entityitem);
			}
		}

		return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.AIR;
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.NONE;
	}
}
