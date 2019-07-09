package party.lemons.corvus.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import party.lemons.corvus.util.RandomUtil;

import java.util.Iterator;
import java.util.Random;

public class WorldGenRandomRockBlob extends WorldGenerator
{
	final int radius;
	final Block[] blocks;

	public WorldGenRandomRockBlob(int radius, Block... blocks)
	{
		this.radius = radius;
		this.blocks = blocks;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		while(true) {
			label51: {
				if (position.getY() > 3) {
					if (worldIn.isAirBlock(position.down())) {
						break label51;
					}

					Block block = worldIn.getBlockState(position.down()).getBlock();
					if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.STONE) {
						break label51;
					}
				}

				if (position.getY() <= 3) {
					return false;
				}

				int i1 = this.radius;

				for(int i = 0; i1 >= 0 && i < 3; ++i) {
					int j = i1 + rand.nextInt(2);
					int k = i1 + rand.nextInt(2);
					int l = i1 + rand.nextInt(2);
					float f = (float)(j + k + l) * 0.333F + 0.5F;
					Iterator var10 = BlockPos.getAllInBox(position.add(-j, -k, -l), position.add(j, k, l)).iterator();

					while(var10.hasNext()) {
						BlockPos blockpos = (BlockPos)var10.next();
						if (blockpos.distanceSq(position) <= (double)(f * f))
						{
							Block chooseRock = (Block) RandomUtil.randomFromArray(rand, blocks);

							worldIn.setBlockState(blockpos, chooseRock.getDefaultState(), 4);
						}
					}

					position = position.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), 0 - rand.nextInt(2), -(i1 + 1) + rand.nextInt(2 + i1 * 2));
				}

				return true;
			}

			position = position.down();
		}
	}
}
