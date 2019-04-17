package party.lemons.corvus.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.corvus.block.BlockCorvusFlower;

public class BlockLilyToxic extends BlockCorvusFlower
{
	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
			PotionEffect effOld = entityLiving.getActivePotionEffect(MobEffects.POISON);

			if(effOld == null || effOld.getDuration() <= 0)
			{
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.POISON, 60, 0));
			}
		}
		super.onEntityCollision(worldIn, pos, state, entityIn);
	}
}
