package party.lemons.corvus.potion;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import party.lemons.corvus.Corvus;
import party.lemons.corvus.init.CorvusPotions;

@Mod.EventBusSubscriber(modid = Corvus.MODID)
public class CorvusPotionHandler
{
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END)
		{
			PotionEffect effectSlowFall = event.player.getActivePotionEffect(CorvusPotions.SLOW_FALL);
			if(effectSlowFall != null)
			{
				if(!event.player.onGround && event.player.motionY < 0.0D)
				{
					double amt = 0.5D + (0.1D * effectSlowFall.getAmplifier());
					event.player.motionY *= amt;
					event.player.fallDistance = 0;
				}
			}

			if(event.player.getActivePotionEffect(CorvusPotions.PROJECTION) != null)
			{
				event.player.capabilities.isFlying = true;
				event.player.capabilities.allowFlying = true;
				event.player.noClip = true;
				event.player.capabilities.allowEdit = false;
				event.player.capabilities.disableDamage = true;
				event.player.resetActiveHand();
				event.player.sendPlayerAbilities();
				event.player.fallDistance = 0;
			}

		}
	}

	@SubscribeEvent
	public static void doNoClip(LivingEvent.LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();

		if(entity instanceof EntityPlayer)
		{
			if(entity.getActivePotionEffect(CorvusPotions.PROJECTION) != null)
			{
				entity.noClip = true;
				if (entity.getActivePotionEffect(CorvusPotions.STUNNED) != null)
				{
					EntityPlayer player = (EntityPlayer) entity;
					player.motionX += (player.getRNG().nextFloat() / (float)player.getRNG().nextInt(2)) * (player.getRNG().nextBoolean() ? -1 : 1);
					//player.motionY += (player.getRNG().nextFloat() / (float)player.getRNG().nextInt(2)) * (player.getRNG().nextBoolean() ? -1 : 1);
					player.motionZ += (player.getRNG().nextFloat() / (float)player.getRNG().nextInt(2)) * (player.getRNG().nextBoolean() ? -1 : 1);
					player.velocityChanged = true;
				}
			}
		}
		else
		{
			if(entity instanceof EntityLiving)
			{
				EntityLiving living = (EntityLiving) entity;
				if (!living.world.isRemote && living.getActivePotionEffect(CorvusPotions.STUNNED) != null)
				{
					living.setRevengeTarget(null);
					living.setAttackTarget(null);

					Vec3d vec3d = RandomPositionGenerator.getLandPos((EntityCreature)living, 4, 7);

					if(vec3d != null)
						living.getNavigator().setPath(living.getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z), 1F);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onAttackEvent(LivingAttackEvent event)
	{
		if(event.getEntity().world.isRemote)
			return;

		if(event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
			PotionEffect effectRage = attacker.getActivePotionEffect(CorvusPotions.BURNING_RAGE);
			if(effectRage != null)
			{
				int amount = 5 + (3 * effectRage.getAmplifier());
				event.getEntity().setFire(amount);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityKilled(LivingDeathEvent event)
	{
		if(event.getEntity() instanceof EntityLivingBase && event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
			PotionEffect eff = attacker.getActivePotionEffect(CorvusPotions.SOUL_FORGE);
			if(eff != null)
			{
				attacker.removePotionEffect(eff.getPotion());

				int health = (int) ((EntityLivingBase) event.getEntity()).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
				attacker.setAbsorptionAmount(attacker.getAbsorptionAmount() + health);
			}
		}
	}
}
