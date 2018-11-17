package party.lemons.corvus.block.effectcandle;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public interface IRequireGrowthCandle
{
	void growCandle(World worldIn, BlockPos pos, IBlockState state, Random random);
}
