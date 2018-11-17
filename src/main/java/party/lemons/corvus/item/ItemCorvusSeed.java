package party.lemons.corvus.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import party.lemons.lemonlib.item.IItemModel;

import java.util.function.Supplier;

public class ItemCorvusSeed extends Item implements IItemModel, IPlantable
{
	private final Supplier<IBlockState> cropBlock;
	private final EnumPlantType type;

	public ItemCorvusSeed(Supplier<IBlockState> cropBlock, EnumPlantType type)
	{
		this.cropBlock = cropBlock;
		this.type = type;
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.getHeldItem(hand);
		IBlockState state = worldIn.getBlockState(pos);

		if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
		{
			worldIn.setBlockState(pos.up(), cropBlock.get());
			itemstack.shrink(1);

			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return type;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return cropBlock.get();
	}
}
