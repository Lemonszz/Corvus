package party.lemons.corvus.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class PotionStun extends PotionCorvus
{
	public PotionStun(boolean isBadEffectIn, int liquidColorIn, int iconX, int iconY)
	{
		super(isBadEffectIn, liquidColorIn, iconX, iconY);
	}

	@Override
	public boolean isInstant()
	{
		return false;
	}

	@Override
	public void performEffect(EntityLivingBase living, int amplifier)
	{


		super.performEffect(living, amplifier);
	}

	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase living, int amplifier, double health)
	{
		if(!(living instanceof EntityCreature))
			return;

		living.setRevengeTarget(null);
		Vec3d vec3d = RandomPositionGenerator.getLandPos((EntityCreature)living, 4, 7);

		((EntityCreature) living).getNavigator().setPath(((EntityCreature) living).getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z), 1F);
		System.out.println("FG");
		super.affectEntity(source, indirectSource, living, amplifier, health);
	}
}
