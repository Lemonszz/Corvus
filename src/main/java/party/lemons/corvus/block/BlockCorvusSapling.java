package party.lemons.corvus.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import party.lemons.corvus.block.effectcandle.IRequireGrowthCandle;
import party.lemons.lemonlib.item.IItemModel;

import java.util.Random;

public class BlockCorvusSapling extends BlockBush implements IGrowable, IItemModel, IRequireGrowthCandle
{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	private final WorldGenerator TREE_GENERATOR;

	public BlockCorvusSapling(WorldGenerator treeGren)
	{
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));

		TREE_GENERATOR = treeGren;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SAPLING_AABB;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			super.updateTick(worldIn, pos, state, rand);

			if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
			{
				this.grow(worldIn, pos, state, rand);
			}
		}
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{

	}

	public void growCandle(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (state.getValue(STAGE).intValue() == 0)
		{
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		}
		else
		{
			this.generateTree(worldIn, pos, state, rand);
		}
	}

	public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if(!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

		TREE_GENERATOR.generate(worldIn, rand, pos);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		this.grow(worldIn, pos, state, rand);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(STAGE).intValue();
		return i;
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, STAGE);
	}

}
