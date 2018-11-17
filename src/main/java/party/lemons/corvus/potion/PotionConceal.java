package party.lemons.corvus.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;

public class PotionConceal extends PotionCorvus
{
	public PotionConceal(boolean isBadEffectIn, int liquidColorIn, int iconX, int iconY)
	{
		super(isBadEffectIn, liquidColorIn, iconX, iconY);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
	{
		entityLivingBaseIn.setInvisible(true);
	}

	@Override
	public boolean isInstant()
	{
		return true;
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		entityLivingBaseIn.setInvisible(false);

		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
	}
}
