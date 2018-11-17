package party.lemons.corvus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.lemonlib.item.IItemModel;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public class BlockCorvusLog extends Block implements IItemModel
{
	public BlockCorvusLog()
	{
		super(Material.WOOD);
		this.setHardness(2.0F);
		this.setResistance(0.5F);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState();


		switch (meta)
		{
			case 0:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
				break;
			case 1:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
				break;
			case 2:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
				break;
			default:
				iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return iblockstate;
	}

	public int getMetaFromState(IBlockState state)
	{
		int i;
		switch (state.getValue(LOG_AXIS))
		{
			case X:
				i = 1;
				break;
			case Z:
				i = 2;
				break;
			default:
				i = 0;
		}
		return i;
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
	 * fine.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:

				switch (state.getValue(LOG_AXIS))
				{
					case X:
						return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
					case Z:
						return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
					default:
						return state;
				}

			default:
				return state;
		}
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		int updateRange = 4;
		int checkRange = 5;

		if (worldIn.isAreaLoaded(pos.add(-checkRange, -checkRange, -checkRange), pos.add(checkRange, checkRange, checkRange)))
		{
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-updateRange, -updateRange, -updateRange), pos.add(updateRange, updateRange, updateRange)))
			{
				IBlockState iblockstate = worldIn.getBlockState(blockpos);

				if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
				{
					iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
				}
			}
		}
	}

	@Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	@Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, LOG_AXIS);
	}
}
