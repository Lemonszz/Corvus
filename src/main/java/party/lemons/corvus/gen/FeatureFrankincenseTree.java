package party.lemons.corvus.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import party.lemons.corvus.block.BlockCorvusSapling;
import party.lemons.corvus.init.CorvusBlocks;

import java.util.Random;
import java.util.function.Supplier;

public class FeatureFrankincenseTree extends WorldGenAbstractTree
{
	private static final Supplier<IBlockState> TRUNK = () -> CorvusBlocks.FRANKINSENCE_LOG.getDefaultState();
	private static final Supplier<IBlockState> LEAF = () -> CorvusBlocks.FRANKINSENCE_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);

	public FeatureFrankincenseTree(boolean notify)
	{
		super(notify);
	}

	public boolean generate(World world, Random rand, BlockPos position)
	{
		int height = rand.nextInt(3) + rand.nextInt(3) + 9;
		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
		{
			for (int y = position.getY(); y <= position.getY() + 1 + height; ++y)
			{
				int offset = 1;

				if (y == position.getY())
				{
					offset = 0;
				}

				if (y >= position.getY() + 1 + height - 2)
				{
					offset = 2;
				}

				offset = 0;

				BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

				for (int x = position.getX() - offset; x <= position.getX() + offset && flag; ++x)
				{
					for (int z = position.getZ() - offset; z <= position.getZ() + offset && flag; ++z)
					{
						if (y >= 0 && y < 256)
						{
							if (!this.isReplaceable(world, pos.setPos(x, y, z)))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				BlockPos down = position.down();
				IBlockState state = world.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, world, down, net.minecraft.util.EnumFacing.UP, ((net.minecraft.block.BlockSapling) Blocks.SAPLING));

				if (isSoil && position.getY() < world.getHeight() - height - 1)
				{
					state.getBlock().onPlantGrow(state, world, down, position);
					this.placeLogAt(world, position);
					EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
					int k2 = height - rand.nextInt(10) - 1;
					int l2 = 0;
					int i3 = position.getX();
					int j1 = position.getZ();
					int k1 = 0;

					for (int l1 = 0; l1 < height; ++l1)
					{
						int i2 = position.getY() + l1;

						if (l1 >= k2 && l2 > 0)
						{
							i3 += enumfacing.getXOffset();
							j1 += enumfacing.getZOffset();
							--l2;
						}

						BlockPos blockpos = new BlockPos(i3, i2, j1);
						state = world.getBlockState(blockpos);

						if (state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos))
						{
							this.placeLogAt(world, blockpos);
							k1 = i2;
						}
					}

					BlockPos blockpos2 = new BlockPos(i3, k1, j1);

					for (int j3 = -3; j3 <= 3; ++j3)
					{
						for (int i4 = -3; i4 <= 3; ++i4)
						{
							if (Math.abs(j3) != 3 || Math.abs(i4) != 3)
							{
								this.placeLeafAt(world, blockpos2.add(j3, 0, i4));
							}
						}
					}

					blockpos2 = blockpos2.up();

					for (int k3 = -1; k3 <= 1; ++k3)
					{
						for (int j4 = -1; j4 <= 1; ++j4)
						{
							this.placeLeafAt(world, blockpos2.add(k3, 0, j4));
						}
					}

					this.placeLeafAt(world, blockpos2.east(2));
					this.placeLeafAt(world, blockpos2.west(2));
					this.placeLeafAt(world, blockpos2.south(2));
					this.placeLeafAt(world, blockpos2.north(2));
					i3 = position.getX();
					j1 = position.getZ();

					for(int i = 0; i < 4; i++)
					{
					EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);

					if (true)
					{
						int val = height - rand.nextInt(10) - 1;

						int l3 = val - rand.nextInt(2) - 1;
						int k4 = 1 + rand.nextInt(3);
						k1 = 0;

						for(int l4 = l3; l4 < height && k4 > 0; --k4)
						{
							if(l4 >= 1)
							{
								int j2 = position.getY() + l4;
								i3 += enumfacing1.getXOffset();
								j1 += enumfacing1.getZOffset();
								BlockPos blockpos1 = new BlockPos(i3, j2, j1);
								state = world.getBlockState(blockpos1);

								if(state.getBlock().isAir(state, world, blockpos1) || state.getBlock().isLeaves(state, world, blockpos1))
								{
									this.placeLogAt(world, blockpos1);
									k1 = j2;
								}
							}
							i3 -= enumfacing1.getXOffset();
							j1 -= enumfacing1.getZOffset();
							++l4;
						}

						if(k1 > 0)
						{
							BlockPos blockpos3 = new BlockPos(i3, k1, j1);

							for(int i5 = -2; i5 <= 2; ++i5)
							{
								for(int k5 = -2; k5 <= 2; ++k5)
								{
									if(Math.abs(i5) != 2 || Math.abs(k5) != 2)
									{
										this.placeLeafAt(world, blockpos3.add(i5, 0, k5));
									}
								}
							}

							blockpos3 = blockpos3.up();

							for(int j5 = -1; j5 <= 1; ++j5)
							{
								for(int l5 = -1; l5 <= 1; ++l5)
								{
									this.placeLeafAt(world, blockpos3.add(j5, 0, l5));
								}
							}
						}
					}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}

	private void placeLogAt(World worldIn, BlockPos pos)
	{
		this.setBlockAndNotifyAdequately(worldIn, pos, TRUNK.get());
	}

	private void placeLeafAt(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos))
		{
			this.setBlockAndNotifyAdequately(worldIn, pos, LEAF.get());
		}
	}

	protected boolean canGrowInto(Block blockType)
	{
		return super.canGrowInto(blockType) || blockType instanceof BlockCorvusSapling;
	}
}
