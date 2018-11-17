package party.lemons.corvus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import party.lemons.corvus.block.effectcandle.IRequireGrowthCandle;

import java.util.Random;
import java.util.function.Supplier;

public class BlockCorvusCrop extends BlockBush implements IGrowable, IRequireGrowthCandle
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 4);
	private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	private final Supplier<Item> drop, seed;

	public BlockCorvusCrop(Supplier<Item> drop, Supplier<Item> seed)
	{
		super(Material.PLANTS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(this.getAgeProperty(), 0));
		this.setTickRandomly(true);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.disableStats();
		this.drop = drop;
		this.seed =  seed;
	}

	protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
	{
		float f = 1.0F;
		BlockPos blockpos = pos.down();

		for(int i = -1; i <= 1; ++i)
		{
			for(int j = -1; j <= 1; ++j)
			{
				float f1 = 0.0F;
				IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

				if(iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) blockIn))
				{
					f1 = 1.0F;

					if(iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j)))
					{
						f1 = 3.0F;
					}
				}

				if(i != 0 || j != 0)
				{
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		BlockPos blockpos1 = pos.north();
		BlockPos blockpos2 = pos.south();
		BlockPos blockpos3 = pos.west();
		BlockPos blockpos4 = pos.east();
		boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
		boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();

		if(flag && flag1)
		{
			f /= 2.0F;
		}else
		{
			boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();

			if(flag2)
			{
				f /= 2.0F;
			}
		}

		return f * 2;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CROPS_AABB[state.getValue(this.getAgeProperty()).intValue()];
	}

	protected PropertyInteger getAgeProperty()
	{
		return AGE;
	}

	protected boolean canSustainBush(IBlockState state)
	{
		return state.getBlock() == Blocks.FARMLAND;
	}

	public int getMaxAge()
	{
		return 4;
	}

	protected int getAge(IBlockState state)
	{
		return state.getValue(this.getAgeProperty()).intValue();
	}

	public IBlockState withAge(int age)
	{
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
	}

	public boolean isMaxAge(IBlockState state)
	{
		return state.getValue(this.getAgeProperty()).intValue() >= this.getMaxAge();
	}

	public void grow(World worldIn, BlockPos pos, IBlockState state)
	{

	}

	protected int getBonemealAgeIncrease(World worldIn)
	{
		return MathHelper.getInt(worldIn.rand, 1, 2);
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		IBlockState soil = worldIn.getBlockState(pos.down());
		return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
	}

	protected Item getSeed()
	{
		return this.seed.get();
	}

	protected Item getCrop()
	{
		return this.drop.get();
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		super.getDrops(drops, world, pos, state, 0);
		int age = getAge(state);
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		if(age >= getMaxAge())
		{
			int k = 3 + fortune;

			for(int i = 0; i < k; ++i)
			{
				if(rand.nextInt(2 * getMaxAge()) <= age)
				{
					drops.add(new ItemStack(this.getSeed(), 1, 0));
				}
			}
		}
	}

	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return this.isMaxAge(state) ? this.getCrop() : this.getSeed();
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this.getSeed());
	}

	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return !this.isMaxAge(state);
	}

	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		this.grow(worldIn, pos, state);
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.withAge(meta);
	}

	public int getMetaFromState(IBlockState state)
	{
		return this.getAge(state);
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AGE);
	}

	@Override
	public void growCandle(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
		int i = this.getAge(state);

		if(i < this.getMaxAge())
		{
			if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true))
			{
				worldIn.setBlockState(pos, this.withAge(i + 1), 2);
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
		}
	}
}
