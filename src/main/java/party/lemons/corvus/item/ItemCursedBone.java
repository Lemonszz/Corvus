package party.lemons.corvus.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;

public class ItemCursedBone extends ItemModel
{
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand)
	{
		if(!player.world.isRemote && target instanceof EntityWolf)
		{
			EntityWolf wolf = (EntityWolf) target;
			if(!wolf.isTamed())
			{
				if(!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(wolf, player))
				{

					System.out.println(wolf.world.isRemote);
					wolf.setTamedBy(player);
					wolf.getNavigator().clearPath();
					wolf.setAttackTarget(null);
					wolf.getAISit().setSitting(true);
					wolf.setHealth(20);
					wolf.world.setEntityState(wolf, (byte)7);

					for (int i = 0; i < 7; ++i)
					{
						double d0 = wolf.getRNG().nextGaussian() * 0.02D;
						double d1 = wolf.getRNG().nextGaussian() * 0.02D;
						double d2 = wolf.getRNG().nextGaussian() * 0.02D;
						wolf.world.spawnParticle(
								EnumParticleTypes.HEART,
								wolf.posX + (double)(wolf.getRNG().nextFloat() * wolf.width * 2.0F) - (double)wolf.width,
								wolf.posY + 0.5D + (double)(wolf.getRNG().nextFloat() * wolf.height),
								wolf.posZ + (wolf.getRNG().nextFloat() * wolf.width * 2.0F) - (double)wolf.width,
								d0, d1, d2);
					}
					stack.shrink(1);
				}
			}
		}

		return false;
	}
}
