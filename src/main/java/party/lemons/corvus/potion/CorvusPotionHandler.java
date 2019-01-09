package party.lemons.corvus.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
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
		if(event.getEntity() instanceof EntityPlayer)
		{
			if(event.getEntityLiving().getActivePotionEffect(CorvusPotions.PROJECTION) != null)
			{
				event.getEntityLiving().noClip = true;
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
