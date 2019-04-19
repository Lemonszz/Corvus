package party.lemons.corvus.gen.gaia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import party.lemons.lemonlib.gen.feature.Feature;

import java.util.Random;
import java.util.function.Supplier;

public class FeatureFlower extends WorldGenerator
{
	private Supplier<IBlockState> placeState;
	private int maxLight;

	public FeatureFlower(Supplier<IBlockState> placeState)
	{
		this(placeState, 99);
	}

	public FeatureFlower(Supplier<IBlockState> placeState, int maxLight)
	{
		this.placeState = placeState;
		this.maxLight=  maxLight;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position)
	{
		IBlockState toPlace = placeState.get();
		if(!(toPlace.getBlock() instanceof BlockBush))
			return false;

		BlockBush block = (BlockBush) toPlace.getBlock();

		for (int i = 0; i < 64; ++i)
		{
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (world.isAirBlock(blockpos) && (!world.provider.isNether() || blockpos.getY() < 255) && block.canBlockStay(world, blockpos, toPlace))
			{
				if(world.getLight(blockpos) <= maxLight)
				{
					world.setBlockState(blockpos, toPlace, 2);
				}
			}
		}

		return true;
	}
}
