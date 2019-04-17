package party.lemons.corvus.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import party.lemons.corvus.capability.projection.IProjection;
import party.lemons.corvus.capability.projection.ProjectionCapability;

public class PotionProjection extends PotionCorvus
{
	public PotionProjection(boolean isBadEffectIn, int liquidColorIn, int iconX, int iconY)
	{
		super(isBadEffectIn, liquidColorIn, iconX, iconY);
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			player.capabilities.isFlying = false;

			if(!player.isCreative())
			{
				player.capabilities.disableDamage = false;
				player.capabilities.allowFlying = false;
			}

			if(!player.isSpectator())
			{
				player.capabilities.allowEdit = true;
			}
			player.sendPlayerAbilities();


			IProjection cap = entity.getCapability(ProjectionCapability.CAPABILITY, null);
			if(cap.isProjecting())
			{
				entity.setPositionAndUpdate(cap.getRecallPosition().getX() + 0.5F, cap.getRecallPosition().getY(), cap.getRecallPosition().getZ() + 0.5F);
				cap.setProjecting(false);
			}
		}
		super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);
	}
}
