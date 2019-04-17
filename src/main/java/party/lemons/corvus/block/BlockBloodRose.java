package party.lemons.corvus.block;

import net.minecraft.block.BlockMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

public class BlockBloodRose extends BlockCorvusFlower
{
	public BlockBloodRose()
	{
		this.setTickRandomly(true);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (rand.nextInt(25) == 0)
		{
			int i = 5;
			int j = 4;

			for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4)))
			{
				if (worldIn.getBlockState(blockpos).getBlock() == this)
				{
					--i;

					if (i <= 0)
					{
						return;
					}
				}
			}

			BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

			for (int k = 0; k < 4; ++k)
			{
				if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, this.getDefaultState()))
				{
					pos = blockpos1;
				}

				blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
			}

			if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, this.getDefaultState()))
			{
				worldIn.setBlockState(blockpos1, this.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		player.attackEntityFrom(DamageSource.CACTUS, 7);
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		placer.attackEntityFrom(DamageSource.CACTUS, 7);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}


	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Cave;
	}
}
