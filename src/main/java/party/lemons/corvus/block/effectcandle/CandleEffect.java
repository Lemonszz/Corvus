package party.lemons.corvus.block.effectcandle;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class CandleEffect
{
	public abstract void doEffect(World world, BlockPos pos);
	public abstract int getColour();


	public static class EffectFire extends CandleEffect
	{
		@Override
		public void doEffect(World world, BlockPos pos)
		{
			if(world.isRemote)
				return;

			AxisAlignedBB bb = new AxisAlignedBB(pos).grow(5);

			List<Entity> entities = world.getEntitiesWithinAABB(EntityMob.class, bb);
			entities.forEach(e->e.setFire(3));
		}

		@Override
		public int getColour()
		{
			return 0xd85858;
		}
	}

	public static class EffectGrowth extends CandleEffect
	{
		@Override
		public void doEffect(World world, BlockPos pos)
		{
			if(world.isRemote)
				return;

			AxisAlignedBB bb = new AxisAlignedBB(pos).grow(5);

			List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, bb, e->!(e instanceof EntityMob));
			entities.forEach(e->e.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 0, true, false)));

			for(int i = 0; i < 10; i++)
			{
				if(world.rand.nextInt(1) == 0)
				{
					int x = (int) (world.rand.nextInt((int) ((bb.maxX - bb.minX) + 1)) + bb.minX);
					int y = (int) (world.rand.nextInt((int) ((bb.maxY - bb.minY) + 1)) + bb.minY);
					int z = (int) (world.rand.nextInt((int) ((bb.maxZ - bb.minZ) + 1)) + bb.minZ);
					BlockPos growPos = new BlockPos(x, y, z);

					IBlockState state = world.getBlockState(growPos);
					Block block = state.getBlock();
					if(block == Blocks.TALLGRASS) return;

					if(block instanceof IRequireGrowthCandle && block instanceof IGrowable)
					{
						if(((IGrowable) block).canGrow(world, growPos, state, false))
						{
							((IRequireGrowthCandle) block).growCandle(world, growPos, state, world.rand);
						}
					}else if(block instanceof IGrowable)
					{
						if(((IGrowable) block).canGrow(world, growPos, state, false))
						{
							((IGrowable) block).grow(world, world.rand, growPos, state);
						}
					}
				}
			}
		}

		@Override
		public int getColour()
		{
			return 0x60d858;
		}
	}

	public static class EffectWind extends CandleEffect
	{
		@Override
		public void doEffect(World world, BlockPos pos)
		{
			if(world.isRemote)
				return;

			AxisAlignedBB bb = new AxisAlignedBB(pos).grow(5);
			List<EntityMob> entities = world.getEntitiesWithinAABB(EntityMob.class, bb);

			entities.forEach(e->{
				Vec3d vec = new Vec3d(pos.getX() - e.getPosition().getX(),  pos.getY() - e.getPosition().getY(), pos.getZ() - e.getPosition().getZ()).normalize();
				if(vec.z == 0 && vec.x == 0)
				{
					vec = vec.add(world.rand.nextFloat(), 0, world.rand.nextFloat());
				}
				e.knockBack(e, 0.5F, vec.x, vec.z);
			});
		}

		@Override
		public int getColour()
		{
			return 0x58a9d8;
		}
	}
}
